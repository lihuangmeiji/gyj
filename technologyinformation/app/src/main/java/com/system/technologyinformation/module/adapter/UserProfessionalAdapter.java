package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.widget.CircleImageView;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserProfessionalAdapter extends BaseQuickAdapter<IdentificationInfosBean, BaseViewHolder> {

    public UserProfessionalAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper,final IdentificationInfosBean item) {
      /*  CircleImageView img = helper.getView(R.id.iv_user_professional_ico);
        Glide.with(mContext).load(item.getCareerCertificate()).into(img);*/
        TextView ivUserProfessionalName = helper.getView(R.id.iv_user_professional_name);
        ivUserProfessionalName.setText(item.getName());
        if(item.isUse()==false){
            ivUserProfessionalName.setTextColor(ivUserProfessionalName.getContext().getResources().getColor(R.color.black));
            ivUserProfessionalName.setBackground(ivUserProfessionalName.getContext().getResources().getDrawable(R.drawable.bg_shape_rz1));
        }else{
            ivUserProfessionalName.setTextColor(ivUserProfessionalName.getContext().getResources().getColor(R.color.color37));
            ivUserProfessionalName.setBackgroundResource(R.drawable.bg_shape_rz2);
        }
        ivUserProfessionalName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.isUse()==false){
                    item.setUse(true);
                }else{
                    item.setUse(false);
                }
                notifyDataSetChanged();
            }
        });
    }
}
