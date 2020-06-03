package com.system.technologyinformation.module.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.DailyMissionBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class DailyMissionAdapter extends BaseQuickAdapter<DailyMissionBean, BaseViewHolder> {

    private boolean islock;

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public DailyMissionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyMissionBean item) {
        ImageView iv = helper.getView(R.id.iv_dm_ico);
        Glide.with(iv.getContext())
                .load(item.getIcon())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .centerCrop()
                .into(iv);
        helper.setText(R.id.tv_dm_title, item.getName());
        helper.setText(R.id.tv_dm_integral, "+" + item.getPoint() + "积分");
        TextView tv_dm_number_ts = helper.getView(R.id.tv_dm_number_ts);
        if (item.isAccomplish()) {
            tv_dm_number_ts.setText("已完成");
            tv_dm_number_ts.setTextColor(tv_dm_number_ts.getContext().getResources().getColor(R.color.white));
            tv_dm_number_ts.setBackgroundResource(R.drawable.bg_shape_daily_mission1);
        } else {
            tv_dm_number_ts.setText("去完成");
            tv_dm_number_ts.setTextColor(tv_dm_number_ts.getContext().getResources().getColor(R.color.color37));
            tv_dm_number_ts.setBackgroundResource(R.drawable.bg_shape_daily_mission2);
        }
        if (item.getType() == 2 && islock == false) {
            tv_dm_number_ts.setText("未解锁");
            tv_dm_number_ts.setTextColor(tv_dm_number_ts.getContext().getResources().getColor(R.color.white));
            tv_dm_number_ts.setBackgroundResource(R.drawable.bg_shape_daily_mission1);
        }
        if (item.getCode().contains("share.link")) {
            helper.setText(R.id.tv_dm_bumber, item.getShareTotal() + "/" + item.getMaxShare());
            helper.getView(R.id.tv_dm_bumber).setVisibility(View.VISIBLE);
            if(item.getShareTotal()>=item.getMaxShare()){
                tv_dm_number_ts.setText("已完成");
                tv_dm_number_ts.setTextColor(tv_dm_number_ts.getContext().getResources().getColor(R.color.white));
                tv_dm_number_ts.setBackgroundResource(R.drawable.bg_shape_daily_mission1);
            }else{
                tv_dm_number_ts.setText("去完成");
                tv_dm_number_ts.setTextColor(tv_dm_number_ts.getContext().getResources().getColor(R.color.color37));
                tv_dm_number_ts.setBackgroundResource(R.drawable.bg_shape_daily_mission2);
            }
        } else {
            helper.getView(R.id.tv_dm_bumber).setVisibility(View.GONE);
        }
    }

    @Override
    public DailyMissionBean getItem(int position) {
        return super.getItem(position);
    }
}
