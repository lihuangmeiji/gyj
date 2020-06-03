package com.system.technologyinformation.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.system.technologyinformation.R;

public class UserPromptPopupWindow extends PopupWindow {
    private UserPromptPopupWindowInterface userPromptPopupWindowInterface;

    public UserPromptPopupWindowInterface getUserPromptPopupWindowInterface() {
        return userPromptPopupWindowInterface;
    }

    public void setUserPromptPopupWindowInterface(UserPromptPopupWindowInterface userPromptPopupWindowInterface) {
        this.userPromptPopupWindowInterface = userPromptPopupWindowInterface;
    }


    public UserPromptPopupWindow(Context mContext, View parent, String title, String contnet, int colortype) {
        View view = View.inflate(mContext, R.layout.user_prompt_loading, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
        // LinearLayout ll_popup = (LinearLayout) view
        // .findViewById(R.id.ll_popup);
        // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
        // R.anim.push_bottom_in_1));

        setWidth(WindowManager.LayoutParams.FILL_PARENT);
        setHeight(WindowManager.LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(false);
        setOutsideTouchable(false);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        setContentView(view);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
        TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(contnet);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        if (title.trim().equals("取消订单")) {
            btn_dialog_confirm.setText("是");
            btn_dialog_cancel.setText("否");
        }
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                userPromptPopupWindowInterface.setCancelOnClickListener();
            }
        });
        btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                userPromptPopupWindowInterface.setConfirmOnClickListener();
            }
        });
    }

    /**
     * 点击事件
     */
    public interface UserPromptPopupWindowInterface {
        void setCancelOnClickListener();

        void setConfirmOnClickListener();
    }
}
