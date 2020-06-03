package com.system.technologyinformation.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ImageView_4_3 extends android.support.v7.widget.AppCompatImageView {
    public ImageView_4_3(Context context) {
        super(context);
    }

    public ImageView_4_3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView_4_3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int finalHeight = (int) (finalWidth * 3.0f / 4);
        //Log.i("widthAndHeight", "onMeasure: finalHeight:"+finalHeight+"--finalWidth:"+finalWidth);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}
