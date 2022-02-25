package com.devjsky.android.whereuat.net.api.callback;


import com.devjsky.android.whereuat.net.pojo._KakaoSignUp;

/**
 * ClassName            _KakaoSignUpCallback
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
public interface _KakaoSignUpCallback {
    void onSuccess(_KakaoSignUp data);
    void onError(_KakaoSignUp data);
}
