package com.devjsky.android.whereuat.view.ui.tracking.activity;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.devjsky.android.whereuat.BuildConfig;
import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.databinding.ActivityTrackingBinding;
import com.devjsky.android.whereuat.view.base.BaseActivity;
import com.devjsky.android.whereuat.viewmodel.TrackingViewModel;
import com.devjsky.android.whereuat.widget.GpsTrackerReceiver;
import com.devjsky.android.whereuat.widget.LocationUpdateService;
import com.devjsky.android.whereuat.widget.utils.GpsUtils;
import com.google.android.material.snackbar.Snackbar;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class TrackingActivity extends BaseActivity<TrackingViewModel> implements OnMapReadyCallback, SharedPreferences.OnSharedPreferenceChangeListener  {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    public static TrackingActivity instance;

    ActivityTrackingBinding binding;

    boolean isStartTracking = false;

    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private GpsTrackerReceiver gpsTrackerReceiver;

    // A reference to the service used to get location updates.
    public LocationUpdateService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;

    Marker mLocationMarker;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdateService.LocalBinder binder = (LocationUpdateService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_tracking;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityTrackingBinding) vdb;
        instance = this;

        gpsTrackerReceiver = new GpsTrackerReceiver();
        naverMapInit();
        btnSet();
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        mViewModel.mAddress.observe(this, s -> {
            if(s != null){
                binding.tvMAddress.setText(s+"");
            }
        });

        mViewModel.mLocation.observe(this, location -> {
            if(location != null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                if(mLocationMarker == null) {
                    mLocationMarker = new Marker();
                }

                    mLocationMarker.setPosition(new LatLng(latitude, longitude));
                    mLocationMarker.setMap(naverMap);



            }
        });
    }

    void btnSet(){
        binding.btnTrackingStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
            }
        });
        binding.btnTrackingEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
            }
        });
    }
    void naverMapInit(){
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.layout_map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.layout_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }
    public void startLocationUpdates(){
        if (!checkPermissions()) {
            requestPermissions();
        } else {

            mService.requestLocationUpdates();
        }
    }
    public void stopLocationUpdates(){
        mService.removeLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);



        // Restore the state of the buttons when the activity (re)launches.
        setTrackingState(GpsUtils.requestingLocationUpdates(this));

        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, LocationUpdateService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);


    }
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(gpsTrackerReceiver,
                new IntentFilter(LocationUpdateService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gpsTrackerReceiver);
        super.onPause();
    }
    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }
    /**
     * Returns the current state of the permissions needed.
     */
    public boolean checkPermissions() {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
//            Snackbar.make(
//                    findViewById(R.id.activity_main),
//                    R.string.permission_rationale,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            ActivityCompat.requestPermissions(TrackingActivity.this,
//                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                    REQUEST_PERMISSIONS_REQUEST_CODE);
//                        }
//                    })
//                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(TrackingActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
                setTrackingState(false);
//                Snackbar.make(
//                        findViewById(R.id.activity_main),
//                        R.string.permission_denied_explanation,
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.settings, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Build intent that displays the App settings screen.
//                                Intent intent = new Intent();
//                                intent.setAction(
//                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package",
//                                        BuildConfig.APPLICATION_ID, null);
//                                intent.setData(uri);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                overridePendingTransition(0, 0);
//                            }
//                        })
//                        .show();
            }
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(GpsUtils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setTrackingState(sharedPreferences.getBoolean(GpsUtils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
    }
    private void setTrackingState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {

            isStartTracking = true;
            binding.btnTrackingStart.setEnabled(false);
            binding.btnTrackingEnd.setEnabled(true);

        } else {

            isStartTracking = false;
            binding.btnTrackingStart.setEnabled(true);
            binding.btnTrackingEnd.setEnabled(false);
        }
    }
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);
        uiSettings.setZoomControlEnabled(false);

        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);





        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();



            }


        });

        binding.viewMapLocationBtn.setMap(naverMap);
        binding.viewMapZoomCtrl.setMap(naverMap);







    }

}