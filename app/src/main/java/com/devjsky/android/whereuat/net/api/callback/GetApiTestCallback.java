package com.devjsky.android.whereuat.net.api.callback;


import com.devjsky.android.whereuat.model.User;
import com.devjsky.android.whereuat.net.pojo.GetApiTest;

/**
 * ClassName            ApiCallback
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
public interface GetApiTestCallback {
    void onSuccess(GetApiTest data);
    void onError(GetApiTest data);
}
