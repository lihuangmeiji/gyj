package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.utils.TextUtil;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class CreditsExchangeAdapter extends BaseQuickAdapter<HomeShoppingSpreeBean, BaseViewHolder> {

    public CreditsExchangeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeShoppingSpreeBean item) {
        ImageView iv = helper.getView(R.id.iv_ce_ico);
        Glide.with(iv.getContext())
                .load(item.getImage())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .centerCrop()
                .into(iv);
        helper.setText(R.id.tv_ce_name, item.getName());
        TextView tv_ce_point = helper.getView(R.id.tv_ce_point);
        if (!EmptyUtils.isEmpty(item.getCurrentPrice()) && item.getCurrentPrice() > 0) {
            if (item.getPoint() > 0) {
                tv_ce_point.setText(TextUtil.FontHighlighting(tv_ce_point.getContext(), "¥", "" + item.getCurrentPrice(), "+" + item.getPoint() + "积分", R.style.tv_ce_point1, R.style.tv_ce_point2, R.style.tv_ce_point1));
            } else {
                tv_ce_point.setText(TextUtil.FontHighlighting(tv_ce_point.getContext(), "¥", "" + item.getCurrentPrice(), R.style.tv_ce_point1, R.style.tv_ce_point2));
            }
        } else {
            if (item.getPoint() > 0) {
                tv_ce_point.setText(TextUtil.FontHighlighting(tv_ce_point.getContext(), item.getPoint() + "", "积分", R.style.tv_ce_point2, R.style.tv_ce_point1));
            }
        }
        helper.setText(R.id.tv_ce_number, "限量" + item.getLimit() + "份");
        helper.setText(R.id.tv_ce_sold_total, item.getSoldTotal() + "人已领");
    }

    @Override
    public HomeShoppingSpreeBean getItem(int position) {
        return super.getItem(position);
    }
}
