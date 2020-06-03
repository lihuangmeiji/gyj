package com.system.technologyinformation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.TabBean;

import java.util.ArrayList;

public class CustomizeTabLayout extends FrameLayout {
    private LinearLayout mTabLinearLayout;
    private Context mContext;
    private ArrayList<TabBean> mTabBeans=new ArrayList<>();
    private int mTabCount;
    private int mCurrentTab;
    private OnTabSelectListener mListener;
    private int mLastTab;

    public void setmListener(OnTabSelectListener mListener) {
        this.mListener = mListener;
    }

    public CustomizeTabLayout(Context context) {
        this(context,null,0);
    }

    public CustomizeTabLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomizeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        mTabLinearLayout=new LinearLayout(context);
        addView(mTabLinearLayout);
    }

    //添加导航栏数据
    public void setTabDate(ArrayList<TabBean> tabBeans){
        if (tabBeans == null || tabBeans.size() == 0) {
            throw new IllegalStateException("TabEntitys can not be NULL or EMPTY !");
        }
        this.mTabBeans.clear();
        this.mTabBeans.addAll(tabBeans);
        notifyDataSetChanged();
    }

    //更新数据
    private void notifyDataSetChanged() {
        mTabLinearLayout.removeAllViews();
        this.mTabCount=mTabBeans.size();
        View tabView;
        for(int i=0;i<mTabCount;i++){
            tabView = View.inflate(mContext, R.layout.layout_coutomizetab_top, null);
            tabView.setTag(i);
            addTab(i,tabView);
        }
        setCurrentTab(0);
    }
    /** 创建并添加tab */
    private void addTab(final int position, View tabView) {
        TextView tv_tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(mTabBeans.get(position).getTitle());
        ImageView iv_tab_icon = (ImageView) tabView.findViewById(R.id.iv_tab_icon);
        int unSelectIcon = mTabBeans.get(position).getSelectIcons();
        String unSelectUrl = mTabBeans.get(position).getUnSelectUrl();
        String SelectUrl = mTabBeans.get(position).getSelectUrl();
        if(android.text.TextUtils.isEmpty(unSelectUrl)||android.text.TextUtils.isEmpty(SelectUrl)){
            iv_tab_icon.setImageResource(unSelectIcon);
        }else{
            Glide.with(mContext).load(unSelectUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(unSelectIcon).into(iv_tab_icon);
        }
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {
                    setCurrentTab(position);
                    if (mListener != null) {
                        mListener.onTabSelect(position);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onTabReselect(position);
                    }
                }
            }
        });
        LinearLayout.LayoutParams lp_tab =  new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        mTabLinearLayout.addView(tabView, position, lp_tab);
    }

    public void setCurrentTab(int currentTab) {
        mLastTab = this.mCurrentTab;
        this.mCurrentTab = currentTab;
        updateTabSelection(currentTab);
    }

    //更新tab
    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabLinearLayout.getChildAt(i);
            final boolean isSelect = i == position;
            TabBean tabBean = mTabBeans.get(i);
            TextView tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
     /*       if(isSelect&&position==0){
                tab_title.setVisibility(GONE);
            }else{
                tab_title.setVisibility(VISIBLE);
            }*/
            tab_title.setTextColor(isSelect ? tabBean.getmSelectColor() :tabBean.getmUnSelectColor());
            ImageView iv_tab_icon = (ImageView) tabView.findViewById(R.id.iv_tab_icon);
            if(android.text.TextUtils.isEmpty(tabBean.getSelectUrl())||android.text.TextUtils.isEmpty(tabBean.getUnSelectUrl())){
                iv_tab_icon.setImageResource(isSelect ? tabBean.getSelectIcons() : tabBean.getUnSelectIcon());
            }else{
                Glide.with(mContext).load(isSelect?tabBean.getSelectUrl():tabBean.getUnSelectUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(isSelect?tabBean.getSelectIcons():tabBean.getUnSelectIcon()).into(iv_tab_icon);
            }
        }
    }


    public interface OnTabSelectListener {
        void onTabSelect(int position);
        void onTabReselect(int position);
    }

}
