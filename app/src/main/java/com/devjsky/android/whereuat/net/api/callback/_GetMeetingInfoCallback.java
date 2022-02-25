package com.devjsky.android.whereuat.net.api.callback;

import com.devjsky.android.whereuat.net.pojo._CreateMeeting;
import com.devjsky.android.whereuat.net.pojo._GetMeetingInfo;

/**
 * ClassName            _GetMeetingInfoCallback
 * Created by JSky on   2022-02-25
 * <p>
 * Description
 */
public interface _GetMeetingInfoCallback {
    void onSuccess(_GetMeetingInfo data);
    void onError(_GetMeetingInfo data);
}
