package com.devjsky.android.whereuat.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devjsky.android.whereuat.common.Constants;
import com.devjsky.android.whereuat.net.HttpStatusCode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * ClassName            BaseFragment
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment implements Constants, HttpStatusCode  {

    protected BaseActivity mContext;
    protected ViewDataBinding vdb;
    public VM mViewModel;
    public View view;

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mContext = (BaseActivity) getActivity();
        vdb = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        view = vdb.getRoot();
        setViewModel();
        observeViewModel();
        return view;
    }

    protected void setViewModel(){

        final Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        mViewModel = new ViewModelProvider(this).get((Class<VM>) types[0]);
        mViewModel.init();
    }

    protected void observeViewModel(){


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
