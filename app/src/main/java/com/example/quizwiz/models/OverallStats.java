package com.example.quizwiz.models;

public class OverallStats {
    private int totalNumOfQuestions;
    private int totalNumOfPendingQuestions;
    private int totalNumOfVerifiedQuestions;
    private int totalNumOfRejectedQuestions;

    public OverallStats(int totalNumOfQuestions, int totalNumOfPendingQuestions, int totalNumOfVerifiedQuestions, int totalNumOfRejectedQuestions) {
        this.totalNumOfQuestions = totalNumOfQuestions;
        this.totalNumOfPendingQuestions = totalNumOfPendingQuestions;
        this.totalNumOfVerifiedQuestions = totalNumOfVerifiedQuestions;
        this.totalNumOfRejectedQuestions = totalNumOfRejectedQuestions;
    }

    public OverallStats() {
    }

    public int getTotalNumOfQuestions() {
        return totalNumOfQuestions;
    }

    public void setTotalNumOfQuestions(int totalNumOfQuestions) {
        this.totalNumOfQuestions = totalNumOfQuestions;
    }

    public int getTotalNumOfPendingQuestions() {
        return totalNumOfPendingQuestions;
    }

    public void setTotalNumOfPendingQuestions(int totalNumOfPendingQuestions) {
        this.totalNumOfPendingQuestions = totalNumOfPendingQuestions;
    }

    public int getTotalNumOfVerifiedQuestions() {
        return totalNumOfVerifiedQuestions;
    }

    public void setTotalNumOfVerifiedQuestions(int totalNumOfVerifiedQuestions) {
        this.totalNumOfVerifiedQuestions = totalNumOfVerifiedQuestions;
    }

    public int getTotalNumOfRejectedQuestions() {
        return totalNumOfRejectedQuestions;
    }

    public void setTotalNumOfRejectedQuestions(int totalNumOfRejectedQuestions) {
        this.totalNumOfRejectedQuestions = totalNumOfRejectedQuestions;
    }
}