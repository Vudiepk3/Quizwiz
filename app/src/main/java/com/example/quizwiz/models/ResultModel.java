package com.example.quizwiz.models;


import android.os.Parcel;
import android.os.Parcelable;

public class ResultModel implements Parcelable {
    int time;
    String type;
    String difficulty;
    double score;

    public ResultModel(int time, String type, String difficulty, double score) {
        this.time = time;
        this.type = type;this.difficulty = difficulty;
        this.score = score;
    }

    protected ResultModel(Parcel in) {
        time = in.readInt();
        type = in.readString();
        difficulty = in.readString();
        score = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(time);
        dest.writeString(type);
        dest.writeString(difficulty);
        dest.writeDouble(score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultModel> CREATOR = new Creator<ResultModel>() {
        @Override
        public ResultModel createFromParcel(Parcel in) {
            return new ResultModel(in);
        }

        @Override
        public ResultModel[] newArray(int size) {
            return new ResultModel[size];
        }
    };

    // Getters và Setters
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}