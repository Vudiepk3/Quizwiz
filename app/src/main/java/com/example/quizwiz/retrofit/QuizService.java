package com.example.quizwiz.retrofit;

import com.example.quizwiz.models.QuestionStats;
import com.example.quizwiz.models.QuizResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizService {
    @GET("api.php")
    Call<QuizResponse> getQuiz(
            @Query("amount") int amount,
            @Query("category") Integer category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );
}

