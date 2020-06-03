package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.OnlineSupermarketBean;

public class ConfirmOrderAdapter extends BaseQuickAdapter<OnlineSupermarketBean, BaseViewHolder> {

    public ConfirmOrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, OnlineSupermarketBean onlineSupermarketBean) {
        ImageView iv = viewHolder.getView(R.id.iv_co_ico);
        Glide.with(iv.getContext())
                .load(onlineSupermarketBean.getImage())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .centerCrop()
                .into(iv);
        viewHolder.setText(R.id.tv_co_name, onlineSupermarketBean.getName());
        viewHolder.setText(R.id.tv_co_number,"*"+onlineSupermarketBean.getShopnumber());
        viewHolder.setText(R.id.tv_co_price,"¥" + (onlineSupermarketBean.getCurrentPrice()*onlineSupermarketBean.getShopnumber()));
    }
}
