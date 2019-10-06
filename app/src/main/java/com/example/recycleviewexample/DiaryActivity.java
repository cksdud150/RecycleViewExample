package com.example.recycleviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recycleviewexample.model.Diary;
import com.example.recycleviewexample.model.DiaryResponse;
import com.example.recycleviewexample.service.UserService;
import com.example.recycleviewexample.util.SharedPreferenceUtil;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.http.POST;

public class DiaryActivity extends AppCompatActivity {
    private EditText titleText;
    private EditText contentText;
    private Button saveButton;

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        init();
        setListener();
    }

    private void init(){
        titleText = findViewById(R.id.titleText);
        contentText = findViewById(R.id.contentText);
        saveButton = findViewById(R.id.saveButton);

        SharedPreferenceUtil.init(this);
    }
    private void setListener(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diary diary = new Diary(titleText.getText().toString(),contentText.getText().toString(), SharedPreferenceUtil.getString("username"));
                UserService userService = RetrofitUtil.getLoginRetrofit().create(UserService.class);
                userService.diaries(diary)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(diaryResponse -> {
                            Toast.makeText(getApplicationContext(),diaryResponse.getResult().getMessage(),Toast.LENGTH_SHORT).show();
                            navigateToMain();
                            finish();
                        },t ->{
                            if(t instanceof HttpException){
                                Log.e(TAG,"error code : " + ((HttpException) t).code());
                                Log.e(TAG,"error code : " + t.getLocalizedMessage());
                            }

                        });
//                Call<DiaryResponse> call = userService.diaries(diary);
//
//                call.enqueue(new Callback<DiaryResponse>() {
//                    @Override
//                    public void onResponse(Call<DiaryResponse> call, Response<DiaryResponse> response) {
//                        if(response.body().getResult().getSuccess().equals("true")){
//                            Toast.makeText(getApplicationContext(),response.body().getResult().getMessage(),Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(DiaryActivity.this, MainActivity.class));
//                            finish();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<DiaryResponse> call, Throwable t) {
//                        Log.e("error", t.getLocalizedMessage());
//
//                    }
//                });

            }
        });
    }
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
