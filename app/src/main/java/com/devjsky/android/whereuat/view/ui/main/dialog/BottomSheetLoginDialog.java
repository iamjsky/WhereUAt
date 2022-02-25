package com.devjsky.android.whereuat.view.ui.main.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.databinding.DialogBottomSheetLoginBinding;

import com.devjsky.android.whereuat.view.base.BaseBottomSheetDialogFragment;
import com.devjsky.android.whereuat.viewmodel.BottomSheetLoginViewModel;

/**
 * ClassName            MyBottomSheetDialog
 * Created by JSky on   2022-02-24
 * <p>
 * Description
 */
public class BottomSheetLoginDialog extends BaseBottomSheetDialogFragment<BottomSheetLoginViewModel> {

    DialogBottomSheetLoginBinding binding;
    @Override
    public int getLayoutId() {
        return R.layout.dialog_bottom_sheet_login;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = (DialogBottomSheetLoginBinding) vdb;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
    }
}
