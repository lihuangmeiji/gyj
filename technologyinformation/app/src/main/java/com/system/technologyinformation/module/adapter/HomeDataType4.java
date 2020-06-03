package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class HomeDataType4 extends MultiItemView<HomeDataBean> {
    Context context;
    boolean isRead=false;
    public HomeDataType4(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.adapter_home_data_type4;
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
        final JCVideoPlayerStandard videoplayer = viewHolder.getView(R.id.videoplayer);
        videoplayer.setUp(homeDataBean.getVideoUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
        Glide.with(context).load(homeDataBean.getVideoImg()).into(videoplayer.thumbImageView);
        videoplayer.loadingProgressBar.setVisibility(View.GONE);
        videoplayer.backButton.setVisibility(View.GONE);
        videoplayer.bottomProgressBar.setVisibility(View.GONE);
        videoplayer.bottomProgressBar.setAlpha(0);
        videoplayer.currentTimeTextView.setVisibility(View.GONE);
        videoplayer.fullscreenButton.setVisibility(View.GONE);
        videoplayer.bottomContainer.setVisibility(View.GONE);
        TextView tv_jf = viewHolder.getView(R.id.tv_jf);
        tv_jf.setText(homeDataBean.getPoints() + "积分");
    }


    @Override
    public boolean isForViewType(HomeDataBean item, int postion) {
        if (item.getContextType() == 2) {
            return true;
        }
        return false;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        WrapperUtils.setFullSpan(viewHolder);
    }

    public String timeCalculate(String timeCreate) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = df.parse(timeCreate);
            long diff = date.getTime() - d1.getTime();// 这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60))
                    / 1000;
            if (days != 0 && days < 30) {
                return days + "天前";
            } else if (days == 0 && hours != 0) {
                return hours + "小时前";
            } else if (days == 0 && hours == 0 && minutes != 0) {
                return minutes + "分钟前";
            } else if (days == 0 && hours == 0 && minutes == 0 && seconds != 0) {
                return seconds + "秒前";
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
        }
        return df1.format(d1);
    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    //Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    //Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    //Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    //Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    //Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    //Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    //Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    //Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    //Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    //Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    //Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    //Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    //Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    //Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    //Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }
}
