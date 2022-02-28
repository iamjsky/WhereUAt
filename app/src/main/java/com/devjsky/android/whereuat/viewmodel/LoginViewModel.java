package com.devjsky.android.whereuat.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.devjsky.android.whereuat.common.MemberInfo;
import com.devjsky.android.whereuat.model.Member;
import com.devjsky.android.whereuat.net.api.UserApi;
import com.devjsky.android.whereuat.net.api.callback.GetApiTestCallback;
import com.devjsky.android.whereuat.net.api.callback._GetMemberInfoCallback;
import com.devjsky.android.whereuat.net.api.callback._KakaoLoginCallback;
import com.devjsky.android.whereuat.net.api.callback._KakaoSignUpCallback;
import com.devjsky.android.whereuat.net.pojo.GetApiTest;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;
import com.devjsky.android.whereuat.net.pojo._KakaoLogin;
import com.devjsky.android.whereuat.net.pojo._KakaoSignUp;
import com.devjsky.android.whereuat.social.kakao.KakaoGetUserInfo;
import com.devjsky.android.whereuat.social.kakao.KakaoUserLogin;
import com.devjsky.android.whereuat.social.kakao.callback.KakaoGetUserInfoCallback;
import com.devjsky.android.whereuat.social.kakao.callback.KakaoLoginCallback;
import com.devjsky.android.whereuat.view.base.BaseViewModel;
import com.devjsky.android.whereuat.widget.MyPreferenceManager;
import com.kakao.sdk.user.model.User;

/**
 * ClassName            LoginViewModel
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
public class LoginViewModel extends BaseViewModel {

    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;
    public MutableLiveData<Boolean> isLoginFin = new MutableLiveData<>();
    Context mContext;
    String kakao_id = "";
    String mem_nickname = "";
    String mem_profile_img_url = "";
    String mem_email = "";

    @Override
    public void init() {
        super.init();
        kakao_id = "";
        mem_nickname = "";
        mem_profile_img_url = "";
        mem_email = "";
        isLoginFin.setValue(false);
        kakaoUserLogin = new KakaoUserLogin(kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(kakaoGetUserInfoCallback);
    }

    public void kakaoLogin(Context context){
        isLoginFin.setValue(false);
        mContext = context;
        kakao_id = "";
        mem_nickname = "";
        mem_profile_img_url = "";
        mem_email = "";
        kakaoUserLogin.login(mContext);
    }

    _GetMemberInfoCallback getMemberInfoCallback = new _GetMemberInfoCallback() {
        @Override
        public void onSuccess(_GetMemberInfo data) {
            if(data.getMemberInfo() != null){
                MyPreferenceManager.setString(mContext, "mem_token", data.getMemberInfo().getMemToken()+"");



                isLoginFin.setValue(true);
            }else{
                isLoginFin.setValue(false);
            }
        }

        @Override
        public void onError(_GetMemberInfo data) {
            isLoginFin.setValue(false);
        }
    };

    KakaoLoginCallback kakaoLoginCallback = new KakaoLoginCallback() {
        @Override
        public void onSuccess(String kakaoUserToken) {
            kakaoGetUserInfo.getUserInfo(mContext);

        }

        @Override
        public void onError(@NonNull Throwable throwable) {

        }
    };
    KakaoGetUserInfoCallback kakaoGetUserInfoCallback = new KakaoGetUserInfoCallback() {
        @Override
        public void onSuccess(User user) {





            if (user.getId() != 0 && user.getId() != -1) {
                kakao_id = user.getId() + "";
            } else {
                kakao_id = "";
            }

            if (user.getKakaoAccount().getProfile().getNickname() != null) {
                mem_nickname = user.getKakaoAccount().getProfile().getNickname() + "";
            } else {
                mem_nickname = "이름없음";
            }


            if (user.getKakaoAccount().getProfile().getThumbnailImageUrl() != null) {
                mem_profile_img_url = user.getKakaoAccount().getProfile().getThumbnailImageUrl() + "";
            } else {
                mem_profile_img_url = "";
            }

            if (user.getKakaoAccount().getEmail() != null &&
                    user.getKakaoAccount().getEmail().contains("@")) {
                mem_email = user.getKakaoAccount().getEmail() + "";
            } else {
                mem_email = "";
            }

            UserApi.kakaoLogin(kakao_id, mem_nickname, mem_profile_img_url, mem_email, new _KakaoLoginCallback() {
                @Override
                public void onSuccess(_KakaoLogin data) {
                    LOG_E("!!!!!!kakaoLogin mem_token !!!! : " +  data.getMemToken());
                    UserApi.getMemberInfo(getMemberInfoCallback);

                }

                @Override
                public void onError(_KakaoLogin data) {
                    if(data.getHeader().getCode() == NOT_FOUND){
                        UserApi.kakaoSignUp(kakao_id, mem_nickname, mem_profile_img_url, mem_email, new _KakaoSignUpCallback() {
                            @Override
                            public void onSuccess(_KakaoSignUp data) {
                                LOG_E("!!!!!!kakaoSignUp mem_token !!!! : " + data.getMemToken());
                                UserApi.getMemberInfo(getMemberInfoCallback);

                            }

                            @Override
                            public void onError(_KakaoSignUp data) {

                            }
                        });
                    }
                }
            });

        }

        @Override
        public void onError(@NonNull Throwable throwable) {

        }
    };
}
