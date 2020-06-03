package com.system.technologyinformation.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends NestedScrollView {
    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mSVListener!=null){
            mSVListener.OnScrollChanger(this,l,t,oldl,oldt);
        }
    }

    public interface SVListener{
        void OnScrollChanger(MyScrollView scrollView, int l, int t, int oldl, int oldt);
    }

    private SVListener mSVListener;

    public void setSVListener(SVListener svListener){

        mSVListener=svListener;
    }
}
