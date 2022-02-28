package com.devjsky.android.whereuat.net.api.callback;

import com.devjsky.android.whereuat.net.pojo._CreateMeeting;
import com.devjsky.android.whereuat.net.pojo._UpdateLocation;

/**
 * ClassName            _UpdateLocationCallback
 * Created by JSky on   2022-02-28
 * <p>
 * Description
 */
public interface _UpdateLocationCallback {
    void onSuccess(_UpdateLocation data);
    void onError(_UpdateLocation data);
}
