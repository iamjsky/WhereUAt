package com.devjsky.android.whereuat.net.pojo;

import com.devjsky.android.whereuat.model.MeetingData;
import com.devjsky.android.whereuat.model.Member;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            _CreateMeeting
 * Created by JSky on   2022-02-25
 * <p>
 * Description
 */
@Data
public class _CreateMeeting {
    @SerializedName("header")
    @Expose
    public HttpBaseHeader header;
    @SerializedName("meeting_group_info")
    @Expose
    public MeetingData meetingGroupInfo;
}
