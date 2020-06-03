package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeFunctionDivisionThree;
import com.system.technologyinformation.utils.CornerTransform;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class HomeBannerlItemViewProvider extends ItemViewBinder<HomeFunctionDivisionThree, HomeBannerlItemViewProvider.ViewHolder> {

    private HomeBannerInterface homeBannerInterface;

    public HomeBannerInterface getHomeBannerInterface() {
        return homeBannerInterface;
    }

    public void setHomeBannerInterface(HomeBannerInterface homeBannerInterface) {
        this.homeBannerInterface = homeBannerInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_banner, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull HomeFunctionDivisionThree homeFunctionDivisionThree) {
        viewHolder.setPosts(homeFunctionDivisionThree.getStringList(),getHomeBannerInterface());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Banner banner;

        private HomeBannerInterface homeBannerInterface1;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        private void setPosts(List<String> stringList,HomeBannerInterface homeBannerInterface) {
            homeBannerInterface1=homeBannerInterface;
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    homeBannerInterface1.setOnBannerListener(position);
                }
            });
            //设置轮播时间
            banner.setDelayTime(2000);
            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    CornerTransform transformation = new CornerTransform(context, dip2px(context, 10));
                    transformation.setExceptCorner(false, false, false, false);
                    Glide.with(context)
                            .load(path)
                            .asBitmap()
                            .skipMemoryCache(true)
                            .error(R.mipmap.homebannermr)
                            .fallback(R.mipmap.homebannermr)
                            .transform(transformation)
                            .into(imageView);
                }
            });
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.ScaleInOut);
            //设置图片集合
            banner.setImages(stringList);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
    }

    /**
     * 点击事件
     */
    public interface HomeBannerInterface {
        void setOnBannerListener(int index);
    }
}
