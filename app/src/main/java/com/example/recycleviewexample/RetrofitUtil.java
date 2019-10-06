package com.example.recycleviewexample;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.recycleviewexample.util.SharedPreferenceUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.MultipartBody.*;

public class RetrofitUtil {
    public static Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://purplebeen.kr:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();


    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static Retrofit getLoginRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("authorization", SharedPreferenceUtil.getString("token"))
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://purplebeen.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static Part createMultipartBody(@NonNull File file, String name) {
        RequestBody mFile = RequestBody.create(MediaType.parse("images/*"), file);
        Part fileToUpload = Part.createFormData(name, file.getName(), mFile);
        return fileToUpload;
    }
    public static RequestBody createRequestBody(@NonNull String value) {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value);
    }
}