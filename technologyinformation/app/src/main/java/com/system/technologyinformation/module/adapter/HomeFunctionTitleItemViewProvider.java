package com.system.technologyinformation.module.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ConvenientFunctionsBean;
import com.system.technologyinformation.model.HomeConfigurationInformationBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.utils.ScreenUtil;
import com.system.technologyinformation.widget.LinearLayout_status_bar;

import me.drakeet.multitype.ItemViewBinder;

public class HomeFunctionTitleItemViewProvider extends ItemViewBinder<HomeConfigurationInformationBean, HomeFunctionTitleItemViewProvider.ViewHolder> {

    private HomeFunctionTitleInterface homeFunctionTitleInterface;

    public HomeFunctionTitleInterface getHomeFunctionTitleInterface() {
        return homeFunctionTitleInterface;
    }

    public void setHomeFunctionTitleInterface(HomeFunctionTitleInterface homeFunctionTitleInterface) {
        this.homeFunctionTitleInterface = homeFunctionTitleInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_home_function_title, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, @NonNull HomeConfigurationInformationBean homeConfigurationInformationBean) {
        if (!homeConfigurationInformationBean.getName().equals("默认")) {
            Glide.with(viewHolder.relBg.getContext())
                    .load(homeConfigurationInformationBean.getHomeImg())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            Drawable drawable = new BitmapDrawable(bitmap);
                            viewHolder.relBg.setBackground(drawable);
                        }
                    });
            Glide.with(viewHolder.iv_home_scanning.getContext())
                    .load(homeConfigurationInformationBean.getScanImg())
                    .asBitmap()
                    .into(viewHolder.iv_home_scanning);
            Glide.with(viewHolder.iv_home_sign.getContext())
                    .load(homeConfigurationInformationBean.getCheckInImg())
                    .asBitmap()
                    .into(viewHolder.iv_home_sign);
            viewHolder.tvHomeScanning.setTextColor(Color.parseColor(homeConfigurationInformationBean.getTextColor()));
            viewHolder.tvHomeSign.setTextColor(Color.parseColor(homeConfigurationInformationBean.getTextColor()));
            ViewGroup.LayoutParams lp = viewHolder.relBg.getLayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                lp.height = (int) ((ScreenUtil.getScreenWidth() - 30) * 9.0f / 15) + BarUtils.getStatusBarHeight(viewHolder.relBg.getContext());
            } else {
                lp.height = (int) ((ScreenUtil.getScreenWidth() - 30) * 9.0f / 15);
            }
            viewHolder.relBg.setLayoutParams(lp);
        }else{
            ViewGroup.LayoutParams lp = viewHolder.relBg.getLayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                lp.height = (int) ((ScreenUtil.getScreenWidth() - 30) * 9.0f / 21) + BarUtils.getStatusBarHeight(viewHolder.relBg.getContext());
            } else {
                lp.height = (int) ((ScreenUtil.getScreenWidth() - 30) * 9.0f / 21);
            }
            viewHolder.relBg.setLayoutParams(lp);
        }
        viewHolder.lin_bootom.setVisibility(View.GONE);
        viewHolder.lin_top.setVisibility(View.VISIBLE);
        viewHolder.lin_home_scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFunctionTitleInterface.setHomeFunctionTitleOnClickListener(1);
            }
        });

        viewHolder.lin_home_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFunctionTitleInterface.setHomeFunctionTitleOnClickListener(2);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout relBg;
        private TextView tvHomeScanning;
        private TextView tvHomeSign;
        private TextView tvBootomContent;
        private LinearLayout lin_bootom;
        private LinearLayout lin_top;
        private LinearLayout lin_home_sign;
        private LinearLayout lin_home_scanning;
        private ImageView iv_home_scanning;
        private ImageView iv_home_sign;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHomeScanning = (TextView) itemView.findViewById(R.id.tv_home_scanning);
            tvHomeSign = (TextView) itemView.findViewById(R.id.tv_home_sign);
            tvBootomContent = (TextView) itemView.findViewById(R.id.tv_bootom_content);
            lin_bootom = (LinearLayout) itemView.findViewById(R.id.lin_bootom);
            lin_top = (LinearLayout) itemView.findViewById(R.id.lin_top);
            relBg = (LinearLayout) itemView.findViewById(R.id.rel_bg);
            lin_home_sign = itemView.findViewById(R.id.lin_home_sign);
            lin_home_scanning = itemView.findViewById(R.id.lin_home_scanning);
            iv_home_scanning = itemView.findViewById(R.id.iv_home_scanning);
            iv_home_sign = itemView.findViewById(R.id.iv_home_sign);
        }
    }

    /**
     * 点击事件
     */
    public interface HomeFunctionTitleInterface {
        void setHomeFunctionTitleOnClickListener(int type);
    }

}
