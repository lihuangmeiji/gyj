package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.UserFunctionListBean;
import com.system.technologyinformation.model.UserServiceFunctionListBean;

public class UserServiceFunctionAdapter extends BaseQuickAdapter<UserServiceFunctionListBean.UserServiceFunctionBean, BaseViewHolder> {

    public UserServiceFunctionAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, UserServiceFunctionListBean.UserServiceFunctionBean userFunctionBean) {
        baseViewHolder.setText(R.id.tv_function_title,userFunctionBean.getUf_name());
        ImageView iv_function_ico=baseViewHolder.getView(R.id.iv_function_ico);
        iv_function_ico.setImageResource(userFunctionBean.getUf_ico());
    }
}
