package com.example.quizwiz.fireBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.models.LeaderBoardModel;
import com.example.quizwiz.models.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FireBaseClass {
    private static final FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    public static FireBaseClass getInstance() {
        return new FireBaseClass();
    }


    // Đăng ký người dùng mới
    public void registerUser(UserModel userInfo) {
        mFireStore.collection(Constants.user)
                .document(getCurrentUserId()).set(userInfo, SetOptions.merge());
    }

    // Lấy thông tin người dùng
    public static void getUserInfo(UserInfoCallback callback) {
        mFireStore.collection(Constants.user)
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserModel userInfo = documentSnapshot.toObject(UserModel.class);
                    callback.onUserInfoFetched(userInfo);
                })
                .addOnFailureListener(e -> callback.onUserInfoFetched(null));
    }

    // Cập nhật hồ sơ người dùng
    public void updateProfile(String name, Uri imgUri) {
        String userId = getCurrentUserId();
        if (!name.isEmpty()) {
            mFireStore.collection(Constants.user).document(userId).update("name", name);
        }
        if (imgUri != null) {
            uploadImage(imgUri);
        }
    }

    // Đặt hình ảnh hồ sơ
    public void setProfileImage(String imageRef, ShapeableImageView view) {
        if (!imageRef.isEmpty()) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference pathReference = storageRef.child(imageRef);
            final long ONE_MEGABYTE = 1024 * 1024;
            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(byteArray -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                view.setImageBitmap(bmp);
            });
        }
    }


    // Tải lên hình ảnh
    private void uploadImage(Uri imgUri) {
        String userId = getCurrentUserId();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profilePicRef = storageRef.child("profile_pictures/" + userId);
        profilePicRef.putFile(imgUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mFireStore.collection(Constants.user).document(userId)
                        .update("image", "profile_pictures/" + userId);
            } else {
                Log.e("ImageUpload", "Unsuccessful");
            }
        });
    }

    // Cập nhật điểm số
    public static void updateScore(double newScore) {
        String userId = getCurrentUserId();
        getUserInfo(new UserInfoCallback() {
            @Override
            public void onUserInfoFetched(UserModel userInfo) {
                if (userInfo != null) {
                    double newAllTimeScore = userInfo.getAllTimeScore() + newScore;
                    double newWeeklyScore = userInfo.getWeeklyScore() + newScore;
                    double newMonthlyScore = userInfo.getMonthlyScore() + newScore;
                    mFireStore.collection(Constants.user).document(userId).update(
                                    Constants.ALL_TIME_SCORE, newAllTimeScore,
                                    Constants.weeklyScore, newWeeklyScore,
                                    Constants.monthlyScore, newMonthlyScore,
                                    Constants.lastGameScore, newScore
                            ).addOnSuccessListener(aVoid -> Log.e("DataUpdate", "Updated"))
                            .addOnFailureListener(e -> Log.e("DataUpdate", "Failed"));
                }
            }
        });
    }

    // Lấy thứ hạng người dùng
    public static void getUserRank(String type, UserRankCallback callback) {
        mFireStore.collection(Constants.user)
                .orderBy(type, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(result -> {
                    int rank = 1;
                    String userId = getCurrentUserId();
                    for (DocumentSnapshot document : result.getDocuments()) {
                        if (document.getId().equals(userId)) break;
                        rank++;
                    }
                    callback.onUserRankFetched(rank);
                })
                .addOnFailureListener(e -> {
                    Log.e("QueryResult", "Failure");
                    callback.onUserRankFetched(null);
                });
    }

    // Lấy dữ liệu bảng xếp hạng
    public void getLeaderBoardData(String type, LeaderBoardDataCallback callback) {
        mFireStore.collection(Constants.user)
                .orderBy(type, Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(result -> {
                    List<UserModel> otherRankers = new ArrayList<>();
                    UserModel rank1 = result.getDocuments().get(0).toObject(UserModel.class);
                    UserModel rank2 = result.getDocuments().get(1).toObject(UserModel.class);
                    UserModel rank3 = result.getDocuments().get(2).toObject(UserModel.class);

                    for (int i = 3; i < result.getDocuments().size(); i++) {
                        UserModel userInfo = result.getDocuments().get(i).toObject(UserModel.class);
                        otherRankers.add(userInfo);
                    }
                    callback.onLeaderBoardDataFetched(new LeaderBoardModel(rank1, rank2, rank3, otherRankers));
                })
                .addOnFailureListener(e -> callback.onLeaderBoardDataFetched(null));
    }

    // Kiểm tra sự tồn tại của tài liệu
    public Task<Boolean> doesDocumentExist(String documentId) {
        return mFireStore.collection(Constants.user).document(documentId).get()
                .continueWith(task -> task.isSuccessful() && task.getResult().exists());
    }

    // Lấy ID người dùng hiện tại
    public static String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser != null ? currentUser.getUid() : "";
    }

    // Interface để callback thông tin người dùng
    public interface UserInfoCallback {
        void onUserInfoFetched(UserModel userInfo);
    }

    // Interface để callback thứ hạng người dùng
    public interface UserRankCallback {
        void onUserRankFetched(Integer rank);
    }

    // Interface để callback dữ liệu bảng xếp hạng
    public interface LeaderBoardDataCallback {
        void onLeaderBoardDataFetched(LeaderBoardModel leaderBoardModel);
    }
}
