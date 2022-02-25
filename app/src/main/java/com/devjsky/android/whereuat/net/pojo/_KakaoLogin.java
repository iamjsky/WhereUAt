package com.devjsky.android.whereuat.net.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            _KakaoLogin
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
@Data
public class _KakaoLogin {
    @SerializedName("header")
    @Expose
    public HttpBaseHeader header;
    @SerializedName("mem_token")
    @Expose
    public String memToken;

}
