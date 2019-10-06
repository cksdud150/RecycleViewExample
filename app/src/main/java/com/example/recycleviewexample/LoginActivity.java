package com.example.recycleviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleviewexample.model.DefaultResponse;
import com.example.recycleviewexample.model.Diary;
import com.example.recycleviewexample.model.LoginResponse;
import com.example.recycleviewexample.model.User;
import com.example.recycleviewexample.service.UserService;
import com.example.recycleviewexample.util.SharedPreferenceUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText idEditText;
    private EditText passwordEditText;
    private TextView textView;
    private Button loginButton;

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setListener();
    }
    private void init() {
        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        textView = findViewById(R.id.textView);
        loginButton = findViewById(R.id.loginButton);

        SharedPreferenceUtil.init(this);
        String token = SharedPreferenceUtil.getString("token");
        if(!token.equals("")){
            startActivity(new Intent(this, DiaryActivity.class));
            finish();
        }
    }
    private void setListener(){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService userService = RetrofitUtil.retrofit.create(UserService.class);
                userService.login(new User(idEditText.getText().toString(),passwordEditText.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loginResponse -> {
                            String token = loginResponse.getAuth().getToken();
                            SharedPreferenceUtil.setStringValue("token",token);
                            SharedPreferenceUtil.setStringValue("username",loginResponse.getUser().getUsername());
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                            navigateToMain();
                        },t ->{
                            if(t instanceof HttpException){
                                Log.e(TAG,"error code : " + ((HttpException) t).code());
                                Log.e(TAG,"error code : " + t.getLocalizedMessage());
                            }

                        });

            }
        });

    }
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
