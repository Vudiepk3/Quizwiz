package com.example.quizwiz.activities;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.quizwiz.adapter.QuizSummeryAdapter;
import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.databinding.ActivityResultBinding;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.example.quizwiz.models.ResultModel;
import com.example.quizwiz.models.UserModel;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy danh sách kết quả từ Intent
        ArrayList<ResultModel> resultList = getIntent().getParcelableArrayListExtra("resultList");

        // Cập nhật điểm số của người dùng lên Firebase
        assert resultList != null;
        FireBaseClass.updateScore(getFinalScore(resultList));

        // Lấy thông tin người dùng từ Firebase và hiển thị trên giao diện
        FireBaseClass.getUserInfo(new FireBaseClass.UserInfoCallback() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onUserInfoFetched(UserModel userInfo) {
                if (userInfo != null) {
                    binding.tvUserPoints.setText(format("%.2f", userInfo.getAllTimeScore()));
                }
            }
        });

        // Lấy thứ hạng của người dùng và hiển thị trên giao diện
        FireBaseClass.getUserRank(Constants.ALL_TIME_SCORE, new FireBaseClass.UserRankCallback() {
            @Override
            public void onUserRankFetched(Integer rank) {
                if (rank != null)
                    binding.tvUserRanking.setText(rank.toString());
            }
        });

        // Thiết lập RecyclerView cho danh sách kết quả
        binding.rvSummery.setLayoutManager(new LinearLayoutManager(this));
        QuizSummeryAdapter adapter = new QuizSummeryAdapter(resultList);
        binding.rvSummery.setAdapter(adapter);

        // Hiển thị tổng điểm
        binding.tvTotalScore.setText("Total Score: " + format("%.2f", getFinalScore(resultList)));

        // Xử lý sự kiện nút trở về trang chính
        binding.btnHome.setOnClickListener(v -> finish());
    }

    // Tính toán tổng điểm từ danh sách kết quả
    @SuppressLint("DefaultLocale")
    private double getFinalScore(ArrayList<ResultModel> list) {
        double result = 0.0;
        for (ResultModel item : list)
            result += item.getScore();
        return Double.parseDouble(format("%.2f", result));
    }
}
