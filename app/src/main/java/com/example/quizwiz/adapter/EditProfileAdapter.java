package com.example.quizwiz.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizwiz.R;
import com.example.quizwiz.constants.Base;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;

public class EditProfileAdapter extends BottomSheetDialogFragment {

    private Context context;
    private ShapeableImageView imageView;
    private Uri imageUri = null;
    private String name;
    private EditText editName;
    private Button saveButton;
    private FireBaseClass fireBaseClass;

    public static final int PICK_IMAGE_REQUEST = 1;

    public EditProfileAdapter(Context context) {
        this.context = context;
        this.fireBaseClass = new FireBaseClass(); // Tạo instance của FireBaseClass
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_btm_sheet, container, false);

        imageView = view.findViewById(R.id.editImage);
        editName = view.findViewById(R.id.etUserName);
        saveButton = view.findViewById(R.id.btnSave);

        // Đặt ảnh hồ sơ từ Firebase
        fireBaseClass.setProfileImage("profile_pictures/" + fireBaseClass.getCurrentUserId(), imageView);

        // Khi người dùng nhấn vào hình ảnh, mở thư viện ảnh
        imageView.setOnClickListener(v -> openGallery());

        // Khi người dùng nhấn nút lưu, cập nhật tên và hình ảnh hồ sơ trên Firebase
        saveButton.setOnClickListener(v -> {
            name = editName.getText().toString();
            fireBaseClass.updateProfile(name, imageUri);
            Base.showToast(context, "Profile updated successfully");
            dismiss();
        });

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Lấy URI của hình ảnh đã chọn
            imageUri = data.getData();
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }
        }
    }
}
