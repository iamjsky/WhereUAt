package com.devjsky.android.whereuat.net.api.callback;

import com.devjsky.android.whereuat.net.pojo._CreateMeeting;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;

/**
 * ClassName            _CreateMeetingCallback
 * Created by JSky on   2022-02-25
 * <p>
 * Description
 */
public interface _CreateMeetingCallback {
    void onSuccess(_CreateMeeting data);
    void onError(_CreateMeeting data);
}
