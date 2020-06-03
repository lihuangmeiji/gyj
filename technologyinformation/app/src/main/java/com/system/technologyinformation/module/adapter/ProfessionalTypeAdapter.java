package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.ProfessionalTypeBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class ProfessionalTypeAdapter extends BaseQuickAdapter<ProfessionalTypeBean, BaseViewHolder> {

    public ProfessionalTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProfessionalTypeBean item) {
        helper.setText(R.id.tv_professional_type, item.getName());
    }
}
