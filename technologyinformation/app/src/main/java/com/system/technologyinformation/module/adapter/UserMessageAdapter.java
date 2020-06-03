package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.UserMessage;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserMessageAdapter extends BaseQuickAdapter<UserMessage, BaseViewHolder> {

    public UserMessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserMessage item) {
     /*   ImageView img = helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(item.getImg_url()).into(img);
        helper.setText(R.id.tv_product_title, item.getName());
        helper.setText(R.id.tv_product_price, "¥" + item.getAmount());*/
        helper.setText(R.id.tv_user_message_title, item.getTitle());
        helper.setText(R.id.tv_user_message_content,item.getContent());
        //TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.string2Millis(homeDataBean.getCreateAt()))  TimeUtils.date2String(TimeUtils.string2Date(item.getCreateAt()),"yyyy-MM-dd")
        helper.setText(R.id.tv_user_message_time,TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.string2Millis(item.getCreateAt())) );
    }

    @Override
    public UserMessage getItem(int position) {
        return super.getItem(position);
    }
}
