package com.system.technologyinformation.module.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.FeedbacksBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class FeedBacksAdapter extends BaseQuickAdapter<FeedbacksBean, BaseViewHolder> {

    private int selfFeedBackId;
    public int getSelfFeedBackId() {
        return selfFeedBackId;
    }

    public void setSelfFeedBackId(int selfFeedBackId) {
        this.selfFeedBackId = selfFeedBackId;
    }
    public FeedBacksAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedbacksBean item) {
        helper.setText(R.id.tv_fb_content_number, item.getNum()+"");
        ImageView iv_fb_ico=helper.getView(R.id.iv_fb_ico);
        if(selfFeedBackId==item.getId()){
            if(item.getBackName().equals("喜欢")){
                iv_fb_ico.setImageResource(R.mipmap.feedbacks_type1_yx);
            }else{
                iv_fb_ico.setImageResource(R.mipmap.feedbacks_type2_yx);
            }
        }else {
            if(item.getBackName().equals("喜欢")){
                iv_fb_ico.setImageResource(R.mipmap.feedbacks_type1_wx);
            }else{
                iv_fb_ico.setImageResource(R.mipmap.feedbacks_type2_wx);
            }
        }
    }

    @Override
    public FeedbacksBean getItem(int position) {
        return super.getItem(position);
    }
}
