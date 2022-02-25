package com.devjsky.android.whereuat.net.api.callback;

import com.devjsky.android.whereuat.net.pojo._KakaoLogin;
import com.devjsky.android.whereuat.net.pojo._KakaoSignUp;

/**
 * ClassName            _KakaoLoginCallback
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
public interface _KakaoLoginCallback {
    void onSuccess(_KakaoLogin data);
    void onError(_KakaoLogin data);
}
