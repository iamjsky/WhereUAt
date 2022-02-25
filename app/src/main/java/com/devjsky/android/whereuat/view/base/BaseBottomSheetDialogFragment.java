package com.devjsky.android.whereuat.view.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.common.Constants;
import com.devjsky.android.whereuat.net.HttpStatusCode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ClassName            BaseBottomSheetDialog
 * Created by JSky on   2022-02-24
 * <p>
 * Description
 */
public abstract class BaseBottomSheetDialogFragment<VM extends BaseViewModel> extends BottomSheetDialogFragment implements Constants, HttpStatusCode {

    private View view;
    protected Context mContext;

    protected ViewDataBinding vdb;
    public VM mViewModel;

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog_visibleSystemUi);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = (BaseActivity) getActivity();
        vdb = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        view = vdb.getRoot();

        setViewModel();
        observeViewModel();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return dialog;
    }


    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
        // id = android.support.design.R.id.design_bottom_sheet for support librares
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
//        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//        layoutParams.height = getBottomSheetDialogDefaultHeight();
//        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getBottomSheetDialogDefaultHeight() {
        return getWindowHeight();
    }

    private int getWindowHeight() { // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    protected void setViewModel(){

        final Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        mViewModel = new ViewModelProvider(this).get((Class<VM>) types[0]);
        mViewModel.init();
    }

    protected void observeViewModel(){


    }

}
