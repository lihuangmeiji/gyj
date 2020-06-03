package com.system.technologyinformation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;

public class Relativelayout_status_bar extends RelativeLayout {
    public Relativelayout_status_bar(Context context) {
        super(context);
    }

    public Relativelayout_status_bar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Relativelayout_status_bar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int finalHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            finalHeight = heightMeasureSpec + BarUtils.getStatusBarHeight(getContext());
        } else {
            finalHeight = heightMeasureSpec;
        }
        //Log.i("widthAndHeight", "onMeasure: finalHeight:"+finalHeight+"--finalWidth:"+finalWidth);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}
