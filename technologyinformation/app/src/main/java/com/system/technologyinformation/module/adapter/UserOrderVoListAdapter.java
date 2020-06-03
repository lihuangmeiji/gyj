package com.system.technologyinformation.module.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.UserOrderBean;
import com.system.technologyinformation.model.UserOrderVoListBean;
import com.system.technologyinformation.utils.TextUtil;

public class UserOrderVoListAdapter extends BaseQuickAdapter<UserOrderBean.ProductsBean, BaseViewHolder> {

    public UserOrderVoListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UserOrderBean.ProductsBean productsBean) {
        if (productsBean.getProduct() != null) {
            ImageView iv = viewHolder.getView(R.id.iv_co_ico);
            Glide.with(iv.getContext())
                    .load(productsBean.getProduct().getImage())
                    .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                    .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                    .centerCrop()
                    .into(iv);
            viewHolder.setText(R.id.tv_co_name, productsBean.getProduct().getName());
            viewHolder.setText(R.id.tv_co_number, "*" + productsBean.getNum());
            if (productsBean.getProduct().getModel() == 2) {
                TextView tv_co_price = viewHolder.getView(R.id.tv_co_price);
                if (!EmptyUtils.isEmpty(productsBean.getProduct().getCurrentPrice()) && productsBean.getProduct().getCurrentPrice() > 0) {
                    if (productsBean.getProduct().getPoint() > 0) {
                        tv_co_price.setText(TextUtil.FontHighlighting(tv_co_price.getContext(), "¥", "" + productsBean.getProduct().getCurrentPrice(), "+" + productsBean.getProduct().getPoint() + "积分", R.style.tv_ce_point1, R.style.tv_ce_point2, R.style.tv_ce_point1));
                    } else {
                        tv_co_price.setText(TextUtil.FontHighlighting(tv_co_price.getContext(), "¥", "" + productsBean.getProduct().getCurrentPrice(), R.style.tv_ce_point1, R.style.tv_ce_point2));
                    }
                } else {
                    if (productsBean.getProduct().getPoint() > 0) {
                        tv_co_price.setText(TextUtil.FontHighlighting(tv_co_price.getContext(), productsBean.getProduct().getPoint() + "", "积分", R.style.tv_ce_point2, R.style.tv_ce_point1));
                    }
                }
                //viewHolder.setText(R.id.tv_co_price,"积分" + productsBean.getProduct().getPoint());
            } else {
                viewHolder.setText(R.id.tv_co_price, "¥" + productsBean.getProduct().getCurrentPrice());
            }
        } else {
            viewHolder.getView(R.id.iv_co_ico).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_co_name).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_co_number).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_co_price).setVisibility(View.GONE);
        }
    }
}
