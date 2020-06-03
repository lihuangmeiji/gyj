package com.system.technologyinformation.module.adapter;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeMessage;
import com.system.technologyinformation.model.UserMessage;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class HomeMessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeMessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_home_message_title,item);
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
