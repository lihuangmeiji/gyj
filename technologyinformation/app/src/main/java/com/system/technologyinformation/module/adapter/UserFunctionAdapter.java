package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.UserFunctionListBean;

public class UserFunctionAdapter extends BaseQuickAdapter<UserFunctionListBean.UserFunctionBean, BaseViewHolder> {

    public UserFunctionAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, UserFunctionListBean.UserFunctionBean userFunctionBean) {
        baseViewHolder.setText(R.id.tv_function_title,userFunctionBean.getUf_name());
        ImageView iv_function_ico=baseViewHolder.getView(R.id.iv_function_ico);
        iv_function_ico.setImageResource(userFunctionBean.getUf_ico());
    }
}
