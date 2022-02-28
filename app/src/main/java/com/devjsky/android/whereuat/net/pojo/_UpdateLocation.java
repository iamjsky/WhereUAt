package com.devjsky.android.whereuat.net.pojo;

import com.devjsky.android.whereuat.model.MeetingGroupData;
import com.devjsky.android.whereuat.model.MeetingGroupMemberData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            _UpdateLocation
 * Created by JSky on   2022-02-28
 * <p>
 * Description
 */
@Data
public class _UpdateLocation {

    @SerializedName("header")
    @Expose
    public HttpBaseHeader header;

    @SerializedName("meeting_group_info")
    @Expose
    public MeetingGroupData meetingGroupInfo;

    @SerializedName("meeting_group_member_info")
    @Expose
    public List<MeetingGroupMemberData> meetingGroupMemberInfoList = new ArrayList<>();

}
