package com.example.quizwiz.activities;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizwiz.databinding.ActivityGetStartedBinding;

public class GetStartedActivity extends AppCompatActivity {
    private ActivityGetStartedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cvGetStarted.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });
    }
}