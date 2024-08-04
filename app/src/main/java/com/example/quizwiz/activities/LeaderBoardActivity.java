package com.example.quizwiz.activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.quizwiz.R;
import com.example.quizwiz.adapter.LeaderBoardAdapter;
import com.example.quizwiz.constants.Base;
import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.databinding.ActivityLeaderBoardBinding;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.example.quizwiz.models.LeaderBoardModel;
import com.example.quizwiz.models.UserModel;

public class LeaderBoardActivity extends AppCompatActivity {

    private ActivityLeaderBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thiết lập LayoutManager cho RecyclerView
        binding.leaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Thiết lập bảng xếp hạng với dữ liệu tất cả thời gian
        setLeaderBoard(Constants.ALL_TIME_SCORE);

        // Lắng nghe sự kiện thay đổi của RadioGroup
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Kiểm tra ID của radio button được chọn
            if (checkedId == R.id.rbAllTime) {
                // Nếu chọn rbAllTime, thiết lập bảng xếp hạng theo điểm allTimeScore
                setLeaderBoard(Constants.ALL_TIME_SCORE);
            } else if (checkedId == R.id.rbWeekly) {
                // Nếu chọn rbWeekly, thiết lập bảng xếp hạng theo điểm weeklyScore
                setLeaderBoard(Constants.weeklyScore);
            } else if (checkedId == R.id.rbMonthly) {
                // Nếu chọn rbMonthly, thiết lập bảng xếp hạng theo điểm monthlyScore
                setLeaderBoard(Constants.monthlyScore);
            }
        });

    }

    // Hàm thiết lập bảng xếp hạng theo loại (all time, weekly, monthly)
    private void setLeaderBoard(String type) {
        FireBaseClass fireBaseClass = new FireBaseClass();
        fireBaseClass.getLeaderBoardData(type, new FireBaseClass.LeaderBoardDataCallback() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onLeaderBoardDataFetched(LeaderBoardModel leaderBoardModel) {
                if (leaderBoardModel != null) {
                    UserModel r1 = leaderBoardModel.getRank1();
                    UserModel r2 = leaderBoardModel.getRank2();
                    UserModel r3 = leaderBoardModel.getRank3();

                    // Thiết lập dữ liệu cho hạng 1, 2, 3
                    binding.tvRank1Name.setText(r1.getName());
                    binding.tvRank1Points.setText(String.format("%.2f", Base.desiredScore(r1, type)));
                    binding.tvRank2Name.setText(r2.getName());
                    binding.tvRank2Points.setText(String.format("%.2f", Base.desiredScore(r2, type)));
                    binding.tvRank3Name.setText(r3.getName());
                    binding.tvRank3Points.setText(String.format("%.2f", Base.desiredScore(r3, type)));

                    // Đặt hình ảnh profile cho hạng 1, 2, 3
                    fireBaseClass.setProfileImage(r1.getImage(), binding.ivRank1);
                    fireBaseClass.setProfileImage(r2.getImage(), binding.ivRank2);
                    fireBaseClass.setProfileImage(r3.getImage(), binding.ivRank3);

                    // Thiết lập Adapter cho RecyclerView
                    LeaderBoardAdapter adapter = new LeaderBoardAdapter(leaderBoardModel.getOtherRanks(), type);
                    binding.leaderBoardRecyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
