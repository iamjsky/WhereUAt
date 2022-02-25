package com.devjsky.android.whereuat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            MeetingGroupMemberData
 * Created by JSky on   2022-02-25
 * <p>
 * Description
 */
@Data
public class MeetingGroupMemberData {
    @SerializedName("idx")
    @Expose
    public Integer idx;
    @SerializedName("mem_idx")
    @Expose
    public Integer memIdx;
    @SerializedName("mg_idx")
    @Expose
    public Integer mgIdx;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("created_date")
    @Expose
    public String createdDate;
    @SerializedName("agree_date")
    @Expose
    public String agreeDate;
    @SerializedName("last_latitude")
    @Expose
    public Double lastLatitude;
    @SerializedName("last_longitude")
    @Expose
    public Double lastLongitude;
    @SerializedName("last_address")
    @Expose
    public String lastAddress;
}
