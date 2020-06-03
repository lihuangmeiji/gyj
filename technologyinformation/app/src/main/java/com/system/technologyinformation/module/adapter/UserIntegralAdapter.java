package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.UserIntegralBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserIntegralAdapter extends BaseQuickAdapter<UserIntegralBean, BaseViewHolder> {
    Context context;
    public UserIntegralAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserIntegralBean item) {
        helper.setText(R.id.tv_user_integral_title, item.getTypeName());
        TextView tv_user_integral_num = helper.getView(R.id.tv_user_integral_num);
        if(item.getDelta()<0){
            tv_user_integral_num.setText(item.getDelta()+"");
            tv_user_integral_num.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            tv_user_integral_num.setText("+"+item.getDelta());
            tv_user_integral_num.setTextColor(context.getResources().getColor(R.color.color37));
        }
        if(EmptyUtils.isEmpty(item.getDeduction())||item.getDeduction()==0||item.getDeduction()==0.0||item.getDeduction()==0.00){
            helper.getView(R.id.tv_user_integral_sm).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.tv_user_integral_sm).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_user_integral_sm,"抵扣现金:"+item.getDeduction());
        }
        helper.setText(R.id.tv_user_integral_time, TimeUtils.date2String(TimeUtils.string2Date(item.getCreateAt()),"yyyy/MM/dd HH:mm"));
    }
}
