package com.system.technologyinformation.module.adapter;

import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.RefundsBean;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class RefundsAdapter extends BaseQuickAdapter<RefundsBean, BaseViewHolder> {

    private int selectId;

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public RefundsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundsBean item) {
        helper.setText(R.id.tv_refunds_content,item.getR_content());
        CheckBox checkBox=helper.getView(R.id.cb_refunds_select);
        if(selectId==helper.getPosition()){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
    }
}
