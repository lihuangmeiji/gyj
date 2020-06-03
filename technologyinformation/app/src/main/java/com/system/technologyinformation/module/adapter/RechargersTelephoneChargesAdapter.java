package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.RechargersTelephoneChargesBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class RechargersTelephoneChargesAdapter extends BaseQuickAdapter<RechargersTelephoneChargesBean, BaseViewHolder> {

    public RechargersTelephoneChargesAdapter(int layoutResId) {
        super(layoutResId);
    }

    private int selectId;

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargersTelephoneChargesBean item) {
        helper.setText(R.id.tv_jg1, item.getSourcePrice() + "元");
        helper.setText(R.id.tv_jg2, "售价"+item.getCurrentPrice()+ "元");
        RelativeLayout rel_contnet=helper.getView(R.id.rel_contnet);
        if(getSelectId()==helper.getPosition()){
            rel_contnet.setBackground(rel_contnet.getContext().getResources().getDrawable(R.drawable.bg_shape_item_ce2));
        }else{
            rel_contnet.setBackground(rel_contnet.getContext().getResources().getDrawable(R.drawable.bg_shape_item_ce3));
        }
    }

    @Override
    public RechargersTelephoneChargesBean getItem(int position) {
        return super.getItem(position);
    }
}
