package com.devjsky.android.whereuat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            UserInfo
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
@Data
public class User {

    @SerializedName("mem_token")
    @Expose
    public String memToken;
    @SerializedName("mem_phone")
    @Expose
    public String memPhone;
    @SerializedName("mem_pw")
    @Expose
    public String memPw;

}
