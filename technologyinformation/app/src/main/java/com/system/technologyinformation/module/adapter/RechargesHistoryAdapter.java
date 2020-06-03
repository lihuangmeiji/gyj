package com.system.technologyinformation.module.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.RechargesHistoryBean;

public class RechargesHistoryAdapter extends BaseQuickAdapter<RechargesHistoryBean,BaseViewHolder>{

    private RechargesHistoryInterface rechargesHistoryInterface;

    private ClipboardManager cm;
    private ClipData mClipData;

    public RechargesHistoryInterface getRechargesHistoryInterface() {
        return rechargesHistoryInterface;
    }

    public void setRechargesHistoryInterface(RechargesHistoryInterface rechargesHistoryInterface) {
        this.rechargesHistoryInterface = rechargesHistoryInterface;
    }

    public RechargesHistoryAdapter(int layoutResId){super(layoutResId);}

    @Override
    protected void convert(BaseViewHolder helper, RechargesHistoryBean bean) {

        final TextView tv_order_id = helper.getView(R.id.tv_order_id);
        tv_order_id.setText("订单编号:" + bean.getOrderNo());

        final String order_id = bean.getOrderNo();

        TextView tv_order_c_time = helper.getView(R.id.tv_order_c_time);
        tv_order_c_time.setText("下单时间:" + bean.getCreateAt());

        TextView recharges_description = helper.getView(R.id.recharges_decription);
        recharges_description.setText("话费充值"+bean.getTotalPrice()+"元");

        TextView recharges_coname = helper.getView(R.id.tv_co_name);
        recharges_coname.setText("充值号码: "+bean.getPhone()+"\n"+"充值面额: "+bean.getTotalPrice()+"元");


        TextView recharges_inprice = helper.getView(R.id.recharges_inprice);
        recharges_inprice.setText("¥ "+bean.getFinalPrice());

        TextView recharges_fail_description = helper.getView(R.id.recharge_fail_descrip);

        ImageView copy_order = helper.getView(R.id.copy_order);
        copy_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                mClipData = ClipData.newPlainText("Label", order_id);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(mContext,"复制成功",Toast.LENGTH_LONG).show();
            }
        });

        TextView tv_uo_status = helper.getView(R.id.tv_uo_status);

        switch (bean.getStatus()){
            case 1:
                tv_uo_status.setText("未支付");
                break;

            case 2:
                tv_uo_status.setText("已支付");
                break;
            case 3:
                tv_uo_status.setText("充值中");
                break;
            case 4:
                tv_uo_status.setText("充值失败");
                recharges_fail_description.setVisibility(View.VISIBLE);
                recharges_fail_description.setText("失败原因：" +bean.getErrorMsg());
                break;
        }

    }



    public interface RechargesHistoryInterface{

        void chargeAgain(RechargesHistoryBean bean);


    }
}


