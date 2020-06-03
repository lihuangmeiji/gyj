package com.system.technologyinformation.widget.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;


import com.system.technologyinformation.R;

import butterknife.BindView;


/**
 * Created by zhaotun on 2017/2/21.
 */

public class DialogLoading extends BaseDialog {

    @BindView(R.id.tv_loading_text)
    TextView tvText;
    @BindView(R.id.iv_load)
    ImageView ivLoad;
    AnimationDrawable rocketAnimation;

    public DialogLoading(Context context) {
        this(context, false);
    }

    public DialogLoading(Context context, boolean shouldSetWindow) {
        super(context, R.style.customDialog_loading);
        shouldSetWindow(shouldSetWindow);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void init() {
        ivLoad.setBackgroundResource(R.drawable.bg_load);
        rocketAnimation = (AnimationDrawable) ivLoad.getBackground();
        rocketAnimation.start();
    }

    public void setText(String text) {
        if (tvText != null) {
            tvText.setText(text);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (rocketAnimation != null) {
            rocketAnimation.stop();
        }
    }
}
