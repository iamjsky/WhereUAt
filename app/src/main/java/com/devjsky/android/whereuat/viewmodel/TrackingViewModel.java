package com.devjsky.android.whereuat.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.devjsky.android.whereuat.view.base.BaseViewModel;

/**
 * ClassName            TrackingViewModel
 * Created by JSky on   2022-02-17
 * <p>
 * Description
 */
public class TrackingViewModel extends BaseViewModel {
    public MutableLiveData<Location> mLocation = new MutableLiveData<>();
    public MutableLiveData<String> mAddress = new MutableLiveData<>();

    public void setMyLocation(Location location, String address){
        mLocation.setValue(location);
        mAddress.setValue(address);
    }

}
