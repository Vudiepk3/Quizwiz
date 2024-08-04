package com.example.quizwiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizwiz.constants.Base;
import com.example.quizwiz.databinding.ActivitySignUpBinding;
import com.example.quizwiz.fireBase.FireBaseClass;
import com.example.quizwiz.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        // Khi người dùng nhấn vào "Đăng nhập"
        binding.tvLoginPage.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });

        // Khi người dùng nhấn vào nút "Đăng ký"
        binding.btnSignUp.setOnClickListener(v -> registerUser());
    }

    // Đăng ký người dùng mới
    private void registerUser() {
        String name = binding.etSinUpName.getText().toString();
        String email = binding.etSinUpEmail.getText().toString();
        String password = binding.etSinUpPassword.getText().toString();

        if (validateForm(name, email, password)) {
            // Hiển thị thanh tiến trình
            Base.showProgressBar(this);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Lấy ID người dùng
                                FirebaseUser user = task.getResult().getUser();
                                assert user != null;
                                String userId = user.getUid();

                                // Tạo đối tượng UserModel với thông tin người dùng
                                UserModel userInfo = new UserModel(name, userId, email);

                                // Đăng ký thông tin người dùng vào Firestore
                                new FireBaseClass().registerUser(userInfo);

                                Base.showToast(this, "User Id created successfully");
                                finish();

                                // Chuyển sang MainActivity sau khi đăng ký thành công
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            } else {
                                Base.showToast(this, "User Id not created. Try again later");
                                finish();
                            }
                        });

        }
    }

    // Xác thực biểu mẫu đăng ký
    private boolean validateForm(String name, String email, String password) {
        if (TextUtils.isEmpty(name)) {
            binding.tilName.setError("Enter name");
            return false;
        } else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Enter valid email address");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("Enter password");
            return false;
        } else {
            return true;
        }
    }
}
