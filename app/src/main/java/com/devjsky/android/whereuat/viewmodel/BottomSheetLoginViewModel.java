package com.devjsky.android.whereuat.viewmodel;

import com.devjsky.android.whereuat.view.base.BaseViewModel;

/**
 * ClassName            BottomSheetLoginViewModel
 * Created by JSky on   2022-02-24
 * <p>
 * Description
 */
public class BottomSheetLoginViewModel extends BaseViewModel {
    private static BottomSheetLoginViewModel instance = null;
    public static BottomSheetLoginViewModel getInstance(){
        if(instance == null){
            instance = new BottomSheetLoginViewModel();
        }
        return instance;
    }
    public int peekHeight = 100;
}
