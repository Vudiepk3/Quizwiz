package com.example.quizwiz.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizwiz.R;
import com.example.quizwiz.models.ResultModel;
import java.util.ArrayList;

public class QuizSummeryAdapter extends RecyclerView.Adapter<QuizSummeryAdapter.ViewHolder> {

    private final ArrayList<ResultModel> list;

    // Constructor của QuizSummeryAdapter
    public QuizSummeryAdapter(ArrayList<ResultModel> list) {
        this.list = list;
    }

    // Tạo ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_stats_item, parent, false);
        return new ViewHolder(view);
    }

    // Gắn dữ liệu vào ViewHolder
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultModel item = list.get(position);
        holder.tvPosition.setText((position + 1) + ".");
        holder.tvTime.setText(item.getTime());
        holder.tvType.setText(item.getType());
        holder.tvDifficulty.setText(item.getDifficulty());
        holder.tvScore.setText(String.format("%.2f", item.getScore()));

    }

    // Trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return list.size();
    }

    // ViewHolder chứa các view con
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosition;
        TextView tvTime;
        TextView tvType;
        TextView tvDifficulty;
        TextView tvScore;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvPosition = view.findViewById(R.id.tvSummeryPosition);
            tvTime = view.findViewById(R.id.tvSummeryTime);
            tvType = view.findViewById(R.id.tvSummeryType);
            tvDifficulty = view.findViewById(R.id.tvSummeryDifficulty);
            tvScore = view.findViewById(R.id.tvSummeryScore);
        }
    }
}
