package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;

import java.text.NumberFormat;

public class OnlineSupermarketAdapter extends MultiItemView<OnlineSupermarketBean> {

    private ShopCarModifyCountInterface shopCarModifyCountInterface;

    public ShopCarModifyCountInterface getShopCarModifyCountInterface() {
        return shopCarModifyCountInterface;
    }

    public void setShopCarModifyCountInterface(ShopCarModifyCountInterface shopCarModifyCountInterface) {
        this.shopCarModifyCountInterface = shopCarModifyCountInterface;
    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_online_supermarket;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final OnlineSupermarketBean onlineSupermarketBean, int i) {
        viewHolder.setText(R.id.tv_tr_name, onlineSupermarketBean.getName());
        ImageView iv = viewHolder.getView(R.id.iv_tr_ico);
        Glide.with(iv.getContext())
                .load(onlineSupermarketBean.getImage())
                .placeholder(R.mipmap.shoppingmr) // can also be a drawable
                .error(R.mipmap.shoppingmr) // will be displayed if the image cannot be loaded
                .into(iv);
        Double integral=onlineSupermarketBean.getCurrentPrice()*0.1/0.01;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        viewHolder.setText(R.id.tv_tr_original_price, "原价¥" + onlineSupermarketBean.getCurrentPrice());
        viewHolder.setText(R.id.tv_tr_original_integral,"+"+integral.intValue()+"积分");
        viewHolder.setText(R.id.tv_tr_discounted_prices,"折扣价¥"+ nf.format(onlineSupermarketBean.getCurrentPrice()*0.9));
        final TextView tv_tr_number = viewHolder.getView(R.id.tv_tr_number);
        tv_tr_number.setText("" + onlineSupermarketBean.getShopnumber());
        final ImageView iv_shopping_reduce = viewHolder.getView(R.id.iv_shopping_reduce);
        if(onlineSupermarketBean.getShopnumber()>0){
            iv_shopping_reduce.setVisibility(View.VISIBLE);
            tv_tr_number.setVisibility(View.VISIBLE);
        }else{
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
        final ImageView iv_shopping_increase = viewHolder.getView(R.id.iv_shopping_increase);
        iv_shopping_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = onlineSupermarketBean.getShopnumber();
                count++;
                /*    if(count<=onlineSupermarketBean.getAmount()){*/
                onlineSupermarketBean.setSc_isChoosed(true);
                onlineSupermarketBean.setShopnumber(count);
                shopCarModifyCountInterface.doIncrease();
                /*}else{
                    count--;
                    Toast.makeText(iv_shopping_increase.getContext(),"已超出购买数量",Toast.LENGTH_SHORT).show();
                }*/
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
}
