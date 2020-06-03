package com.system.technologyinformation.module.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ProductItemInfo;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserOrdreTitleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int selIndex;

    public int getSelIndex() {
        return selIndex;
    }

    public void setSelIndex(int selIndex) {
        this.selIndex = selIndex;
    }

    public UserOrdreTitleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_uo_title, item);
        if(getSelIndex()==helper.getPosition()){
            helper.getView(R.id.tv_uo_title_bs).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.tv_uo_title_bs).setVisibility(View.GONE);
        }
    }
}
