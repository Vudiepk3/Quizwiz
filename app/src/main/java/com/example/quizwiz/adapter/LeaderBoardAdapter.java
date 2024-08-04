package com.example.quizwiz.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizwiz.R;
import com.example.quizwiz.constants.Base;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.example.quizwiz.models.UserModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    private List<UserModel> itemList;
    private String type;

    // Constructor cho LeaderBoardAdapter
    public LeaderBoardAdapter(List<UserModel> itemList, String type) {
        this.itemList = itemList;
        this.type = type;
    }

    // Tạo ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leader_board_item, parent, false);
        return new ViewHolder(view);
    }

    // Gắn dữ liệu vào ViewHolder
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userInfo = itemList.get(position);
        holder.tvRank.setText(String.valueOf(position + 4));
        holder.tvName.setText(userInfo.getName());
        holder.tvPoints.setText(String.format("%.2f", Base.desiredScore(userInfo, type)));
        new FireBaseClass().setProfileImage(userInfo.getImage(), holder.ivImage);
    }

    // Trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder chứa các view con
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank;
        ShapeableImageView ivImage;
        TextView tvName;
        TextView tvPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tvLeaderboardRank);
            ivImage = itemView.findViewById(R.id.ivLeaderBoardProfilePic);
            tvName = itemView.findViewById(R.id.tvLeaderboardName);
            tvPoints = itemView.findViewById(R.id.tvLeaderboardScore);
        }
    }
}
