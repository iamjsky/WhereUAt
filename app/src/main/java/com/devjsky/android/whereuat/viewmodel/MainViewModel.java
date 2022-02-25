package com.devjsky.android.whereuat.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.devjsky.android.whereuat.model.MeetingData;
import com.devjsky.android.whereuat.model.MeetingGroupMemberData;
import com.devjsky.android.whereuat.model.User;
import com.devjsky.android.whereuat.net.api.MeetingApi;
import com.devjsky.android.whereuat.net.api.UserApi;
import com.devjsky.android.whereuat.net.api.callback.GetApiTestCallback;
import com.devjsky.android.whereuat.net.api.callback._GetMeetingInfoCallback;
import com.devjsky.android.whereuat.net.pojo.GetApiTest;
import com.devjsky.android.whereuat.net.pojo._GetMeetingInfo;
import com.devjsky.android.whereuat.view.base.BaseViewModel;
import com.devjsky.android.whereuat.widget.SingleLiveEvent;

import java.util.List;

/**
 * ClassName            MainViewModel
 * Created by JSky on   2022-02-16
 * <p>
 * Description
 */
public class MainViewModel extends BaseViewModel {

    public MutableLiveData<Object> mainViewModelApiResult = new MutableLiveData<>();

    public MutableLiveData<User> userData = new MutableLiveData<>();

    public MutableLiveData<MeetingData> meetingData = new MutableLiveData<>();
    public MutableLiveData<List<MeetingGroupMemberData>> meetingGroupMemberData = new MutableLiveData<>();

    public MutableLiveData<Location> mLocation = new MutableLiveData<>();
    public MutableLiveData<String> mAddress = new MutableLiveData<>();

    public void setMyLocation(Location location, String address){
        mLocation.setValue(location);
        mAddress.setValue(address);
    }

    public void addValue() {
        Log.e("로그", "addValiue()");


        UserApi.getApiTest("test", new GetApiTestCallback() {
            @Override
            public void onSuccess(GetApiTest data) {
                Log.e("로그", "user : " + data);
                Log.e("로그", "user.getMemPhone() : " + data.getUserInfo().getMemPhone());
                userData.setValue(data.getUserInfo());
                mainViewModelApiResult.setValue(data);
            }

            @Override
            public void onError(GetApiTest data) {
                mainViewModelApiResult.setValue(data);
            }
        });

    }

    public void getMeetingInfo(){
        MeetingApi.getMeetingInfo(new _GetMeetingInfoCallback() {
            @Override
            public void onSuccess(_GetMeetingInfo data) {
                meetingData.setValue(data.getMeetingGroupInfo());
                meetingGroupMemberData.setValue(data.getMeetingGroupMemberDataList());
            }

            @Override
            public void onError(_GetMeetingInfo data) {

            }
        });
    }


}
