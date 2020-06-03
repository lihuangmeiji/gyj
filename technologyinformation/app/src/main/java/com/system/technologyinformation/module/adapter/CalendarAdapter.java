package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.CalendarBean;
import com.system.technologyinformation.model.ProductItemInfo;

import java.util.Calendar;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class CalendarAdapter extends BaseQuickAdapter<CalendarBean, BaseViewHolder> {

    private int mMonth;

    private int year;

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public CalendarAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CalendarBean item) {
        TextView tv_calendar=helper.getView(R.id.tv_calendar);
        if(item.getCalendarNumber()==1){
            tv_calendar.setBackgroundResource(R.drawable.bg_shape_cicle_sign1);
            tv_calendar.setTextColor(helper.itemView.getResources().getColor(R.color.white));
            tv_calendar.setText(item.getCalendarContent());
        }else if(item.getCalendarNumber()==99){
            tv_calendar.setBackground(null);
            tv_calendar.setTextColor(helper.itemView.getResources().getColor(R.color.black));
            tv_calendar.setText(item.getCalendarContent());
        }else{
            Calendar ctime = Calendar.getInstance();
            int day = ctime.get(Calendar.DAY_OF_MONTH);// 获取当前天数
            int mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
            int mYear = ctime.get(Calendar.YEAR);
            int content=Integer.valueOf(item.getCalendarContent()).intValue();
            if(content<day&&mMonth==getmMonth()&&mYear==getYear()){
                tv_calendar.setBackgroundResource(R.drawable.bg_shape_cicle_sign2);
                tv_calendar.setTextColor(helper.itemView.getResources().getColor(R.color.color37));
                tv_calendar.setText("补");
            }else{
                tv_calendar.setBackground(null);
                tv_calendar.setTextColor(helper.itemView.getResources().getColor(R.color.tabwd));
                tv_calendar.setText(item.getCalendarContent());
            }
        }
    }

    @Override
    public CalendarBean getItem(int position) {
        return super.getItem(position);
    }
}
