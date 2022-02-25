package com.devjsky.android.whereuat.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.devjsky.android.whereuat.common.MemberInfo;
import com.devjsky.android.whereuat.net.api.UserApi;
import com.devjsky.android.whereuat.net.api.callback._GetMemberInfoCallback;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;
import com.devjsky.android.whereuat.social.kakao.KakaoSendLinkMsg;
import com.devjsky.android.whereuat.social.kakao.callback.KakaoSendLinkMsgCallback;
import com.devjsky.android.whereuat.view.base.BaseViewModel;
import com.devjsky.android.whereuat.widget.MyPreferenceManager;
import com.devjsky.android.whereuat.widget.utils.KotlinToJavaUtils;
import com.kakao.sdk.link.model.LinkResult;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.ItemContent;

/**
 * ClassName            SplashViewModel
 * Created by JSky on   2022-02-17
 * <p>
 * Description
 */
public class SplashViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> isGetUserInfoFin = new MutableLiveData<>();

    KakaoSendLinkMsg kakaoSendLinkMsg;

    @Override
    public void init() {
        super.init();
        isGetUserInfoFin.setValue(null);
        kakaoSendLinkMsg = new KakaoSendLinkMsg(new KakaoSendLinkMsgCallback() {
            @Override
            public void onSuccess(LinkResult linkResult) {
                LOG_E("KakaoSendLinkMsg : " + linkResult.toString());
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LOG_E("KakaoSendLinkMsg : " + throwable.toString());
            }
        });
    }

    public void getMemberInfo(Context mContext){
        String memToken = MyPreferenceManager.getString(mContext, "mem_token") + "";
        MemberInfo.getInstance().setMem_token(memToken);
        if(memToken.equals("")){
            isGetUserInfoFin.setValue(false);
        }else{
            LOG_E("memToken : " + memToken);

            UserApi.getMemberInfo(new _GetMemberInfoCallback() {
                @Override
                public void onSuccess(_GetMemberInfo data) {
                    MyPreferenceManager.setString(mContext, "mem_token", data.getMemberInfo().getMemToken()+"");
                    isGetUserInfoFin.setValue(true);
                }

                @Override
                public void onError(_GetMemberInfo data) {
                    isGetUserInfoFin.setValue(false);
                }
            });
        }



    }
    public void kakaoSendLinkMsg(Context context){

        FeedTemplate feedTemplate = KotlinToJavaUtils.Companion.getFeedTemplate("딩딩", "부산시 수영구 수영로 541-1", "1");


        kakaoSendLinkMsg.sendMsg(context, feedTemplate);
    }
}
