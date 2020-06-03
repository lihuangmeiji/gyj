package com.system.technologyinformation.module.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.adapter.CreditsExchangeAdapter;
import com.system.technologyinformation.module.contract.CreditsExchangeContract;
import com.system.technologyinformation.module.presenter.CreditsExchangePresenter;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.chat.QuestionMessageActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.CornerTransform;
import com.system.technologyinformation.widget.RecycleViewDivider;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreditsExchangeActivity extends BaseActivity<CreditsExchangePresenter> implements CreditsExchangeContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    CreditsExchangeAdapter creditsExchangeAdapter;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.swipeLayout_ce)
    SwipeRefreshLayout swipeLayout;
    int currentPage = 1;
    int pageNumber = 10;

    @Override
    protected int getContentView() {
        return R.layout.activity_credits_exchange;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("兑换商城");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.color49));
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        creditsExchangeAdapter = new CreditsExchangeAdapter(R.layout.item_credits_exchange);
        creditsExchangeAdapter.setOnLoadMoreListener(this, recyclerView);
        creditsExchangeAdapter.setEnableLoadMore(true);
        creditsExchangeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(creditsExchangeAdapter);
        creditsExchangeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                intent.putExtra("shoppingId", creditsExchangeAdapter.getItem(i).getId());
                startActivity(intent);
            }
        });
        refresh();
        if (login != null) {
            tvPoint.setText(login.getPoint() + "");
        } else {
            tvPoint.setText("0");
        }

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            // scheme部分
            String scheme = uri.getScheme();
            // host部分
            String host = uri.getHost();
            //port部分
            int port = uri.getPort();
            // 访问路径
            String path = uri.getPath();
        }
    }

    @OnClick({R.id.toolbar
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
        }
    }

    @Override
    public void setShoppingDataResult(List<HomeShoppingSpreeBean> homeShoppingDataResult) {
        swipeLayout.setRefreshing(false);
        creditsExchangeAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (homeShoppingDataResult == null || homeShoppingDataResult.size() == 0) {
            creditsExchangeAdapter.loadMoreEnd();
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        creditsExchangeAdapter.addData(homeShoppingDataResult);
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
        creditsExchangeAdapter.loadMoreComplete();
        currentPage++;
        if (homeShoppingDataResult.size() < pageNumber) {
            creditsExchangeAdapter.loadMoreEnd();
            return;
        }

    }

    @Override
    public void setHomeBannerResult(List<HomeBannerBean> homeBannerBeanList) {
        List<String> stringList = new ArrayList<>();
        if (homeBannerBeanList != null && homeBannerBeanList.size() > 0) {
            for (int i = 0; i < homeBannerBeanList.size(); i++) {
                stringList.add(homeBannerBeanList.get(i).getImg());
            }
        }
        loadTestDatas(stringList, homeBannerBeanList);
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            getPresenter().getShoppingDataResult(1, 30);
            getPresenter().getHomeBannerResult();
        } else {
            openLogin();
        }
    }


    private void loadTestDatas(List<String> stringList, final List<HomeBannerBean> homeBannerBeanList) {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int index) {
                if (homeBannerBeanList != null && homeBannerBeanList.size() > 0 && homeBannerBeanList.get(index) != null) {
                    if (homeBannerBeanList.get(index).isNeedLogin() == false) {
                        if (EmptyUtils.isEmpty(homeBannerBeanList.get(index).getTarget())) {
                            return;
                        }
                        if (homeBannerBeanList.get(index).getTarget().contains("gyj://")) {
                            if (homeBannerBeanList.get(index).getTarget().contains("main")) {
                                PackageManager manager = getBaseContext().getPackageManager();
                                String scheme;
                                if (homeBannerBeanList.get(index).getTarget().contains("?id=")) {
                                    scheme = homeBannerBeanList.get(index).getTarget() + "&lytype=bd";
                                } else {
                                    scheme = homeBannerBeanList.get(index).getTarget() + "?lytype=bd";
                                }
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(scheme));
                                List list = manager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
                                if (list != null && list.size() > 0) {
                                    startActivity(intent);
                                }
                            } else {
                                if (homeBannerBeanList.get(index).getTarget().contains("home/exchange")) {
                                    if (homeBannerBeanList.get(index).getTarget().contains("home/exchange?id")) {
                                        Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                        intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/exchange?id=", "")).intValue());
                                        startActivity(intent);
                                    } else {
                                        startActivity(new Intent(getBaseContext(), CreditsExchangeActivity.class));
                                    }
                                } else if (homeBannerBeanList.get(index).getTarget().contains("home/mall")) {
                                    if (homeBannerBeanList.get(index).getTarget().contains("home/mall?id")) {
                                        Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                        intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/mall?id=", "")).intValue());
                                        startActivity(intent);
                                    } else {
                                        startActivity(new Intent(getBaseContext(), PanicBuyingActivity.class));
                                    }
                                } else if (homeBannerBeanList.get(index).getTarget().contains("food")) {
                                    startActivity(new Intent(getBaseContext(), TableReservationActivity.class));
                                } else if (homeBannerBeanList.get(index).getTarget().contains("news")) {
                                    startActivity(new Intent(getBaseContext(), InformationConsultingActivity.class));
                                } else if (homeBannerBeanList.get(index).getTarget().contains("feedback/submit")) {
                                    startActivity(new Intent(getBaseContext(), QuestionMessageActivity.class));
                                }
                            }
                        } else {
                            Intent intentWeb = new Intent(getBaseContext(), QuestionDetailesActivity.class);
                            intentWeb.putExtra("title", homeBannerBeanList.get(index).getName());
                            intentWeb.putExtra("url", homeBannerBeanList.get(index).getTarget());
                            startActivity(intentWeb);
                        }
                    } else {
                        if (login != null) {
                            if (EmptyUtils.isEmpty(homeBannerBeanList.get(index).getTarget())) {
                                return;
                            }
                            if (homeBannerBeanList.get(index).getTarget().contains("gyj://")) {
                                if (homeBannerBeanList.get(index).getTarget().contains("main")) {
                                    PackageManager manager = getBaseContext().getPackageManager();
                                    String scheme;
                                    if (homeBannerBeanList.get(index).getTarget().contains("?id=")) {
                                        scheme = homeBannerBeanList.get(index).getTarget() + "&lytype=bd";
                                    } else {
                                        scheme = homeBannerBeanList.get(index).getTarget() + "?lytype=bd";
                                    }
                                    Intent intent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(scheme));
                                    List list = manager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
                                    if (list != null && list.size() > 0) {
                                        startActivity(intent);
                                    }
                                } else {
                                    if (homeBannerBeanList.get(index).getTarget().contains("home/exchange")) {
                                        if (homeBannerBeanList.get(index).getTarget().contains("home/exchange?id")) {
                                            Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                            intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/exchange?id=", "")).intValue());
                                            startActivity(intent);
                                        } else {
                                            startActivity(new Intent(getBaseContext(), CreditsExchangeActivity.class));
                                        }
                                    } else if (homeBannerBeanList.get(index).getTarget().contains("home/mall")) {
                                        if (homeBannerBeanList.get(index).getTarget().contains("home/mall?id")) {
                                            Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                            intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/mall?id=", "")).intValue());
                                            startActivity(intent);
                                        } else {
                                            startActivity(new Intent(getBaseContext(), PanicBuyingActivity.class));
                                        }
                                    } else if (homeBannerBeanList.get(index).getTarget().contains("food")) {
                                        startActivity(new Intent(getBaseContext(), TableReservationActivity.class));
                                    } else if (homeBannerBeanList.get(index).getTarget().contains("news")) {
                                        startActivity(new Intent(getBaseContext(), InformationConsultingActivity.class));
                                    } else if (homeBannerBeanList.get(index).getTarget().contains("feedback/submit")) {
                                        startActivity(new Intent(getBaseContext(), QuestionMessageActivity.class));
                                    }
                                }
                            } else {
                                Intent intentWeb = new Intent(getBaseContext(), QuestionDetailesActivity.class);
                                intentWeb.putExtra("title", homeBannerBeanList.get(index).getName());
                                intentWeb.putExtra("url", homeBannerBeanList.get(index).getTarget() + "?userId=" + login.getId());
                                startActivity(intentWeb);
                         /*       Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(homeBannerBeanList.get(index).getTarget() + "?userId=" + login.getId() + "&title=" + homeBannerBeanList.get(index).getName()));
                                startActivity(intent);*/
                            }
                        } else {
                            openLogin();
                        }
                    }
                }
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
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置图片集合
        banner.setImages(stringList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void refresh() {
        swipeLayout.setRefreshing(true);
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 100);
    }


    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                creditsExchangeAdapter.getData().clear();
                creditsExchangeAdapter.notifyDataSetChanged();
                getPresenter().getShoppingDataResult(currentPage, pageNumber);
                getPresenter().getHomeBannerResult();
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        if (swipeLayout != null) {
            swipeLayout.setEnabled(false);
        }
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().getShoppingDataResult(currentPage, pageNumber);
            }
        }, 100);
    }


    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == 405) {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getShoppingDataResult(1, 30);
                }
            });
        } else if (code == -10) {
            return;
        } else {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
        toast(msg);
    }


    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hiddenLoading() {
        super.hiddenLoading();
    }

    @Override
    public void toast(String msg) {
        super.toast(msg);
    }
}
