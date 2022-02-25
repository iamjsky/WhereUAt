package com.devjsky.android.whereuat.widget.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.common.Constants;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ClassName            AddressUtils
 * Created by JSky on   2021-01-28
 * <p>
 * Description
 */
public class GpsUtils implements Constants {
    public static String getCurrentAddress(Context context, double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Log.e(TAG, "지오코더 서비스 사용불가");

            return "error1";
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG,"잘못된 GPS 좌표");
            return "error2";

        }



        if (addresses == null || addresses.size() == 0) {
            Log.e(TAG,"주소 미발견");
            return "error3";

        }

        Address address = addresses.get(0);
        String[] splitAddr = (address.getAddressLine(0).toString()).split(" ");
        StringBuilder sb = new StringBuilder();
        if(splitAddr.length > 1){
            for(int i=1; i < splitAddr.length; i++){
                if(i==splitAddr.length-1){
                    sb.append(splitAddr[i]);
                }else{
                    sb.append(splitAddr[i]+" ");
                }

            }
        }else{
                sb.append(address.getAddressLine(0).toString());
        }

        return sb.toString();

    }
   public static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     * @param requestingLocationUpdates The location updates state.
     */
    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     * @param location  The {@link Location}.
     */
    public static String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    static String getLocationTitle(Context context) {
        return context.getString(R.string.location_updated,
                DateFormat.getDateTimeInstance().format(new Date()));
    }
}
