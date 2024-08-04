package com.example.quizwiz.models;

import java.util.List;

public class QuizResponse {
    private int responseCode;
    private List<QuizResult> results;

    public QuizResponse(int responseCode, List<QuizResult> results) {
        this.responseCode = responseCode;
        this.results = results;
    }

    // Getters và Setters cho responseCode và results
    public int intgetResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<QuizResult> getResults() {
        return results;
    }

    public void setResults(List<QuizResult> results) {
        this.results = results;
    }
}
