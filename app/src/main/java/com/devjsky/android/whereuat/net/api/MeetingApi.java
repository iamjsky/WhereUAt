package com.devjsky.android.whereuat.net.api;

import com.devjsky.android.whereuat.common.MemberInfo;
import com.devjsky.android.whereuat.model.MeetingData;
import com.devjsky.android.whereuat.net.api.callback._CreateMeetingCallback;
import com.devjsky.android.whereuat.net.api.callback._GetMeetingInfoCallback;
import com.devjsky.android.whereuat.net.api.callback._GetMemberInfoCallback;
import com.devjsky.android.whereuat.net.pojo.HttpBaseHeader;
import com.devjsky.android.whereuat.net.pojo._CreateMeeting;
import com.devjsky.android.whereuat.net.pojo._GetMeetingInfo;
import com.devjsky.android.whereuat.net.pojo._GetMemberInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ClassName            MeetingApi
 * Created by JSky on   2022-02-25
 * <p>
 * Description
 */
public class MeetingApi extends BaseApi {


    public static void createMeeting(MeetingData meetingData, _CreateMeetingCallback callback) {
        final _CreateMeeting apiData = new _CreateMeeting();
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

        Double latitude = meetingData.getPlaceLatitude();
        Double longitude = meetingData.getPlaceLongitude();
        String address = meetingData.getPlaceAddress()+"";


        apiService.createMeeting(mem_token, latitude, longitude, address).enqueue(new Callback<_CreateMeeting>() {
            @Override
            public void onResponse(Call<_CreateMeeting> call, Response<_CreateMeeting> response) {
                if (response.isSuccessful()) {
                    _CreateMeeting resData = response.body();
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
                    header.setMessage("createMeeting()>>" + "response is not successful");
                    apiData.setHeader(header);
                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<_CreateMeeting> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("createMeeting()>>" + t.toString());
                apiData.setHeader(header);
                callback.onError(apiData);
            }
        });


    }


    public static void getMeetingInfo(_GetMeetingInfoCallback callback) {
        final _GetMeetingInfo apiData = new _GetMeetingInfo();
        HttpBaseHeader header = new HttpBaseHeader();

        String mem_token = "";
        if (MemberInfo.getInstance().getMem_token() != null
                && !MemberInfo.getInstance().getMem_token().equals("")) {
            mem_token = MemberInfo.getInstance().getMem_token() + "";
        }else{
            header.setCode(MEM_TOKEN_ERROR);
            header.setMessage("getMeetingInfo()>>" + "mem_token is null");
            apiData.setHeader(header);
            callback.onError(apiData);
        }




        apiService.getMeetingInfo(mem_token).enqueue(new Callback<_GetMeetingInfo>() {
            @Override
            public void onResponse(Call<_GetMeetingInfo> call, Response<_GetMeetingInfo> response) {
                if (response.isSuccessful()) {
                    _GetMeetingInfo resData = response.body();
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
                    header.setMessage("getMeetingInfo()>>" + "response is not successful");
                    apiData.setHeader(header);
                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<_GetMeetingInfo> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("getMeetingInfo()>>" + t.toString());
                apiData.setHeader(header);
                callback.onError(apiData);
            }
        });


    }

    public static void updateLocation(_GetMeetingInfoCallback callback) {
        final _GetMeetingInfo apiData = new _GetMeetingInfo();
        HttpBaseHeader header = new HttpBaseHeader();

        String mem_token = "";
        if (MemberInfo.getInstance().getMem_token() != null
                && !MemberInfo.getInstance().getMem_token().equals("")) {
            mem_token = MemberInfo.getInstance().getMem_token() + "";
        }else{
            header.setCode(MEM_TOKEN_ERROR);
            header.setMessage("getMeetingInfo()>>" + "mem_token is null");
            apiData.setHeader(header);
            callback.onError(apiData);
        }




        apiService.getMeetingInfo(mem_token).enqueue(new Callback<_GetMeetingInfo>() {
            @Override
            public void onResponse(Call<_GetMeetingInfo> call, Response<_GetMeetingInfo> response) {
                if (response.isSuccessful()) {
                    _GetMeetingInfo resData = response.body();
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
                    header.setMessage("getMeetingInfo()>>" + "response is not successful");
                    apiData.setHeader(header);
                    callback.onError(apiData);
                }
            }

            @Override
            public void onFailure(Call<_GetMeetingInfo> call, Throwable t) {
                header.setCode(INTERNAL_SERVER_ERROR);
                header.setMessage("getMeetingInfo()>>" + t.toString());
                apiData.setHeader(header);
                callback.onError(apiData);
            }
        });


    }


}
