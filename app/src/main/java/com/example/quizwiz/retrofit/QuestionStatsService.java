package com.example.quizwiz.retrofit;

import com.example.quizwiz.models.QuestionStats;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionStatsService {
    @GET("api_count_global.php")
    Call<QuestionStats> getData();
}
