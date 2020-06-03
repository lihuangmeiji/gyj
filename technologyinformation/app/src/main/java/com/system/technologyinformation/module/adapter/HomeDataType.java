package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeDataBean;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;

public class HomeDataType extends MultiItemView<HomeDataBean>{


    public HomeDataType(Context context) {
        super();
        addChildeItemView(new HomeDataType1());
        addChildeItemView(new HomeDataType2(context));
        addChildeItemView(new HomeDataType3(context));
        addChildeItemView(new HomeDataType4(context));
    }

    @NonNull
    @Override
    public int getLayoutId() {
        return 0;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull HomeDataBean homeDataBean, int i) {

    }
}
