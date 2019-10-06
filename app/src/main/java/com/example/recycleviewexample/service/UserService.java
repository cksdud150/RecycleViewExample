package com.example.recycleviewexample.service;

import com.example.recycleviewexample.model.DefaultResponse;
import com.example.recycleviewexample.model.Diary;
import com.example.recycleviewexample.model.DiaryResponse;
import com.example.recycleviewexample.model.LoginResponse;
import com.example.recycleviewexample.model.User;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {
    @Multipart//파일 업로드할수 있게 세팅
    @POST("/users")
    Observable<DefaultResponse> register(@Part("data") User user, @Part MultipartBody.Part profile);//part 첨부파일 보낼때는 파트 // 포스트나 풋  ->  바디 /// os 세븐레이어

    @POST("/sign")
    Observable<LoginResponse> login(@Body User user);

    @POST("/diaries")
    Observable<DiaryResponse> diaries(@Body Diary diary);
}
