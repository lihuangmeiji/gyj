package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.CourierStationBean;
import com.system.technologyinformation.model.ProductItemInfo;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class CourierStationAdapter extends BaseQuickAdapter<CourierStationBean, BaseViewHolder> {

    public CourierStationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourierStationBean item) {

    }
}
