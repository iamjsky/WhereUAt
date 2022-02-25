package com.devjsky.android.whereuat.net.pojo;


import com.devjsky.android.whereuat.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            GetApiTest
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
@Data
public class GetApiTest {

    @SerializedName("header")
    @Expose
    public HttpBaseHeader header;
    @SerializedName("user_info")
    @Expose
    public User userInfo;
}
