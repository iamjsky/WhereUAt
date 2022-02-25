package com.devjsky.android.whereuat.net.api;


import com.devjsky.android.whereuat.common.Constants;
import com.devjsky.android.whereuat.net.HttpStatusCode;
import com.devjsky.android.whereuat.net.RetrofitGenerator;

/**
 * ClassName            BaseApi
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
public class BaseApi implements Constants, HttpStatusCode {
    static ApiService apiService = RetrofitGenerator.generate(ApiService.class);
}
