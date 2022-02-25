package com.devjsky.android.whereuat.widget;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kakao.sdk.common.KakaoSdk;
import com.naver.maps.map.NaverMapSdk;

/**
 * ClassName            MyApplication
 * Created by JSky on   2022-02-17
 * <p>
 * Description
 */
public class MyApplication  extends Application {

    private static MyApplication instance;

    public static MyApplication getMyApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit ");
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("07q890uof8"));
        NaverMapSdk.getInstance(this).setOnAuthFailedListener(new NaverMapSdk.OnAuthFailedListener() {
            @Override
            public void onAuthFailed(@NonNull NaverMapSdk.AuthFailedException e) {
                Log.e("로그", e.toString());
            }
        });
        // Kakao Sdk 초기화
        KakaoSdk.init(this, "959ff9cf592a65fd4f8bbf962e38ad91");
    }
}