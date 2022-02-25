package com.devjsky.android.whereuat.net.api.callback;

import com.devjsky.android.whereuat.net.pojo.GetApiTest;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;

/**
 * ClassName            _GetMemberInfoCallback
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
public interface _GetMemberInfoCallback {
    void onSuccess(_GetMemberInfo data);
    void onError(_GetMemberInfo data);
}
