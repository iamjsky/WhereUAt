package com.devjsky.android.whereuat.view.ui.splash.activity;

import android.content.Intent;
import android.os.Bundle;

import com.devjsky.android.whereuat.view.ui.main.activity.MainActivity;
import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.databinding.ActivitySplashBinding;
import com.devjsky.android.whereuat.view.base.BaseActivity;
import com.devjsky.android.whereuat.view.ui.login.activity.LoginActivity;
import com.devjsky.android.whereuat.viewmodel.SplashViewModel;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    ActivitySplashBinding binding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivitySplashBinding) vdb;


        Intent kakaoIntent = getIntent();
        if (kakaoIntent != null) {
            LOG_E("kakaoIntent.getAction() : " + kakaoIntent.getAction().toString());
            if (kakaoIntent.getAction() == Intent.ACTION_VIEW) {
                String kakaoLinkType = kakaoIntent.getData().getQueryParameter("kakaoLinkType") + "";
                if (kakaoLinkType.equals("meeting_agree")) {
                    int mg_idx = Integer.parseInt(kakaoIntent.getData().getQueryParameter("mg_idx") + "");
                    LOG_E("kakaoLinkType : " + kakaoLinkType + ", mg_idx : " + mg_idx);
                }


            }
        }


        binding.btnSend.setOnClickListener(v -> {
            mViewModel.kakaoSendLinkMsg(this);
        });

        init();
    }

    void init() {
       mViewModel.getMemberInfo(mContext);
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        mViewModel.isGetUserInfoFin.observe(this, aBoolean -> {
            if (aBoolean != null) {
                Intent intent;
                if (aBoolean) {
                    intent = new Intent(this, MainActivity.class);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}