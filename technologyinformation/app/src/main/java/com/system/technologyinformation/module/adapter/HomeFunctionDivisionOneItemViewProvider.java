package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.ConvenientFunctionsBean;
import com.system.technologyinformation.model.HomeFunctionDivisionOne;
import com.system.technologyinformation.module.holder.AbstractHolder;
import com.system.technologyinformation.module.holder.PageMenuViewHolderCreator;
import com.system.technologyinformation.utils.ScreenUtil;
import com.system.technologyinformation.widget.IndicatorView;
import com.system.technologyinformation.widget.LinearLayout_status_bar;
import com.system.technologyinformation.widget.PageMenuLayout;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class HomeFunctionDivisionOneItemViewProvider extends ItemViewBinder<HomeFunctionDivisionOne, HomeFunctionDivisionOneItemViewProvider.ViewHolder> {

    private HomeFunctionDivisionOneInterface homeFunctionDivisionOneInterface;

    public HomeFunctionDivisionOneInterface getHomeFunctionDivisionOneInterface() {
        return homeFunctionDivisionOneInterface;
    }

    public void setHomeFunctionDivisionOneInterface(HomeFunctionDivisionOneInterface homeFunctionDivisionOneInterface) {
        this.homeFunctionDivisionOneInterface = homeFunctionDivisionOneInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_home_function_division_one, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, @NonNull HomeFunctionDivisionOne homeFunctionDivisionOne) {
        if (homeFunctionDivisionOne.getHomeConfigurationInformationBean() != null) {
            if (!homeFunctionDivisionOne.getHomeConfigurationInformationBean().getName().equals("默认")) {
                Glide.with(viewHolder.relBg.getContext())
                        .load(homeFunctionDivisionOne.getHomeConfigurationInformationBean().getHomeImg())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                Drawable drawable = new BitmapDrawable(bitmap);
                                viewHolder.relBg.setBackground(drawable);
                            }
                        });
                Glide.with(viewHolder.iv_home_scanning.getContext())
                        .load(homeFunctionDivisionOne.getHomeConfigurationInformationBean().getScanImg())
                        .asBitmap()
                        .into(viewHolder.iv_home_scanning);
                Glide.with(viewHolder.iv_home_sign.getContext())
                        .load(homeFunctionDivisionOne.getHomeConfigurationInformationBean().getCheckInImg())
                        .asBitmap()
                        .into(viewHolder.iv_home_sign);
                viewHolder.tvHomeScanning.setTextColor(Color.parseColor(homeFunctionDivisionOne.getHomeConfigurationInformationBean().getTextColor()));
                viewHolder.tvHomeSign.setTextColor(Color.parseColor(homeFunctionDivisionOne.getHomeConfigurationInformationBean().getTextColor()));
            }
            viewHolder.lin_home_scanning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeFunctionDivisionOneInterface.setHomeFunctionTitleOnClickListener1(1);
                }
            });

            viewHolder.lin_home_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeFunctionDivisionOneInterface.setHomeFunctionTitleOnClickListener1(2);
                }
            });
        }
        viewHolder.setPosts(homeFunctionDivisionOne.getConvenientFunctionsBeanList(), getHomeFunctionDivisionOneInterface());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private PageMenuLayout mPageMenuLayout;
        private IndicatorView entranceIndicatorView;
        private HomeFunctionDivisionOneInterface homeFunctionDivisionOneInterface;
        private LinearLayout lin_home_sign;
        private LinearLayout lin_home_scanning;
        private ImageView iv_home_scanning;
        private ImageView iv_home_sign;
        private LinearLayout_status_bar relBg;
        private TextView tvHomeScanning;
        private TextView tvHomeSign;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPageMenuLayout = (PageMenuLayout) itemView.findViewById(R.id.pagemenu);
            entranceIndicatorView = (IndicatorView) itemView.findViewById(R.id.main_home_entrance_indicator);
            tvHomeScanning = (TextView) itemView.findViewById(R.id.tv_home_scanning);
            tvHomeSign = (TextView) itemView.findViewById(R.id.tv_home_sign);
            relBg = (LinearLayout_status_bar) itemView.findViewById(R.id.rel_bg);
            lin_home_sign = itemView.findViewById(R.id.lin_home_sign);
            lin_home_scanning = itemView.findViewById(R.id.lin_home_scanning);
            iv_home_scanning = itemView.findViewById(R.id.iv_home_scanning);
            iv_home_sign = itemView.findViewById(R.id.iv_home_sign);
        }

        private void setPosts(List<ConvenientFunctionsBean> convenientFunctionsBeanList, HomeFunctionDivisionOneInterface homeFunctionDivisionOneInterface) {
            this.homeFunctionDivisionOneInterface = homeFunctionDivisionOneInterface;
            init(convenientFunctionsBeanList);
        }

        private void init(List<ConvenientFunctionsBean> convenientFunctionsBeanList) {
            entranceIndicatorView.setCurrentIndicator(0);
            mPageMenuLayout.setPageDatas(convenientFunctionsBeanList, new PageMenuViewHolderCreator() {
                @Override
                public AbstractHolder createHolder(View itemView) {
                    return new AbstractHolder<ConvenientFunctionsBean>(itemView) {
                        private TextView entranceNameTextView;
                        private ImageView entranceIconImageView;
                        private ImageView ivFunctionBs;

                        @Override
                        protected void initView(View itemView) {
                            entranceIconImageView = itemView.findViewById(R.id.iv_function_ico);
                            entranceNameTextView = itemView.findViewById(R.id.tv_function_title);
                            ivFunctionBs = itemView.findViewById(R.id.iv_function_bs);
                 /*           LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ((float) ScreenUtil.getScreenWidth() / 4.0f) / 6 * 5);
                            itemView.setLayoutParams(layoutParams);*/
                        }

                        @Override
                        public void bindView(RecyclerView.ViewHolder holder, final ConvenientFunctionsBean data, final int pos) {
                            entranceNameTextView.setText(data.getName());
                            Glide.with(holder.itemView.getContext()).load(data.getIcon()).into(entranceIconImageView);
                            if (EmptyUtils.isEmpty(data.getHot())) {
                                ivFunctionBs.setVisibility(View.GONE);
                            } else {
                                ivFunctionBs.setVisibility(View.VISIBLE);
                                Glide.with(holder.itemView.getContext()).load(data.getHot()).into(ivFunctionBs);
                            }
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    homeFunctionDivisionOneInterface.setHomeServiceOnClickListener(pos, data);
                                }
                            });
                        }
                    };
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_home_function_division;
                }
            });
            entranceIndicatorView.setIndicatorCount(mPageMenuLayout.getPageCount());
            mPageMenuLayout.setOnPageListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    entranceIndicatorView.setCurrentIndicator(position);
                }
            });
        }
    }

    /**
     * 点击事件
     */
    public interface HomeFunctionDivisionOneInterface {
        void setHomeServiceOnClickListener(int index, ConvenientFunctionsBean data);
        void setHomeFunctionTitleOnClickListener1(int type);
    }

}
