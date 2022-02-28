package com.devjsky.android.whereuat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            MeetingData
 * Created by JSky on   2022-02-25
 * <p>
 * Description
 */
@Data
public class MeetingGroupData {
    @SerializedName("place_address")
    @Expose
    public String placeAddress;
    @SerializedName("place_latitude")
    @Expose
    public Double placeLatitude;
    @SerializedName("place_longitude")
    @Expose
    public Double placeLongitude;
    @SerializedName("place_state")
    @Expose
    public String placeState;
    @SerializedName("place_time")
    @Expose
    public String placeTime;
    @SerializedName("place_name")
    @Expose
    public String placeName;
    @SerializedName("mg_idx")
    @Expose
    public Integer mgIdx;
    @SerializedName("place_created_date")
    @Expose
    public String placeCreatedDate;
    @SerializedName("created_mem_idx")
    @Expose
    public Integer createdMemIdx;
    @SerializedName("created_mem_token")
    @Expose
    public String createdMemToken;


}
