package com.system.technologyinformation.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;

public class LinearLayout_status_bar extends LinearLayout {
    public LinearLayout_status_bar(Context context) {
        super(context);
    }

    public LinearLayout_status_bar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayout_status_bar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int finalHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            finalHeight = (int) (finalWidth * 9.0f / 15) + BarUtils.getStatusBarHeight(getContext());
        } else {
            finalHeight = (int) (finalWidth * 9.0f / 15);
        }
        //Log.i("widthAndHeight", "onMeasure: finalHeight:"+finalHeight+"--finalWidth:"+finalWidth);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}
