package com.devjsky.android.whereuat.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.devjsky.android.whereuat.widget.utils.GpsUtils;

/**
 * ClassName            GpsTrackerBootReceiver
 * Created by JSky on   2022-02-17
 * <p>
 * Description
 */
public class GpsTrackerReceiver extends BroadcastReceiver {
    private static final String TAG = "GpsTrackerBootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Location location = intent.getParcelableExtra(LocationUpdateService.EXTRA_LOCATION);
        if (location != null) {

                Log.e(TAG, "GpsTrackerReceiver : " + GpsUtils.getLocationText(location));



        }
    }
}