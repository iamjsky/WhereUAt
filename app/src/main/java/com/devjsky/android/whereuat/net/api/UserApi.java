package com.devjsky.android.whereuat.net.api;

import com.devjsky.android.whereuat.common.MemberInfo;
import com.devjsky.android.whereuat.model.Member;
import com.devjsky.android.whereuat.net.api.callback.GetApiTestCallback;
import com.devjsky.android.whereuat.net.api.callback._GetMemberInfoCallback;
import com.devjsky.android.whereuat.net.api.callback._KakaoLoginCallback;
import com.devjsky.android.whereuat.net.api.callback._KakaoSignUpCallback;
import com.devjsky.android.whereuat.net.pojo.GetApiTest;
import com.devjsky.android.whereuat.net.pojo.HttpBaseHeader;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;
import com.devjsky.android.whereuat.net.pojo._KakaoLogin;
import com.devjsky.android.whereuat.net.pojo._KakaoSignUp;
import com.devjsky.android.whereuat.widget.MyPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ClassName            UserApi
 * Created by JSky on   2022-02-15
 * <p>
 * Description
 */
public class UserApi extends BaseApi {

    public static void getApiTest(String mem_token, GetApiTestCallback callback) {
        final GetApiTest apiData = new GetApiTest();
        HttpBaseHeader header = new HttpBaseHeader();

        apiService.getApiTest(mem_token).enqueue(new Callback<GetApiTest>() {
            @Override
            public void onResponse(Call<GetApiTest> call, Response<GetApiTest> response) {
                if (response.isSuccessful()) {
                    GetApiTest resData = response.body();
                    int code = resData.getHeader().getCode();
                    String message = resData.getHeader().getMessage();

                    header.setCode(code);
                    header.setMessage(message);
                    resData.setHeader(header);

                    switch (code) {
                        case OK:
                            callback.onSuccess(resData);
                            break;

                        default:
                            callback.onError(resData);
                            break;
                    }


                } else {
                    header.setCode(INTERNAL_SERVER_ERROR);
                    header.setMessage("getApiTest()>>" + "response is not successful");
                    apiData.setHeader(header);

                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<GetApiTest> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("getApiTest()>>" + t.toString());
                apiData.setHeader(header);

                callback.onError(apiData);
            }
        });


    }

    public static void kakaoLogin(String kakao_id, String mem_nickname, String mem_profile_img_url, String mem_email, _KakaoLoginCallback callback) {
        final _KakaoLogin apiData = new _KakaoLogin();
        HttpBaseHeader header = new HttpBaseHeader();

        apiService.kakaoLogin(kakao_id, mem_nickname, mem_profile_img_url, mem_email).enqueue(new Callback<_KakaoLogin>() {
            @Override
            public void onResponse(Call<_KakaoLogin> call, Response<_KakaoLogin> response) {
                if (response.isSuccessful()) {
                    _KakaoLogin resData = response.body();
                    int code = resData.getHeader().getCode();
                    String message = resData.getHeader().getMessage();

                    header.setCode(code);
                    header.setMessage(message);
                    resData.setHeader(header);

                    switch (code) {
                        case OK:
                            MemberInfo.getInstance().setMem_token(resData.getMemToken());
                            callback.onSuccess(resData);
                            break;

                        default:
                            MemberInfo.getInstance().setMem_token("");
                            callback.onError(resData);
                            break;
                    }


                } else {
                    header.setCode(INTERNAL_SERVER_ERROR);
                    header.setMessage("kakaoLogin()>>" + "response is not successful");
                    apiData.setHeader(header);
                    MemberInfo.getInstance().setMem_token("");
                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<_KakaoLogin> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("kakaoLogin()>>" + t.toString());
                apiData.setHeader(header);
                MemberInfo.getInstance().setMem_token("");
                callback.onError(apiData);
            }
        });


    }

    public static void kakaoSignUp(String kakao_id, String mem_nickname, String mem_profile_img_url, String mem_email, _KakaoSignUpCallback callback) {
        final _KakaoSignUp apiData = new _KakaoSignUp();
        HttpBaseHeader header = new HttpBaseHeader();

        apiService.kakaoSignUp(kakao_id, mem_nickname, mem_profile_img_url, mem_email).enqueue(new Callback<_KakaoSignUp>() {
            @Override
            public void onResponse(Call<_KakaoSignUp> call, Response<_KakaoSignUp> response) {
                if (response.isSuccessful()) {
                    _KakaoSignUp resData = response.body();
                    int code = resData.getHeader().getCode();
                    String message = resData.getHeader().getMessage();

                    header.setCode(code);
                    header.setMessage(message);
                    resData.setHeader(header);

                    switch (code) {
                        case OK:
                            MemberInfo.getInstance().setMem_token(resData.getMemToken());
                            callback.onSuccess(resData);
                            break;

                        default:
                            MemberInfo.getInstance().setMem_token("");
                            callback.onError(resData);
                            break;
                    }


                } else {
                    header.setCode(INTERNAL_SERVER_ERROR);
                    header.setMessage("kakaoSignUp()>>" + "response is not successful");
                    apiData.setHeader(header);
                    MemberInfo.getInstance().setMem_token("");
                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<_KakaoSignUp> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("kakaoSignUp()>>" + t.toString());
                apiData.setHeader(header);
                MemberInfo.getInstance().setMem_token("");
                callback.onError(apiData);
            }
        });


    }

    public static void getMemberInfo(_GetMemberInfoCallback callback) {
        final _GetMemberInfo apiData = new _GetMemberInfo();
        HttpBaseHeader header = new HttpBaseHeader();
        String mem_token = "";
        if (MemberInfo.getInstance().getMem_token() != null
                && !MemberInfo.getInstance().getMem_token().equals("")) {
            mem_token = MemberInfo.getInstance().getMem_token() + "";
        }else{
            header.setCode(MEM_TOKEN_ERROR);
            header.setMessage("createMeeting()>>" + "mem_token is null");
            apiData.setHeader(header);
            callback.onError(apiData);
        }

        apiService.getMemberInfo(mem_token).enqueue(new Callback<_GetMemberInfo>() {
            @Override
            public void onResponse(Call<_GetMemberInfo> call, Response<_GetMemberInfo> response) {
                if (response.isSuccessful()) {
                    _GetMemberInfo resData = response.body();
                    int code = resData.getHeader().getCode();
                    String message = resData.getHeader().getMessage();

                    header.setCode(code);
                    header.setMessage(message);
                    resData.setHeader(header);

                    switch (code) {
                        case OK:
                            MemberInfo.getInstance().setMem_token(resData.getMemberInfo().getMemToken());
                            MemberInfo.getInstance().setMemProfileImgUrl(resData.getMemberInfo().getMemProfileImgUrl()+"");

                            callback.onSuccess(resData);
                            break;

                        default:
                            MemberInfo.getInstance().setMem_token("");
                            MemberInfo.getInstance().setMemProfileImgUrl(resData.getMemberInfo().getMemProfileImgUrl()+"");
                            callback.onError(resData);
                            break;
                    }


                } else {
                    header.setCode(INTERNAL_SERVER_ERROR);
                    header.setMessage("getMemberInfo()>>" + "response is not successful");
                    apiData.setHeader(header);
                    MemberInfo.getInstance().setMem_token("");
                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<_GetMemberInfo> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("getMemberInfo()>>" + t.toString());
                apiData.setHeader(header);
                MemberInfo.getInstance().setMem_token("");
                callback.onError(apiData);
            }
        });


    }
}
