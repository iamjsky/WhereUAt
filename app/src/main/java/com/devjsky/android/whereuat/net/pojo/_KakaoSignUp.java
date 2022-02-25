package com.devjsky.android.whereuat.net.pojo;

import com.devjsky.android.whereuat.model.Member;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            _KakaoSignUp
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
@Data
public class _KakaoSignUp {
    @SerializedName("header")
    @Expose
    public HttpBaseHeader header;
    @SerializedName("mem_token")
    @Expose
    public String memToken;
}
