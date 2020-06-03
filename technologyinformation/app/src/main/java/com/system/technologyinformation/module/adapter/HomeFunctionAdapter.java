package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeFunctionBean;


public class HomeFunctionAdapter extends BaseQuickAdapter<HomeFunctionBean, BaseViewHolder> {

    public HomeFunctionAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeFunctionBean commodityBean) {
        baseViewHolder.setText(R.id.tv_function_title,commodityBean.getHf_name());
        ImageView iv_function_ico=baseViewHolder.getView(R.id.iv_function_ico);
        iv_function_ico.setImageResource(commodityBean.getHf_ico());
    }
}
