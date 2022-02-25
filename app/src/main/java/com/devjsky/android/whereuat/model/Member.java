package com.devjsky.android.whereuat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            Member
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
@Data
public class Member {
    @SerializedName("mem_token")
    @Expose
    public String memToken;
    @SerializedName("kakao_id")
    @Expose
    public String kakaoId;
    @SerializedName("mem_name")
    @Expose
    public String memName;
    @SerializedName("mem_profile_img_url")
    @Expose
    public String memProfileImgUrl;
    @SerializedName("mem_nickname")
    @Expose
    public String memNickname;
    @SerializedName("mem_phone")
    @Expose
    public String memPhone;
    @SerializedName("mem_email_pw")
    @Expose
    public String memEmailPw;
    @SerializedName("created_date")
    @Expose
    public String createdDate;
    @SerializedName("last_login_date")
    @Expose
    public String lastLoginDate;
    @SerializedName("state")
    @Expose
    public Integer state;
}
