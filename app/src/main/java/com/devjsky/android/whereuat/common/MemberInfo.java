package com.devjsky.android.whereuat.common;

import com.devjsky.android.whereuat.model.Member;
import com.devjsky.android.whereuat.model.User;

import lombok.Data;

/**
 * ClassName            MemberInfo
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
@Data
public class MemberInfo {
    private static MemberInfo instance = null;
    public static MemberInfo getInstance(){
        if(instance == null){
            instance = new MemberInfo();
        }

        return instance;
    }

    private String mem_token="";
    private Integer memIdx=-1;
    private boolean isMeeting = false;
    private String memProfileImgUrl="";
}
