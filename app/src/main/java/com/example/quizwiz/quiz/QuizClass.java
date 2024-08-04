package com.example.quizwiz.quiz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizwiz.activities.QuizActivity;
import com.example.quizwiz.adapter.GridAdapter;
import com.example.quizwiz.constants.Base;
import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.models.CategoryStats;
import com.example.quizwiz.models.QuestionStats;
import com.example.quizwiz.models.QuizResponse;
import com.example.quizwiz.models.QuizResult;
import com.example.quizwiz.retrofit.QuestionStatsService;
import com.example.quizwiz.retrofit.QuizService;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizClass {

    private final Context context;

    // Constructor nhận vào Context
    public QuizClass(Context context) {
        this.context = context;
    }

    // Hàm lấy danh sách câu hỏi quiz
    public void getQuizList(int amount, Integer category, String difficulty, String type) {
        if (Constants.isNetworkAvailable(context)) {
            // Hiển thị ProgressBar
            Dialog pbDialog = Base.showProgressBar(context);

            // Tạo đối tượng Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://opentdb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Tạo service để gọi API
            QuizService service = retrofit.create(QuizService.class);
            Call<QuizResponse> dataCall = service.getQuiz(amount, category, difficulty, type);

            // Gọi API và xử lý callback
            dataCall.enqueue(new Callback<QuizResponse>() {
                @Override
                public void onResponse(@NonNull Call<QuizResponse> call, @NonNull Response<QuizResponse> response) {
                    // Hủy ProgressBar
                    pbDialog.dismiss();
                    if (response.isSuccessful()) {
                        QuizResponse responseData = response.body();
                        assert responseData != null;
                        ArrayList<QuizResult> questionList = new ArrayList<>(responseData.getResults());
                        if (!questionList.isEmpty()) {
                            Intent intent = new Intent(context, QuizActivity.class);
                            intent.putParcelableArrayListExtra("questionList", (ArrayList<? extends Parcelable>) questionList);
                            context.startActivity(intent);
                        } else {
                            Base.showToast(context, "We are sorry, but currently " +
                                    "we don't have " + amount + " question for this particular selection");
                            Log.e("debug", responseData.toString());
                        }
                    } else {
                        Base.showToast(context, "Error Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<QuizResponse> call, @NonNull Throwable t) {
                    pbDialog.dismiss();

                    Base.showToast(context, "CallBack Failure");
                }
            });
        } else {
            Base.showToast(context, "Network is not Available");
        }
    }

    // Hàm thiết lập RecyclerView
    public void setRecyclerView(RecyclerView recyclerView) {
        if (Constants.isNetworkAvailable(context)) {
            // Hiển thị ProgressBar
            Dialog pbDialog = Base.showProgressBar(context);

            // Tạo đối tượng Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://opentdb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Tạo service để gọi API
            QuestionStatsService service = retrofit.create(QuestionStatsService.class);
            Call<QuestionStats> dataCall = service.getData();

            // Gọi API và xử lý callback
            dataCall.enqueue(new Callback<QuestionStats>() {
                @Override
                public void onResponse(@NonNull Call<QuestionStats> call, @NonNull Response<QuestionStats> response) {
                    // Hủy ProgressBar
                    pbDialog.dismiss();

                    if (response.isSuccessful()) {
                        QuestionStats questionStats = response.body();
                        assert questionStats != null;
                        java.util.Map<String, CategoryStats> categoryMap = questionStats.getCategories();
                        GridAdapter adapter = new GridAdapter(Constants.getCategoryItemList(), categoryMap);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnClickListener(new GridAdapter.OnClickListener() {
                            @Override
                            public void onClick(int id) {
                                getQuizList(10, id, null, null);
                            }
                        });
                    } else {
                        Base.showToast(context, "Error Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<QuestionStats> call, @NonNull Throwable t) {
                    pbDialog.dismiss();
                    Base.showToast(context, "Network is not Available");
                }
            });
        } else {
            Base.showToast(context, "Network is not Available");
        }
    }
}
