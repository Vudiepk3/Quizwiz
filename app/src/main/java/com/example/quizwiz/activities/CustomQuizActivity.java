package com.example.quizwiz.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizwiz.constants.Constants;
import com.example.quizwiz.databinding.ActivityCustomQuizBinding;
import com.example.quizwiz.quiz.QuizClass;

import java.util.Arrays;
import java.util.List;

public class CustomQuizActivity extends AppCompatActivity {

    private ActivityCustomQuizBinding binding;
    private int amount = 10;
    private Integer category = null;
    private String difficulty = null;
    private String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Xử lý SeekBar
        handleSeekBar();

        // Thiết lập Adapter cho Spinner
        String[] categoryList = Constants.getCategoryStringArray().toArray(new String[0]);
        binding.categorySpinner.setAdapter(getSpinnerAdapter(Arrays.asList(categoryList)));
        binding.difficultySpinner.setAdapter(getSpinnerAdapter(Constants.difficultyList));
        binding.typeSpinner.setAdapter(getSpinnerAdapter(Constants.typeList));

        // Xử lý sự kiện chọn item cho Spinner
        handleCategorySpinner();
        handleDifficultySpinner();
        handleTypeSpinner();

        // Khởi tạo QuizClass và thiết lập sự kiện click cho nút bắt đầu quiz
        QuizClass quizClass = new QuizClass(this);
        binding.startCustomQuiz.setOnClickListener(view -> quizClass.getQuizList(amount, category, difficulty, type));
    }

    // Xử lý SeekBar để cập nhật số lượng câu hỏi
    private void handleSeekBar() {
        binding.seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amount = progress;
                String text = "Amount: " + progress;
                binding.tvAmount.setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không làm gì khi bắt đầu kéo SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Không làm gì khi dừng kéo SeekBar
            }
        });
    }

    // Xử lý sự kiện chọn item cho Spinner category
    private void handleCategorySpinner() {
        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (position == 0) ? null : position + 8;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });
    }

    // Xử lý sự kiện chọn item cho Spinner difficulty
    private void handleDifficultySpinner() {
        binding.difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficulty = (position == 0) ? null : position == 1 ? "easy" : position == 2 ? "medium" : "hard";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });
    }

    // Xử lý sự kiện chọn item cho Spinner type
    private void handleTypeSpinner() {
        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (position == 0) ? null : position == 1 ? "multiple" : "boolean";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có gì được chọn
            }
        });
    }

    // Tạo và trả về SpinnerAdapter cho các Spinner
    private SpinnerAdapter getSpinnerAdapter(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
