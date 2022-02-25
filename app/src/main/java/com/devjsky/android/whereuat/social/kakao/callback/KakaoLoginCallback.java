package com.devjsky.android.whereuat.social.kakao.callback;

import androidx.annotation.NonNull;

/**
 * ClassName            KakaoLoginCallback
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
public interface KakaoLoginCallback {
    void onSuccess(String kakaoUserToken);
    void onError(@NonNull Throwable throwable);
}
