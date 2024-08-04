package com.example.quizwiz.models;

public class UserModel {

    private String id;
    private String emailId;
    private String name;
    private String image;
    private double allTimeScore;
    private double weeklyScore;
    private double monthlyScore;
    private double lastGameScore;

    public UserModel() {
        this.id = "";
        this.emailId = "";
        this.name = "";
        this.image = "";
        this.allTimeScore = 0.0;
        this.weeklyScore = 0.0;
        this.monthlyScore = 0.0;
        this.lastGameScore = 0.0;
    }
    public UserModel(String name, String id, String emailId) {
        this.id = id;
        this.emailId = emailId;
        this.name = name;
    }

    public UserModel(String id, String emailId, String name, String image, double allTimeScore, double weeklyScore, double monthlyScore, double lastGameScore) {
        this.id = id;
        this.emailId = emailId;
        this.name = name;
        this.image = image;
        this.allTimeScore = allTimeScore;
        this.weeklyScore = weeklyScore;
        this.monthlyScore = monthlyScore;
        this.lastGameScore = lastGameScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getAllTimeScore() {
        return allTimeScore;
    }

    public void setAllTimeScore(double allTimeScore) {
        this.allTimeScore = allTimeScore;
    }

    public double getWeeklyScore() {
        return weeklyScore;
    }

    public void setWeeklyScore(double weeklyScore) {
        this.weeklyScore = weeklyScore;
    }

    public double getMonthlyScore() {
        return monthlyScore;
    }

    public void setMonthlyScore(double monthlyScore) {
        this.monthlyScore = monthlyScore;
    }

    public double getLastGameScore() {
        return lastGameScore;
    }

    public void setLastGameScore(double lastGameScore) {
        this.lastGameScore = lastGameScore;
    }
}
