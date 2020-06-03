package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.TableReservationBean;
import com.system.technologyinformation.utils.CornerTransform;
import com.system.technologyinformation.widget.ImageView_1_1;
import com.system.technologyinformation.widget.RoundImageView;

import java.text.NumberFormat;

public class TableReservationAdapter extends BaseQuickAdapter<OnlineSupermarketBean, BaseViewHolder> {
    private ShopCarModifyCountInterface shopCarModifyCountInterface;

    public ShopCarModifyCountInterface getShopCarModifyCountInterface() {
        return shopCarModifyCountInterface;
    }

    public void setShopCarModifyCountInterface(ShopCarModifyCountInterface shopCarModifyCountInterface) {
        this.shopCarModifyCountInterface = shopCarModifyCountInterface;
    }

    public TableReservationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final OnlineSupermarketBean onlineSupermarketBean) {
        RoundImageView iv = viewHolder.getView(R.id.iv_tr_ico);
        Glide.with(iv.getContext())
                .load(onlineSupermarketBean.getImage())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .into(iv);
        viewHolder.setText(R.id.tv_tr_name, onlineSupermarketBean.getName());
        viewHolder.setText(R.id.tv_tr_desc, onlineSupermarketBean.getDesc());
        Double integral = onlineSupermarketBean.getCurrentPrice() * 0.1 / 0.01;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        viewHolder.setText(R.id.tv_tr_prices, "¥" + onlineSupermarketBean.getCurrentPrice());
        viewHolder.setText(R.id.tv_tr_original_integral, "+" + integral.intValue() + "积分");
        viewHolder.setText(R.id.tv_tr_discounted_prices, "折扣价¥" + nf.format(onlineSupermarketBean.getCurrentPrice() * 0.9));
        final TextView tv_tr_number = viewHolder.getView(R.id.tv_tr_number);
        tv_tr_number.setText("" + onlineSupermarketBean.getShopnumber());
        final ImageView iv_shopping_reduce = viewHolder.getView(R.id.iv_shopping_reduce);
        if (onlineSupermarketBean.getShopnumber() > 0) {
            iv_shopping_reduce.setVisibility(View.VISIBLE);
            tv_tr_number.setVisibility(View.VISIBLE);
        } else {
            iv_shopping_reduce.setVisibility(View.GONE);
            tv_tr_number.setVisibility(View.GONE);
        }
        iv_shopping_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = onlineSupermarketBean.getShopnumber();
                count--;
                if (count == 0) {
                    iv_shopping_reduce.setVisibility(View.GONE);
                    onlineSupermarketBean.setSc_isChoosed(false);
                    onlineSupermarketBean.setShopnumber(0);
                } else {
                    iv_shopping_reduce.setVisibility(View.VISIBLE);
                    onlineSupermarketBean.setSc_isChoosed(true);
                    onlineSupermarketBean.setShopnumber(count);
                }
                shopCarModifyCountInterface.doDecrease();
            }
        });
        if (onlineSupermarketBean.getInventory() == null || onlineSupermarketBean.getInventory().intValue() == 0) {
            viewHolder.getView(R.id.tv_inventory).setVisibility(View.VISIBLE);
        } else {
            viewHolder.getView(R.id.tv_inventory).setVisibility(View.GONE);
        }
        final ImageView iv_shopping_increase = viewHolder.getView(R.id.iv_shopping_increase);
        iv_shopping_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onlineSupermarketBean.getInventory() == null || onlineSupermarketBean.getInventory().intValue() == 0) {
                    Toast.makeText(iv_shopping_increase.getContext(), "已售罄", Toast.LENGTH_SHORT).show();
                } else {
                    int count = onlineSupermarketBean.getShopnumber();
                    count++;
                    /*    if(count<=onlineSupermarketBean.getAmount()){*/
                    onlineSupermarketBean.setSc_isChoosed(true);
                    onlineSupermarketBean.setShopnumber(count);
                    shopCarModifyCountInterface.doIncrease();
                }
            }
        });
    }


    /**
     * 改变数量的接口
     */
    public interface ShopCarModifyCountInterface {
        void doIncrease();

        void doDecrease();

        void childDelete(OnlineSupermarketBean onlineSupermarketBean);

        void checkChild(boolean isChecked);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
