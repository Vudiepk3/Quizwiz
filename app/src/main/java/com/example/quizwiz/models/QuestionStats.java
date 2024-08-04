package com.example.quizwiz.models;

import java.util.Map;

public class QuestionStats {
    public OverallStats overall;
    public Map<String, CategoryStats> categories;

    public QuestionStats(OverallStats overall, Map<String, CategoryStats> categories) {
        this.overall = overall;
        this.categories = categories;
    }

    // Getters và Setters cho overall và categories
    public OverallStats getOverall() {return overall;
    }

    public void setOverall(OverallStats overall) {
        this.overall = overall;
    }

    public Map<String, CategoryStats> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, CategoryStats> categories) {
        this.categories = categories;
    }


}
