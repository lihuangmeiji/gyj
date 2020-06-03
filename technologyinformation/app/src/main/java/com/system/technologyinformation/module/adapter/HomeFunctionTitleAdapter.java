package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeFunctionBean;
import com.system.technologyinformation.model.HomeFunctionListBean;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;


public class HomeFunctionTitleAdapter extends MultiItemView<String> {


    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_function_title;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull String str, int i) {

    }
}
