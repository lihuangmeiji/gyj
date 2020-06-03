package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.FeedbacksBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.utils.TextUtil;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class HomeShoppingSpreeAdapter extends BaseQuickAdapter<HomeShoppingSpreeBean, BaseViewHolder> {


    public HomeShoppingSpreeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeShoppingSpreeBean item) {
        ImageView iv = helper.getView(R.id.iv_ss_ico);
        Glide.with(iv.getContext())
                .load(item.getImage())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .into(iv);
        helper.setText(R.id.tv_ss_name, item.getName());
        TextView tv_ss_discounted_prices=helper.getView(R.id.tv_ss_discounted_prices);
        if(item.getModel()==1){
            helper.setText(R.id.tv_ss_discounted_prices, "¥"+item.getCurrentPrice());
        }else{
            if (!EmptyUtils.isEmpty(item.getCurrentPrice()) && item.getCurrentPrice() > 0) {
                if (item.getPoint() > 0) {
                    tv_ss_discounted_prices.setText(TextUtil.FontHighlighting(tv_ss_discounted_prices.getContext(), "¥", "" + item.getCurrentPrice(), "+" + item.getPoint() + "积分", R.style.tv_ce_point1, R.style.tv_ce_point2, R.style.tv_ce_point1));
                } else {
                    tv_ss_discounted_prices.setText(TextUtil.FontHighlighting(tv_ss_discounted_prices.getContext(), "¥", "" + item.getCurrentPrice(), R.style.tv_ce_point1, R.style.tv_ce_point2));
                }
            } else {
                if (item.getPoint() > 0) {
                    tv_ss_discounted_prices.setText(TextUtil.FontHighlighting(tv_ss_discounted_prices.getContext(), item.getPoint() + "", "积分", R.style.tv_ce_point2, R.style.tv_ce_point1));
                }
            }
        }
    }

    @Override
    public HomeShoppingSpreeBean getItem(int position) {
        return super.getItem(position);
    }
}
