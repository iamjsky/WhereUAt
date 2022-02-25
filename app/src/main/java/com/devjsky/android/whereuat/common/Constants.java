package com.devjsky.android.whereuat.common;

import android.util.Log;

/**
 * ClassName            Constants
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
public interface Constants {
    String TAG = "로그";
    int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    int FRAGMENT_PAGE_BOTTOM_SHEET_PAGE_LOGIN = 1000;
    int FRAGMENT_PAGE_BOTTOM_SHEET_PAGE_MEETING_GROUP = 2000;

    default void LOG_I(String msg){
        Log.i(TAG, msg);
    }
    default  void LOG_E(String msg){
        Log.e(TAG, msg);
    }
}
