package com.devjsky.android.whereuat.net.pojo;

import com.devjsky.android.whereuat.model.Member;
import com.devjsky.android.whereuat.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            GetMemberInfo
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
@Data
public class _GetMemberInfo {
    @SerializedName("header")
    @Expose
    public HttpBaseHeader header;
    @SerializedName("member_info")
    @Expose
    public Member memberInfo;

}
