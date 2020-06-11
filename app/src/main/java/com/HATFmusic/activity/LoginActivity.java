package com.HATFmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

import com.HATFmusic.R;
import com.HATFmusic.UserLab;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (null != msg) {
                switch (msg.what) {
                    case UserLab.USER_LOGIN_SUCCESS:
                        loginSuccess();
                        break;
                    case UserLab.USER_LOGIN_PASSWORD_ERROR:
                        loginPasswordError();
                        break;
                    case UserLab.USER_LOGIN_NET_ERROR:
                        loginFailed();
                        break;
                }
            }
        }
    };

    private TextInputLayout username, password;
    private Button loginButton, registerButton;
    private UserLab lab = UserLab.getInstance();

    private void loginSuccess() {
        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG)
                .show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void loginPasswordError() {
        Toast.makeText(LoginActivity.this, "密码错误，请重试！", Toast.LENGTH_LONG)
                .show();
    }

    private void loginFailed() {
        Toast.makeText(LoginActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            TextInputLayout username = findViewById(R.id.username);
            TextInputLayout password = findViewById(R.id.password);
            String u = username.getEditText().getText().toString();
            String p = password.getEditText().getText().toString();
            lab.login(u, p, handler);
        });

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "正在注册！", Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
