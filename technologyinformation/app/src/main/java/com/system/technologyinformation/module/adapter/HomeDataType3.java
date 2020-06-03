package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeDataBean;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;
import com.wenld.multitypeadapter.utils.WrapperUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeDataType3 extends MultiItemView<HomeDataBean>{
    Context context;
    public HomeDataType3(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.adapter_home_data_type3;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull HomeDataBean homeDataBean, int i) {
        TextView tv_hometitle = viewHolder.getView(R.id.tv_hometitle);
        tv_hometitle.setText(homeDataBean.getTitle());
        TextView tv_hometype = viewHolder.getView(R.id.tv_hometype);
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(5);
        if(homeDataBean.getRgb()!=null&&!homeDataBean.getRgb().equals("")){
            drawable.setColor(Color.parseColor(homeDataBean.getRgb().trim()));
            drawable.setStroke(1, Color.parseColor(homeDataBean.getRgb().trim()));
            tv_hometype.setBackground(drawable);
            tv_hometype.setTextColor(Color.parseColor("#ffffff"));
        }
        tv_hometype.setText(homeDataBean.getCtName());
        TextView tv_homename = viewHolder.getView(R.id.tv_homename);
        tv_homename.setText(homeDataBean.getOriginName());
        TextView tv_hometime = viewHolder.getView(R.id.tv_hometime);
        tv_hometime.setText(TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.string2Millis(homeDataBean.getCreateAt())));
        List<String> homeImg = homeDataBean.getImgList();
        if (homeImg!=null&&homeImg.size() >= 1) {
            ImageView tv_homeimg1 = viewHolder.getView(R.id.tv_homeimg1);
            Glide.with(context).load(homeImg.get(0)).into(tv_homeimg1);
        }
        TextView tv_jf=viewHolder.getView(R.id.tv_jf);
        tv_jf.setText(homeDataBean.getPoints()+"积分");
    }

    @Override
    public boolean isForViewType(HomeDataBean item, int postion) {
        if (item.getContextType()==1) {
            return true;
        }
        return false;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        WrapperUtils.setFullSpan(viewHolder);
    }

}
