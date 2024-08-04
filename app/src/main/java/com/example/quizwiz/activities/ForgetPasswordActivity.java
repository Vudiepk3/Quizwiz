package com.example.quizwiz.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizwiz.constants.Base;
import com.example.quizwiz.databinding.ActivityForgetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Khi người dùng nhấn vào nút "Submit"
        binding.btnForgotPasswordSubmit.setOnClickListener(v -> resetPassword());
    }

    // Kiểm tra tính hợp lệ của biểu mẫu
    private boolean validateForm(String email) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailForgetPassword.setError("Enter valid email address");
            return false;
        }
        return true;
    }

    // Đặt lại mật khẩu
    private void resetPassword() {
        String email = binding.etForgotPasswordEmail.getText().toString();

        if (validateForm(email)) {
            // Hiển thị thanh tiến trình
            Base.showProgressBar(this);
                // Gửi email đặt lại mật khẩu
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Đặt lại thành công
                                finish();
                                binding.tilEmailForgetPassword.setVisibility(View.GONE);
                                binding.tvSubmitMsg.setVisibility(View.VISIBLE);
                                binding.btnForgotPasswordSubmit.setVisibility(View.GONE);
                            } else {
                                // Đặt lại không thành công
                                finish();
                                Base.showToast(this, "Can not reset your password. Try after sometime");
                            }
                        });
            }
        }
}
