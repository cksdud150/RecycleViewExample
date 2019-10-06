package com.example.recycleviewexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recycleviewexample.model.DefaultResponse;
import com.example.recycleviewexample.model.User;
import com.example.recycleviewexample.service.UserService;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText idEditText;
    private EditText editPassword;
    private EditText editPasswordAgain;
    private CircleImageView profile;
    private Button button;

    private Uri uri;

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setListeners();
    }

    private void init() {
        idEditText = findViewById(R.id.idEditText);
        editPassword = findViewById(R.id.editPassword);
        editPasswordAgain = findViewById(R.id.editPasswordAgain);
        profile = findViewById(R.id.profile);
        button = findViewById(R.id.button);
    }

    private void setListeners() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImage();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPassword.equals(editPasswordAgain)) {
                    File file = new File(getRealPathFromUri(uri));
                    UserService userService = RetrofitUtil.retrofit.create(UserService.class);
                    userService.register(new User(idEditText.getText().toString(), editPassword.getText().toString()),
                            RetrofitUtil.createMultipartBody(file, "profile"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(DefaultResponse -> {
                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                finish();
                            },t ->{
                                if(t instanceof HttpException){
                                    Log.e(TAG,"error code : " + ((HttpException) t).code());
                                    Log.e(TAG,"error code : " + t.getLocalizedMessage());
                                }

                            });
//                    call.enqueue(new Callback<DefaultResponse>() {
//                        @Override
//                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                            if (response.body().getResult().getSuccess().equals("true")) {
//                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Log.e("error", response.body().getResult().getMessage());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                            Log.e("error", t.getLocalizedMessage());
//                        }
//                    });
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void requestImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3001);
        } else {
            imageIntent();
        }
    }

    private void imageIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3001: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    imageIntent();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000 && resultCode == RESULT_OK) {
            uri = data.getData();
            Glide.with(getApplicationContext()).load(uri).into(profile);
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

}

