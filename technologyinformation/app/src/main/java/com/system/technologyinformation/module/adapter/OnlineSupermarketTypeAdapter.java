package com.system.technologyinformation.module.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.model.OnlineSupermarketTypeBean;
import com.system.technologyinformation.R;

public class OnlineSupermarketTypeAdapter extends BaseQuickAdapter<OnlineSupermarketTypeBean, BaseViewHolder> {

    private int selectId;

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public OnlineSupermarketTypeAdapter(int layoutResId){
        super(layoutResId);
    }

    @Override
    public OnlineSupermarketTypeBean getItem(int position) {
        return super.getItem(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, OnlineSupermarketTypeBean item) {
        helper.setText(R.id.tv_tr_type,item.getName());
        RelativeLayout rel_tr_type=helper.getView(R.id.rel_tr_type);
        View v_select=helper.getView(R.id.v_select);
        TextView tv_tr_type=helper.getView(R.id.tv_tr_type);
        if(selectId==item.getId()){
            rel_tr_type.setBackgroundColor(rel_tr_type.getContext().getResources().getColor(R.color.white));
            tv_tr_type.setTextColor(tv_tr_type.getContext().getResources().getColor(R.color.color28));
            v_select.setVisibility(View.VISIBLE);
        }else{
            rel_tr_type.setBackgroundColor(rel_tr_type.getContext().getResources().getColor(R.color.color25));
            v_select.setVisibility(View.GONE);
            tv_tr_type.setTextColor(tv_tr_type.getContext().getResources().getColor(R.color.black));
        }
    }
}
