package com.devjsky.android.whereuat.social.kakao.callback;

import androidx.annotation.NonNull;

import com.kakao.sdk.user.model.User;


/**
 * ClassName            KakaoGetUserInfoCallback
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
public interface KakaoGetUserInfoCallback {
    void onSuccess(User user);
    void onError(@NonNull Throwable throwable);
}
