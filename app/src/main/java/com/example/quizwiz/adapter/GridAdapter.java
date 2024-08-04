package com.example.quizwiz.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizwiz.R;
import com.example.quizwiz.models.CategoryModel;
import com.example.quizwiz.models.QuestionStats;
import com.example.quizwiz.models.CategoryStats;
import java.util.List;
import java.util.Map;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private final List<CategoryModel> items;
    private final Map<String, CategoryStats> categoryStat;
    private OnClickListener onClickListener;

    // Constructor cho GridAdapter

    public GridAdapter(List<CategoryModel> items, Map<String, CategoryStats> categoryStat) {
        // Khởi tạo các thành phần trong GridAdapter
        this.items = items;
        this.categoryStat = categoryStat;
    }


    // Tạo ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new ViewHolder(view);
    }

    // Gắn dữ liệu vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel item = items.get(position);
        holder.tvCategoryName.setText(item.getName());
        Integer count = categoryStat.get(item.getId()) == null ? 0 : categoryStat.get(item.getId()).getTotalNumOfQuestions();
        holder.tvQuestionCount.setText(count + " questions");

        // Thiết lập sự kiện click cho itemView
        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(Integer.parseInt(item.getId()));
            }
        });
    }

    // Trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder chứa các view con
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvCategoryName;
        TextView tvQuestionCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_category_icon);
            tvCategoryName = itemView.findViewById(R.id.tv_Category_name);
            tvQuestionCount = itemView.findViewById(R.id.tv_no_of_questions);
        }
    }

    // Thiết lập OnClickListener
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Giao diện OnClickListener
    public interface OnClickListener {
        void onClick(int id);
    }
}
