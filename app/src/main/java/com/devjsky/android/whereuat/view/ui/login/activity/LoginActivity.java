package com.devjsky.android.whereuat.view.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devjsky.android.whereuat.view.ui.main.activity.MainActivity;
import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.databinding.ActivityLoginBinding;
import com.devjsky.android.whereuat.view.base.BaseActivity;
import com.devjsky.android.whereuat.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    ActivityLoginBinding binding;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        mViewModel.isLoginFin.observe(this, aBoolean -> {
            if(aBoolean != null && aBoolean){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityLoginBinding) vdb;

        binding.btnLoginKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.kakaoLogin(mContext);
            }
        });
    }
}