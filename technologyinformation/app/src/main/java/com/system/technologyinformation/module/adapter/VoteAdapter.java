package com.system.technologyinformation.module.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.VoteBean;
import com.system.technologyinformation.model.VoteOptionListBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class VoteAdapter extends BaseQuickAdapter<VoteOptionListBean, BaseViewHolder> {

    private boolean isShow;
    private CheckInterface checkInterface;
    private int sunnum;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getSunnum() {
        return sunnum;
    }

    public void setSunnum(int sunnum) {
        this.sunnum = sunnum;
    }

    public CheckInterface getCheckInterface() {
        return checkInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public VoteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VoteOptionListBean item) {
        helper.setText(R.id.tv_vote_content, item.getName());
        final CheckBox cb_vote_select = helper.getView(R.id.cb_vote_select);
        cb_vote_select.setChecked(item.isChoosed());
        cb_vote_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInterface.dcheckGroup(item.getId(), cb_vote_select.isChecked());
            }
        });

        if (isShow) {
            cb_vote_select.setEnabled(true);
            helper.getView(R.id.tv_vote_number).setVisibility(View.VISIBLE);
            if (getSunnum() != 0) {
                DecimalFormat df = new DecimalFormat("0.00%");
                BigDecimal d = new BigDecimal(item.getNum() / getSunnum());
                String percent = df.format(d);
                helper.setText(R.id.tv_vote_number, percent + "票");
            }
        } else {
            cb_vote_select.setEnabled(false);
            helper.getView(R.id.tv_vote_number).setVisibility(View.GONE);
        }
    }

    /**
     * 评论复选框
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void dcheckGroup(int groupPosition, boolean isChecked);
    }
}
