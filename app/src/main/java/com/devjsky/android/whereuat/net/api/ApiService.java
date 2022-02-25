package com.devjsky.android.whereuat.net.api;

import com.devjsky.android.whereuat.net.pojo.GetApiTest;
import com.devjsky.android.whereuat.net.pojo._CreateMeeting;
import com.devjsky.android.whereuat.net.pojo._GetMeetingInfo;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;
import com.devjsky.android.whereuat.net.pojo._KakaoLogin;
import com.devjsky.android.whereuat.net.pojo._KakaoSignUp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ClassName            ApiService
 * Created by JSky on   2022-02-16
 * <p>
 * Description
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("apiTest.php")
    Call<GetApiTest> getApiTest(

            @Field("mem_token") String mem_token

    );
    @FormUrlEncoded
    @POST("getMemberInfo.php")
    Call<_GetMemberInfo> getMemberInfo(

            @Field("mem_token") String mem_token

    );
    @FormUrlEncoded
    @POST("kakaoLogin.php")
    Call<_KakaoLogin> kakaoLogin(

            @Field("kakao_id") String kakao_id,
            @Field("mem_nickname") String mem_nickname,
            @Field("mem_profile_img_url") String mem_profile_img_url,
            @Field("mem_email") String mem_email

    );
    @FormUrlEncoded
    @POST("kakaoSignUp.php")
    Call<_KakaoSignUp> kakaoSignUp(

            @Field("kakao_id") String kakao_id,
            @Field("mem_nickname") String mem_nickname,
            @Field("mem_profile_img_url") String mem_profile_img_url,
            @Field("mem_email") String mem_email

    );
    @FormUrlEncoded
    @POST("sendMyLocation.php")
    Call<GetApiTest> sendMyLocation(

            @Field("mem_token") String mem_token

    );

    @FormUrlEncoded
    @POST("createMeeting.php")
    Call<_CreateMeeting> createMeeting(

            @Field("mem_token") String mem_token,
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude,
            @Field("address") String address

    );

    @FormUrlEncoded
    @POST("getMeetingInfo.php")
    Call<_GetMeetingInfo> getMeetingInfo(

            @Field("mem_token") String mem_token

    );
}
