package com.system.technologyinformation.module.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.UserConsumptionBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserConsumptionAdapter extends BaseQuickAdapter<UserConsumptionBean, BaseViewHolder> {

    public UserConsumptionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserConsumptionBean item) {
        helper.setText(R.id.tv_user_integral_title, item.getShopName());
        helper.setText(R.id.tv_user_integral_num, "-￥" + item.getPrice());
        helper.setText(R.id.tv_user_integral_time, item.getCreateTime());
    }
}
