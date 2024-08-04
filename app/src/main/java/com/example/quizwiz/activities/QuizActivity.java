package com.example.quizwiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.quizwiz.R;
import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.databinding.ActivityQuizBinding;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.example.quizwiz.models.QuizResult;
import com.example.quizwiz.models.ResultModel;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private ArrayList<QuizResult> questionList;
    private int position = 0;
    private boolean allowPlaying = true;
    private CountDownTimer timer;
    private final ArrayList<ResultModel> resultList = new ArrayList<>();
    private int timeLeft = 0;
    private double score = 0.0;
    private String correctAnswer;
    private List<String> optionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy danh sách câu hỏi từ intent
        questionList = (ArrayList<QuizResult>) getIntent().getSerializableExtra("questionList");
        binding.pbProgress.setMax(questionList.size());

        // Đặt câu hỏi và các tuỳ chọn
        setQuestion();
        setOptions();
        startTimer();

        // Thiết lập hiển thị tiến trình
        binding.tvProgress.setText("1/" + questionList.size());

        // Xử lý sự kiện nút "Next"
        binding.btnNext.setOnClickListener(v -> onNext());

        // Tạo background cho các lựa chọn sai
        final android.graphics.drawable.Drawable redBg = ContextCompat.getDrawable(this, R.drawable.red_button_bg);

        // Xử lý sự kiện khi chọn các lựa chọn
        View.OnClickListener optionClickListener = view -> {
            if (allowPlaying) {
                timer.cancel();
                view.setBackground(redBg);
                showCorrectAnswer();
                setScore((Button) view);
                allowPlaying = false;
            }
        };

        binding.option1.setOnClickListener(optionClickListener);
        binding.option2.setOnClickListener(optionClickListener);
        binding.option3.setOnClickListener(optionClickListener);
        binding.option4.setOnClickListener(optionClickListener);
    }

    private void onNext() {
        // Tạo kết quả của câu hỏi hiện tại
        ResultModel resultModel = new ResultModel(20 - timeLeft, questionList.get(position).getType(),
                questionList.get(position).getDifficulty(), score);
        resultList.add(resultModel);

        score = 0.0;

        // Chuyển sang câu hỏi tiếp theo nếu còn câu hỏi
        if (position < questionList.size() - 1) {
            timer.cancel();
            position++;
            setQuestion();
            setOptions();
            binding.pbProgress.setProgress(position + 1);
            binding.tvProgress.setText((position + 1) + "/" + questionList.size());
            resetButtonBackground();
            allowPlaying = true;
            startTimer();
        } else {
            endGame();
        }
    }

    private void setQuestion() {
        // Giải mã chuỗi câu hỏi
        String decodedQuestion = Constants.decodeHtmlString(questionList.get(position).getQuestion());
        binding.tvQuestion.setText(decodedQuestion);
    }

    private void setOptions() {
        QuizResult question = questionList.get(position);
        android.util.Pair<String, List<String>> temp = Constants.getRandomOptions(question.getCorrectAnswer(), question.getIncorrectAnswers());
        optionList = temp.second;
        correctAnswer = temp.first;

        binding.option1.setText(optionList.get(0));
        binding.option2.setText(optionList.get(1));

        if (question.getType().equals("multiple")) {
            binding.option3.setVisibility(View.VISIBLE);
            binding.option4.setVisibility(View.VISIBLE);
            binding.option3.setText(optionList.get(2));
            binding.option4.setText(optionList.get(3));
        } else {
            binding.option3.setVisibility(View.GONE);
            binding.option4.setVisibility(View.GONE);
        }
    }

    private void setScore(Button button) {
        if (correctAnswer.equals(button.getText().toString())) {
            score = getScore();
        }
    }

    private double getScore() {
        // Tính điểm dựa trên loại câu hỏi (boolean hoặc multiple choice)
        double score1 = questionList.get(position).getType().equals("boolean") ? 0.5 : 1.0;

        // Tính điểm dựa trên thời gian còn lại
        double score2 = timeLeft / 20.0;

        // Tính điểm dựa trên độ khó của câu hỏi
        double score3;
        if (questionList.get(position).getDifficulty().equals("easy")) {
            score3 = 1.0;
        } else if (questionList.get(position).getDifficulty().equals("medium")) {
            score3 = 2.0;
        } else {
            score3 = 3.0;
        }

        // Tổng điểm là tổng của các điểm thành phần
        return score1 + score2 + score3;
    }


    private void showCorrectAnswer() {
        android.graphics.drawable.Drawable blueBg = ContextCompat.getDrawable(this, R.drawable.blue_button_bg);

        if (correctAnswer.equals(optionList.get(0))) {
            binding.option1.setBackground(blueBg);
        } else if (correctAnswer.equals(optionList.get(1))) {
            binding.option2.setBackground(blueBg);
        } else if (correctAnswer.equals(optionList.get(2))) {
            binding.option3.setBackground(blueBg);
        } else {
            binding.option4.setBackground(blueBg);
        }
    }

    private void resetButtonBackground() {
        android.graphics.drawable.Drawable grayBg = ContextCompat.getDrawable(this, R.drawable.gray_button_bg);
        binding.option1.setBackground(grayBg);
        binding.option2.setBackground(grayBg);
        binding.option3.setBackground(grayBg);
        binding.option4.setBackground(grayBg);
    }

    private void startTimer() {
        binding.circularProgressBar.setMax(20);
        binding.circularProgressBar.setProgress(20);

        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.circularProgressBar.incrementProgressBy(-1);
                binding.tvTimer.setText(String.valueOf(millisUntilFinished / 1000));
                timeLeft = (int) (millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                showCorrectAnswer();
                allowPlaying = false;
            }
        }.start();
    }

    private void endGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("resultList", resultList);
        startActivity(intent);
        finish();
    }
}
