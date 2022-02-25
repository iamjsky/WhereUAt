package com.devjsky.android.whereuat.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ClassName            RetrofitGenerator
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
public class RetrofitGenerator {

    public static Retrofit retrofit;

    private static OkHttpClient createOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);
        return builder.build();
    }

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    static {
        // ph server
        retrofit = new Retrofit.Builder()
                .baseUrl("https://iamjsky.cafe24.com/mobile_app/whereuat/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(createOkHttpClient())
                .build();
    }


    public static <T> T generate(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
