package com.devjsky.android.whereuat.view.ui.main.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.model.MeetingData;
import com.devjsky.android.whereuat.net.api.MeetingApi;
import com.devjsky.android.whereuat.net.api.callback._CreateMeetingCallback;
import com.devjsky.android.whereuat.net.pojo._CreateMeeting;
import com.devjsky.android.whereuat.view.base.BaseDialog;

/**
 * ClassName            CreateMeetingDialog
 * Created by JSky on   2022-02-24
 * <p>
 * Description
 */
public class MeetingGroupInfoDialog extends BaseDialog {

    MeetingData meetingData = null;
    MeetingGroupInfoDialogCallback callback;

    public interface MeetingGroupInfoDialogCallback {
        void onClose();
        void onFinishMeeting();
    }

    public MeetingGroupInfoDialog(Context context, MeetingGroupInfoDialogCallback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_meeting);
        Button btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreateMeeting();
            }
        });
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

    }

    public void setData(MeetingData data){
        if(meetingData != null){
            meetingData = null;
        }

        meetingData = data;
    }

    public void dialogCreateMeeting(){


    }

    public void closeDialog(){
        callback.onClose();
        dismiss();
    }
}
