package com.example.quizwiz.models;

import java.util.List;

public class LeaderBoardModel {
    private UserModel rank1,rank2,rank3;
    private List<UserModel> otherRanks;

    public LeaderBoardModel(UserModel rank1, UserModel rank2, UserModel rank3, List<UserModel> otherRanks) {
        this.rank1 = rank1;
        this.rank2 = rank2;this.rank3 = rank3;
        this.otherRanks = otherRanks;
    }

    // Getters và Setters cho các thuộc tính
    public UserModel getRank1() {
        return rank1;
    }

    public void setRank1(UserModel rank1) {
        this.rank1 = rank1;
    }

    public UserModel getRank2() {
        return rank2;
    }

    public void setRank2(UserModel rank2) {
        this.rank2 = rank2;
    }

    public UserModel getRank3() {
        return rank3;
    }

    public void setRank3(UserModel rank3) {
        this.rank3 = rank3;
    }

    public List<UserModel> getOtherRanks() {
        return otherRanks;
    }

    public void setOtherRanks(List<UserModel> otherRanks) {
        this.otherRanks = otherRanks;
    }
}
