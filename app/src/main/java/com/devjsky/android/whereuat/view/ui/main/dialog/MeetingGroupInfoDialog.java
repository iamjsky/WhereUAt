package com.devjsky.android.whereuat.view.ui.main.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.model.MeetingGroupData;
import com.devjsky.android.whereuat.view.base.BaseDialog;

/**
 * ClassName            CreateMeetingDialog
 * Created by JSky on   2022-02-24
 * <p>
 * Description
 */
public class MeetingGroupInfoDialog extends BaseDialog {

    MeetingGroupData meetingGroupData = null;
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

    public void setData(MeetingGroupData data){
        if(meetingGroupData != null){
            meetingGroupData = null;
        }

        meetingGroupData = data;
    }

    public void dialogCreateMeeting(){


    }

    public void closeDialog(){
        callback.onClose();
        dismiss();
    }
}
