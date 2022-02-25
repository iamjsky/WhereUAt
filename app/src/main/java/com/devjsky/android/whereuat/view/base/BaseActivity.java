package com.devjsky.android.whereuat.view.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.devjsky.android.whereuat.common.Constants;
import com.devjsky.android.whereuat.net.HttpStatusCode;
import com.devjsky.android.whereuat.net.pojo.GetApiTest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;




public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements Constants, HttpStatusCode {

    protected Context mContext;
    protected Activity mActivity;

    protected ViewDataBinding vdb;
    public VM mViewModel;


    @LayoutRes
    public abstract int getLayoutId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vdb = DataBindingUtil.setContentView(this, getLayoutId());
        mContext = this;
        mActivity = this;

        setViewModel();
        observeViewModel();
    }








    protected void setViewModel(){

        final Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        mViewModel = new ViewModelProvider(this).get((Class<VM>) types[0]);
        mViewModel.init();
    }

    protected void observeViewModel(){


    }


}