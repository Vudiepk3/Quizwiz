package com.example.quizwiz.constants;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.quizwiz.R;
import com.example.quizwiz.models.UserModel;

public class Base {

    public static Dialog showProgressBar(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_progress_bar);
        dialog.show();
        return dialog;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static double desiredScore(UserModel userInfo, String type) {
        switch (type) {
            case Constants.ALL_TIME_SCORE:
                return userInfo.getAllTimeScore();
            case Constants.weeklyScore:
                return userInfo.getWeeklyScore();
            case Constants.monthlyScore:
                return userInfo.getMonthlyScore();
            default:
                return 0.0;
        }
    }

}
