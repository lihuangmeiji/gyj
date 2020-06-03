package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.utils.TextUtil;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class PanicBuyingAdapter extends BaseQuickAdapter<HomeShoppingSpreeBean, BaseViewHolder> {

    public PanicBuyingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeShoppingSpreeBean item) {
        ImageView iv = helper.getView(R.id.iv_pb_ico);
        Glide.with(iv.getContext())
                .load(item.getImage())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .centerCrop()
                .into(iv);
        helper.setText(R.id.tv_pb_discounted_prices, TextUtil.FontHighlighting(iv.getContext(),"特价 ","¥"+item.getCurrentPrice(),R.style.tv_pb_discounted_prices1,R.style.tv_pb_discounted_prices2));
        helper.setText(R.id.tv_pb_original_integral, "¥"+item.getSourcePrice());
    }
}
