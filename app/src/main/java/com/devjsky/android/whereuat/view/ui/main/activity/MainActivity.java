package com.devjsky.android.whereuat.view.ui.main.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.common.MemberInfo;
import com.devjsky.android.whereuat.databinding.ActivityMainBinding;
import com.devjsky.android.whereuat.model.MeetingGroupData;
import com.devjsky.android.whereuat.model.MeetingGroupMemberData;
import com.devjsky.android.whereuat.view.base.BaseActivity;
import com.devjsky.android.whereuat.view.ui.main.dialog.BottomSheetLoginDialog;
import com.devjsky.android.whereuat.view.ui.main.dialog.CreateMeetingDialog;
import com.devjsky.android.whereuat.viewmodel.MainViewModel;
import com.devjsky.android.whereuat.widget.GpsTrackerReceiver;
import com.devjsky.android.whereuat.widget.LocationUpdateService;
import com.devjsky.android.whereuat.widget.utils.GpsUtils;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel> implements OnMapReadyCallback, SharedPreferences.OnSharedPreferenceChangeListener {

    public static MainActivity instance;
    ActivityMainBinding binding;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    public Marker meetingMarker;
    public List<Marker> meetingGroupMemberMarkerList;

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
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        binding = (ActivityMainBinding) vdb;
        init();


    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        mViewModel.mAddress.observe(this, s -> {
            if (s != null) {
                binding.tvMAddress.setText(s + "");
            }
        });

        mViewModel.mLocation.observe(this, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                if (mLocationMarker == null) {
                    mLocationMarker = new Marker();
                }

                mLocationMarker.setPosition(new LatLng(latitude, longitude));

                mLocationMarker.setIcon(OverlayImage.fromResource(R.drawable.ico_marker_01));
                mLocationMarker.setWidth(180);
                mLocationMarker.setHeight(180);


               mLocationMarker.setMap(naverMap);


            }
        });
        mViewModel.meetingData.observe(this, meetingData -> {
            if (meetingData != null && meetingData.getPlaceState().equals("Y")) {
                if (meetingMarker != null) {
                    meetingMarker.setMap(null);
                    meetingMarker = null;

                }
                meetingMarker = new Marker();

                meetingMarker.setPosition(new LatLng(meetingData.getPlaceLatitude(), meetingData.getPlaceLongitude()));
                meetingMarker.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        if (meetingData.getCreatedMemToken() != null &&
                                !meetingData.getCreatedMemToken().equals("") &&
                                meetingData.getCreatedMemToken().equals(MemberInfo.getInstance().getMem_token())) {
                            Toast.makeText(mContext, meetingData.getPlaceAddress() + "", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                meetingMarker.setMap(naverMap);
            } else {

                meetingMarker = null;

            }
        });

        mViewModel.meetingGroupMemberData.observe(this, meetingGroupMemberData -> {
            LOG_E("meetingGroupMemberData SIZE : " + meetingGroupMemberData.size());
            if (meetingGroupMemberData != null && meetingGroupMemberData.size() > 0) {
                if (naverMap != null) {
                    if (meetingGroupMemberMarkerList != null) {
                        if (meetingGroupMemberMarkerList.size() > 0) {
                            for (Marker marker : meetingGroupMemberMarkerList) {
                                marker.setMap(null);
                            }
                        }
                        meetingGroupMemberMarkerList = null;
                    }
                    meetingGroupMemberMarkerList = new ArrayList<>();
                    for (MeetingGroupMemberData data : meetingGroupMemberData) {

                        Marker marker = new Marker();
                        marker.setPosition(new LatLng(data.getLastLatitude(), data.getLastLongitude()));





                            marker.setOnClickListener(new Overlay.OnClickListener() {
                                @Override
                                public boolean onClick(@NonNull Overlay overlay) {

                                    Toast.makeText(mContext, data.getLastAddress() + "", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            });

                            marker.setIcon(OverlayImage.fromResource(R.drawable.ico_marker_02));
                            marker.setWidth(180);
                        marker.setHeight(180);
                            marker.setMap(naverMap);
                            meetingGroupMemberMarkerList.add(marker);
                        }


                    } else{
                        meetingGroupMemberMarkerList = null;
                    }
                } else {
                if (meetingGroupMemberMarkerList != null) {
                    if (meetingGroupMemberMarkerList.size() > 0) {
                        for (Marker marker : meetingGroupMemberMarkerList) {
                            marker.setMap(null);
                        }
                    }

                }
                    meetingGroupMemberMarkerList = null;
                }
            });


//        // API 응답에 따른 처리
//        mViewModel.mainViewModelApiResult.observe(this, o -> {
//            Log.e("로그", "!!!");
//            if(o != null){
//                if(o instanceof GetApiTest){
//                    Integer code = ((GetApiTest) o).getHeader().getCode();
//                    switch(code){
//                        case OK :
//                            binding.viewTest.setVisibility(View.GONE);
//                            break;
//
//                        default:
//
//                            Toast.makeText(mContext, code+"", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//
//
//
//                }
//            }
//        });
        }

        void init () {

            gpsTrackerReceiver = new GpsTrackerReceiver();
            binding.ivBottomMenuUserProfile.setOnClickListener(v -> {
                setBottomSheet(FRAGMENT_PAGE_BOTTOM_SHEET_PAGE_LOGIN);


            });

            binding.ivBottomMenuChat.setOnClickListener(v -> {
                if (binding.layoutChat.getVisibility() == View.VISIBLE) {
                    binding.layoutChat.setVisibility(View.GONE);
                    binding.edtxtChat.setText("");
                } else {
                    binding.edtxtChat.setText("");
                    binding.layoutChat.setVisibility(View.VISIBLE);

                }

            });
            binding.ivChatLayoutClose.setOnClickListener(v -> {
                binding.layoutChat.setVisibility(View.GONE);
            });
            binding.ivChatSend.setOnClickListener(v -> {
                binding.edtxtChat.setText("");
            });


            naverMapInit();
            btnSet();
        }

        void naverMapInit () {
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

        @Override
        public void onMapReady (@NonNull NaverMap map){
            this.naverMap = map;
            naverMap.setLocationSource(locationSource);
            UiSettings uiSettings = naverMap.getUiSettings();
            uiSettings.setLocationButtonEnabled(false);
            uiSettings.setZoomControlEnabled(false);

            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

            binding.viewMapLocationBtn.setMap(naverMap);
            binding.viewMapZoomCtrl.setMap(naverMap);
            mViewModel.getMeetingInfo();
            naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                    if (meetingMarker == null) {

                        meetingMarker = new Marker();
                        meetingMarker.setPosition(latLng);


                        MeetingGroupData meetingGroupData = new MeetingGroupData();
                        meetingGroupData.setPlaceLatitude(latLng.latitude);
                        meetingGroupData.setPlaceLongitude(latLng.longitude);
                        String address = GpsUtils.getCurrentAddress(mContext, latLng.latitude, latLng.longitude) + "";
                        meetingGroupData.setPlaceAddress(address);


//                    meetingMarker.setOnClickListener(new Overlay.OnClickListener() {
//                        @Override
//                        public boolean onClick(@NonNull Overlay overlay) {
//
//                            if(mViewModel.meetingData != null){
//                                Toast.makeText(mContext, latLng.latitude + "," + latLng.longitude, Toast.LENGTH_SHORT).show();
//                            }else{
//                                CreateMeetingDialog createMeetingDialog = new CreateMeetingDialog(mContext, new CreateMeetingDialog.CreateMeetingDialogCallback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        if(meetingMarker != null){
//                                            meetingMarker.setMap(null);
//                                            meetingMarker = null;
//                                            mViewModel.getMeetingInfo();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError() {
//
//                                    }
//
//                                    @Override
//                                    public void onCancel() {
//
//                                    }
//                                });
//                                createMeetingDialog.setData(meetingData);
//                                createMeetingDialog.show();
//                            }
//
//
//
//                            return false;
//                        }
//                    });
                        meetingMarker.setMap(naverMap);
                        CreateMeetingDialog createMeetingDialog = new CreateMeetingDialog(mContext, new CreateMeetingDialog.CreateMeetingDialogCallback() {
                            @Override
                            public void onSuccess() {

                                mViewModel.getMeetingInfo();

                            }

                            @Override
                            public void onError() {
                                if (meetingMarker != null) {
                                    meetingMarker.setMap(null);
                                    meetingMarker = null;

                                }
                            }

                            @Override
                            public void onCancel() {
                                if (meetingMarker != null) {
                                    meetingMarker.setMap(null);
                                    meetingMarker = null;

                                }
                            }
                        });
                        createMeetingDialog.setData(meetingGroupData);
                        createMeetingDialog.show();
                    }
                }
            });

        }


        public void setBottomSheet ( int pageType){


            switch (pageType) {
                case FRAGMENT_PAGE_BOTTOM_SHEET_PAGE_LOGIN:
                    BottomSheetLoginDialog bottomSheetLoginDialog = new BottomSheetLoginDialog();
                    bottomSheetLoginDialog.show(getSupportFragmentManager(), "");

                    break;

                case FRAGMENT_PAGE_BOTTOM_SHEET_PAGE_MEETING_GROUP:

                    break;


            }


        }
        void btnSet () {
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
        public void startLocationUpdates () {
            if (!checkPermissions()) {
                requestPermissions();
            } else {

                mService.requestLocationUpdates();
            }
        }
        public void stopLocationUpdates () {
            mService.removeLocationUpdates();
        }

        @Override
        protected void onStart () {
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
        protected void onResume () {
            super.onResume();

            LocalBroadcastManager.getInstance(this).registerReceiver(gpsTrackerReceiver,
                    new IntentFilter(LocationUpdateService.ACTION_BROADCAST));
        }

        @Override
        protected void onPause () {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(gpsTrackerReceiver);
            super.onPause();
        }
        @Override
        protected void onStop () {
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
        public boolean checkPermissions () {
            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }

        public void requestPermissions () {
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
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }

        /**
         * Callback received when a permissions request has been completed.
         */
        @Override
        public void onRequestPermissionsResult ( int requestCode,
        @NonNull String[] permissions, @NonNull int[] grantResults){
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
        public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String s){
            // Update the buttons state depending on whether location updates are being requested.
            if (s.equals(GpsUtils.KEY_REQUESTING_LOCATION_UPDATES)) {
                setTrackingState(sharedPreferences.getBoolean(GpsUtils.KEY_REQUESTING_LOCATION_UPDATES,
                        false));
            }
        }
        private void setTrackingState ( boolean requestingLocationUpdates){
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

    }