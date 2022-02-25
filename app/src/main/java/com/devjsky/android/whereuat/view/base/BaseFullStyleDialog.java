package com.devjsky.android.whereuat.view.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.devjsky.android.whereuat.R;

/**
 * ClassName            BaseFullStyleDialog
 * Created by JSky on   2022-02-23
 * <p>
 * Description
 */
public class BaseFullStyleDialog extends Dialog {
    protected Context mContext;

    public BaseFullStyleDialog(Context context){
        super(context, R.style.FullScreenDialog_visibleSystemUi );
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;



        Window window = getWindow();
        if( window != null ) {
            // 백그라운드 투명
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams params = window.getAttributes();
            // 화면에 가득 차도록
            params.width         = WindowManager.LayoutParams.MATCH_PARENT;
            params.height        = WindowManager.LayoutParams.MATCH_PARENT;

            window.setAttributes( params );
            // UI 하단 정렬
            window.setGravity( Gravity.CENTER );
        }
    }

}