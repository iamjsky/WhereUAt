package com.devjsky.android.whereuat.view.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.devjsky.android.whereuat.R;
import com.devjsky.android.whereuat.common.Constants;
import com.devjsky.android.whereuat.net.HttpStatusCode;

/**
 * ClassName            BaseDialog
 * Created by JSky on   2022-02-23
 * <p>
 * Description
 */
public class BaseDialog extends Dialog implements Constants, HttpStatusCode {
    protected Context mContext;

    public BaseDialog(Context context){
        super( context );

        this.mContext = context;

//        setCancelable(false);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if( window != null ) {
//            // 백그라운드 투명
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params = window.getAttributes();
//            // 화면에 가득 차도록
//            params.width         = WindowManager.LayoutParams.MATCH_PARENT;
//            params.height        = WindowManager.LayoutParams.MATCH_PARENT;

            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationDialogStyle;
            window.setAttributes( params );
            // UI 하단 정렬
            window.setGravity( Gravity.CENTER );
        }
    }


}