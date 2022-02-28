package com.devjsky.android.whereuat.view.ui.main.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devjsky.android.whereuat.R;

import com.devjsky.android.whereuat.model.MeetingGroupData;
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
public class CreateMeetingDialog extends BaseDialog {

    MeetingGroupData meetingGroupData = null;
    CreateMeetingDialogCallback callback;

    public interface CreateMeetingDialogCallback {
        void onSuccess();
        void onError();
        void onCancel();
    }

    public CreateMeetingDialog(Context context, CreateMeetingDialogCallback callback) {
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

    public void setData(MeetingGroupData data){
        if(meetingGroupData != null){
            meetingGroupData = null;
        }

        meetingGroupData = data;
    }

    public void dialogCreateMeeting(){
        if(meetingGroupData != null){
            MeetingApi.createMeeting(meetingGroupData, new _CreateMeetingCallback() {
                @Override
                public void onSuccess(_CreateMeeting data) {
                    if(data.getHeader().getCode() == OK){
                        callback.onSuccess();


                        dismiss();
                    }

                }

                @Override
                public void onError(_CreateMeeting data) {
                    callback.onError();
                }
            });
        }

    }

    public void closeDialog(){
        callback.onCancel();
        dismiss();
    }
}
