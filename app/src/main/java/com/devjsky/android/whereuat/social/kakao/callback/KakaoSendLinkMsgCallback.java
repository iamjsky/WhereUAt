package com.devjsky.android.whereuat.social.kakao.callback;

import androidx.annotation.NonNull;

import com.kakao.sdk.link.model.LinkResult;

/**
 * ClassName            KakaoSendLinkMsgCallback
 * Created by JSky on   2022-02-21
 * <p>
 * Description
 */
public interface KakaoSendLinkMsgCallback {
    void onSuccess(LinkResult linkResult);
    void onError(@NonNull Throwable throwable);
}