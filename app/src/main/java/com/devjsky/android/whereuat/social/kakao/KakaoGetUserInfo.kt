package com.devjsky.android.whereuat.social.kakao

import android.content.Context
import android.util.Log
import com.devjsky.android.whereuat.common.Constants.TAG
import com.devjsky.android.whereuat.social.kakao.callback.KakaoGetUserInfoCallback
import com.kakao.sdk.user.UserApiClient


/**
 * ClassName            KakaoGetUserInfo
 * Created by JSky on   2022-02-22
 *
 * Description
 */
class KakaoGetUserInfo {
    private lateinit var getUserInfoCallback: KakaoGetUserInfoCallback



    constructor(getUserInfoCallback: KakaoGetUserInfoCallback){
        this.getUserInfoCallback = getUserInfoCallback;
    }


    fun getUserInfo(context: Context) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
                getUserInfoCallback.onError(error)
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                getUserInfoCallback.onSuccess(user)
            }
        }
    }
}