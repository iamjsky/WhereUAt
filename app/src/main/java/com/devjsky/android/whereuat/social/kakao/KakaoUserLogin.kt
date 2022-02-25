package com.devjsky.android.whereuat.social.kakao

import android.content.Context
import android.util.Log
import com.devjsky.android.whereuat.common.Constants.TAG
import com.devjsky.android.whereuat.social.kakao.callback.KakaoLoginCallback
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient


/**
 * ClassName            KakaoUserLogin
 * Created by JSky on   2022-02-22
 *
 * Description
 */
class KakaoUserLogin {


    private lateinit var loginCallback: KakaoLoginCallback


    constructor(loginCallback:KakaoLoginCallback){

        this.loginCallback = loginCallback;
    }

    // 로그인 공통 callback 구성
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
            loginCallback.onError(error);
        }
        else if (token != null) {
            Log.d(TAG, "로그인 성공 ${token.accessToken}")

            loginCallback.onSuccess(token.accessToken)




        }

    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    fun login(context: Context){
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}