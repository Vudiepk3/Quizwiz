package com.example.quizwiz.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.databinding.ActivityMainBinding;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.example.quizwiz.models.UserModel;
import com.example.quizwiz.quiz.QuizClass;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy thông tin người dùng từ Firebase
        FireBaseClass.getInstance();
        FireBaseClass.getUserInfo(new FireBaseClass.UserInfoCallback() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onUserInfoFetched(UserModel userInfo) {
                if (userInfo != null) {
                    binding.tvUserPoints.setText(String.format("%.2f", userInfo.getAllTimeScore()));
                    binding.tvUserName.setText("Hi, " + userInfo.getName());
                    FireBaseClass.getInstance().setProfileImage(userInfo.getImage(), binding.mainProfileImage);
                }
            }
        });

        // Lấy xếp hạng của người dùng từ Firebase
        FireBaseClass.getInstance();
        FireBaseClass.getUserRank(Constants.ALL_TIME_SCORE, new FireBaseClass.UserRankCallback() {
            @Override
            public void onUserRankFetched(Integer rank) {
                if (rank != null) {
                    binding.tvUserRanking.setText(rank.toString());
                }
            }
        });

        // Cấu hình RecyclerView cho danh sách chủ đề câu hỏi
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.rvCategoryList.setLayoutManager(gridLayoutManager);
        QuizClass quizClass = new QuizClass(this);
        quizClass.setRecyclerView(binding.rvCategoryList);

        // Xử lý sự kiện khi người dùng nhấn vào nút "Random Quiz"
        binding.btnRandomQuiz.setOnClickListener(view -> quizClass.getQuizList(10, null, null, null));

        // Xử lý sự kiện khi người dùng nhấn vào nút "Custom Quiz"
        binding.btnCustomQuiz.setOnClickListener(view -> startActivity(new Intent(this, CustomQuizActivity.class)));

        // Xử lý sự kiện khi người dùng nhấn vào phần thống kê người chơi
        binding.cvPlayerStats.setOnClickListener(view -> startActivity(new Intent(this, LeaderBoardActivity.class)));

        // Xử lý sự kiện khi người dùng nhấn vào hình đại diện của mình
        binding.mainProfileImage.setOnClickListener(view -> startActivity(new Intent(this, UserProfileActivity.class)));
    }
}
