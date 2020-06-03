package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.DeliveryInfoBean;
import com.system.technologyinformation.model.UserMessage;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserLogisticsAdapter extends BaseQuickAdapter<DeliveryInfoBean, BaseViewHolder> {

    public UserLogisticsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, DeliveryInfoBean item) {
        ImageView iv_zx = helper.getView(R.id.iv_zx);
        helper.setText(R.id.tvAcceptStation, item.getInfo());
        helper.setText(R.id.tvAcceptTime, item.getDateTime());
        TextView tvTopLine = helper.getView(R.id.tv_top_line);
        if (helper.getPosition() == 0) {
            // 第一行头的竖线不显示
            tvTopLine.setVisibility(View.INVISIBLE);
            iv_zx.setVisibility(View.VISIBLE);
            iv_zx.setEnabled(true);
            iv_zx.setSelected(true);
            // 字体颜色加深
        } else {
            iv_zx.setVisibility(View.INVISIBLE);
            iv_zx.setEnabled(false);
            iv_zx.setSelected(false);
            tvTopLine.setVisibility(View.VISIBLE);
        }
        TextView tvBootomLine = helper.getView(R.id.tv_bootom_line);
        if (helper.getPosition() == getItemCount() - 1) {
            // 第一行头的竖线不显示
            tvBootomLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
        } else {
            tvBootomLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getParentPosition(@NonNull DeliveryInfoBean item) {
        return super.getParentPosition(item);
    }

    @Override
    public DeliveryInfoBean getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


}
