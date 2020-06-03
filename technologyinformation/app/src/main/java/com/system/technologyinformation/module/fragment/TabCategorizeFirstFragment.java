package com.system.technologyinformation.module.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.lei.lib.java.rxcache.RxCache;
import com.lei.lib.java.rxcache.entity.CacheResponse;
import com.lei.lib.java.rxcache.util.RxUtil;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseFragment;
import com.system.technologyinformation.common.ui.BaseLazyFragment;
import com.system.technologyinformation.model.ConvenientFunctionsBean;
import com.system.technologyinformation.model.FlickerScreenBean;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeConfigurationInformationBean;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.HomeFunctionDivisionFour;
import com.system.technologyinformation.model.HomeFunctionDivisionOne;
import com.system.technologyinformation.model.HomeFunctionDivisionThree;
import com.system.technologyinformation.model.HomeFunctionDivisionTwo;
import com.system.technologyinformation.model.HomeMessage;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;

import com.system.technologyinformation.model.PayEntranceBean;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.UserServiceFunctionListBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.HomeBannerlItemViewProvider;
import com.system.technologyinformation.module.adapter.HomeFunctionBootomItemViewProvider;
import com.system.technologyinformation.module.adapter.HomeFunctionDivisionFourItemViewProvider;
import com.system.technologyinformation.module.adapter.HomeFunctionDivisionOneItemViewProvider;
import com.system.technologyinformation.module.adapter.HomeFunctionDivisionTwoItemViewProvider;
import com.system.technologyinformation.module.adapter.HomeFunctionTitleItemViewProvider;
import com.system.technologyinformation.module.contract.TabCategorizeFirstContract;
import com.system.technologyinformation.module.presenter.TabCategorizeFirstPresenter;

import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.chat.QuestionMessageActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.home.CourierStationActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeDetailedActivity;
import com.system.technologyinformation.module.ui.home.InformationConsultingActivity;
import com.system.technologyinformation.module.ui.home.PanicBuyingActivity;
import com.system.technologyinformation.module.ui.home.RechargersTelephoneChargesActivity;
import com.system.technologyinformation.module.ui.scanner.ScannerGyjActivity;
import com.system.technologyinformation.module.ui.users.UserMessageActivity;
import com.system.technologyinformation.module.ui.users.UserPayActivity;
import com.system.technologyinformation.utils.AnimationEffect;
import com.system.technologyinformation.utils.CommonUtil;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.utils.ScreenUtil;
import com.system.technologyinformation.utils.ScreenUtils;
import com.system.technologyinformation.widget.AnimationRecyclerView;
import com.system.technologyinformation.widget.AnimationScrollView;
import com.system.technologyinformation.widget.RecycleViewDivider;
import com.system.technologyinformation.widget.Relativelayout_status_bar;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TabCategorizeFirstFragment extends BaseLazyFragment<TabCategorizeFirstPresenter> implements TabCategorizeFirstContract.View, HomeBannerlItemViewProvider.HomeBannerInterface, HomeFunctionTitleItemViewProvider.HomeFunctionTitleInterface, HomeFunctionDivisionOneItemViewProvider.HomeFunctionDivisionOneInterface, HomeFunctionDivisionTwoItemViewProvider.HomeFunctionDivisionTwoInterface {


    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerViewHome;
    @BindView(R.id.swipeLayout)
    PullToRefreshLayout swipeLayout;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    @BindView(R.id.iv_home_scanning_sx)
    ImageView ivHomeScanningSx;
    @BindView(R.id.iv_home_sign_sx)
    ImageView ivHomeSignSx;
    @BindView(R.id.rel_title)
    Relativelayout_status_bar relTitle;
    @BindView(R.id.iv_title_bg)
    ImageView ivTitleBg;
    @BindView(R.id.v_status)
    View v_status;
    @BindView(R.id.iv_home_ico)
    ImageView iv_home_ico;
    private List<ConvenientFunctionsBean> convenientFunctionsBeanList;
    private List<String> homeMessageList;
    private List<String> homeBannerList;
    private List<HomeShoppingSpreeBean> homeShoppingSpreeBeanList;
    List<HomeBannerBean> homeBannerBeanList;
    private Items items;
    private MultiTypeAdapter adapter;
    LinearLayoutManager layoutManager;
    boolean isscr = true;
    int currentPage = 0;
    int pageNumber = 30;
    HomeConfigurationInformationBean homeConfigurationInformationBean;
    private final int REQUEST_PERMISION_CODE_CAMARE = 10;
    private final int RESULT_REQUEST_CODE = 11;

    int zcsx = 0;

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        addUpdateInfoReceiver();
        homeBannerBeanList = new ArrayList<>();

        convenientFunctionsBeanList = new ArrayList<>();

        homeMessageList = new ArrayList<>();

        homeBannerList = new ArrayList<>();

        homeShoppingSpreeBeanList = new ArrayList<>();

        items = new Items();
        adapter = new MultiTypeAdapter(items);
        HomeFunctionTitleItemViewProvider homeFunctionTitleItemViewProvider = new HomeFunctionTitleItemViewProvider();
        homeFunctionTitleItemViewProvider.setHomeFunctionTitleInterface(this);
        adapter.register(HomeConfigurationInformationBean.class, homeFunctionTitleItemViewProvider);

        HomeFunctionDivisionOneItemViewProvider homeFunctionDivisionOneItemViewProvider = new HomeFunctionDivisionOneItemViewProvider();
        homeFunctionDivisionOneItemViewProvider.setHomeFunctionDivisionOneInterface(this);
        adapter.register(HomeFunctionDivisionOne.class, homeFunctionDivisionOneItemViewProvider);

        HomeFunctionDivisionTwoItemViewProvider homeFunctionDivisionTwoItemViewProvider = new HomeFunctionDivisionTwoItemViewProvider();
        homeFunctionDivisionTwoItemViewProvider.setHomeFunctionDivisionTwoInterface(this);
        adapter.register(HomeFunctionDivisionTwo.class, homeFunctionDivisionTwoItemViewProvider);

        HomeBannerlItemViewProvider homeBannerlItemViewProvider = new HomeBannerlItemViewProvider();
        homeBannerlItemViewProvider.setHomeBannerInterface(this);
        adapter.register(HomeFunctionDivisionThree.class, homeBannerlItemViewProvider);
        adapter.register(HomeFunctionDivisionFour.class, new HomeFunctionDivisionFourItemViewProvider());

        adapter.register(String.class, new HomeFunctionBootomItemViewProvider());

   /*     homeConfigurationInformationBean = new HomeConfigurationInformationBean();
        homeConfigurationInformationBean.setName("默认");
        homeConfigurationInformationBean.setArchive(0);
        items.add(homeConfigurationInformationBean);*/

        homeConfigurationInformationBean = new HomeConfigurationInformationBean();
        homeConfigurationInformationBean.setName("默认");
        homeConfigurationInformationBean.setArchive(0);

        HomeFunctionDivisionOne homeFunctionDivisionOne = new HomeFunctionDivisionOne();
        homeFunctionDivisionOne.setConvenientFunctionsBeanList(convenientFunctionsBeanList);
        homeFunctionDivisionOne.setHomeConfigurationInformationBean(homeConfigurationInformationBean);
        items.add(homeFunctionDivisionOne);

        HomeFunctionDivisionTwo homeFunctionDivisionTwo = new HomeFunctionDivisionTwo();
        homeFunctionDivisionTwo.setHomeMessageList(homeMessageList);
        items.add(homeFunctionDivisionTwo);

        HomeFunctionDivisionThree homeFunctionDivisionThree = new HomeFunctionDivisionThree();
        homeFunctionDivisionThree.setStringList(homeBannerList);
        items.add(homeFunctionDivisionThree);

        HomeFunctionDivisionFour homeFunctionDivisionFour = new HomeFunctionDivisionFour();
        homeFunctionDivisionFour.setHomeShoppingSpreeBeanList(homeShoppingSpreeBeanList);
        items.add(homeFunctionDivisionFour);

        items.add("bottom");
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.color39)));
        recyclerViewHome.setAdapter(adapter);


        recyclerViewHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                selectItem(dy);
            }
        });

        getHomeConfigurationInformationDataCache();
        getPresenter().getFunctionDivisionOne();
        getPresenter().getFunctionDivisionTwo();
        getPresenter().getHomeBannerResult();
        getPresenter().getHomeShoppingDataResult();
        //getPresenter().getUpdateUserInfoResult(0);
        getFlickerScreenDataCache();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v_status.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            lp.height = BarUtils.getStatusBarHeight(getContext());
            v_status.setLayoutParams(lp);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_tab_categorize_first;
    }


    @Override
    protected void loadLazyData() {
        loadUserInfo();
        getPresenter().getFunctionDivisionTwo();
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
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        hiddenLoading();
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == 1) {
            if (msg.contains("今日已签到,无需重复操作")) {
                new SignSuccessView(getContext(), recyclerViewHome, 2);
                return;
            }
        } else if (code == -10) {
            return;
        }

        toast(msg);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
        }

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case RESULT_REQUEST_CODE:
                    if (data == null) return;
                    String content = data.getStringExtra("result_content");
                    Gson gson = new Gson();
                    String strC;
                    try {
                        Type type = new TypeToken<PayEntranceBean>() {
                        }.getType();
                        PayEntranceBean payEntranceBean = new Gson().fromJson(content, type);
                        strC = gson.toJson(payEntranceBean);
                    } catch (Exception e) {
                        strC = content;
                    }
                    if (EmptyUtils.isEmpty(strC)) {
                        toast("解析失败!");
                        return;
                    }
                    showLoading();
                    getPresenter().getShopInfo(strC);
                    break;
                default:
                    break;
            }
        }
    }


    BroadcastReceiver broadcastReceiver;

    private void addUpdateInfoReceiver() {
        broadcastReceiver = new BroadcastReceiver()

        {
            @Override
            public void onReceive(Context context, Intent intent) {
                convenientFunctionsBeanList.clear();
                getPresenter().getFunctionDivisionOne();
                getPresenter().getFunctionDivisionTwo();
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("userupdate");
        getContext().registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            getContext().unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void setHomeBannerResult(List<HomeBannerBean> homeBannerBeanList) {
        this.homeBannerBeanList = homeBannerBeanList;
        if (homeBannerBeanList != null && homeBannerBeanList.size() > 0) {
            for (int i = 0; i < homeBannerBeanList.size(); i++) {
                homeBannerList.add(homeBannerBeanList.get(i).getImg());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setHomeShoppingDataResult(List<HomeShoppingSpreeBean> homeShoppingDataResult) {
        homeShoppingSpreeBeanList.addAll(homeShoppingDataResult);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUpdateUserInfoResult(BaseResponseInfo baseResponseInfo) {

    }

    @Override
    public void setFunctionDivisionOne(List<ConvenientFunctionsBean> convenientFunctionsBeanList) {
        this.convenientFunctionsBeanList.addAll(convenientFunctionsBeanList);
        adapter.notifyDataSetChanged();
    }

    //["恭喜您签到成功，获得积分奖励","积分增加后无法正常打开APP 先减到","国庆节 点赞赚积分"]
//["恭喜您签到成功，获得积分奖励","恭喜您签到成功，获得积分奖励","恭喜您签到成功，获得积分奖励"]
    @Override
    public void setFunctionDivisionTwo(BaseResponseInfo baseResponseInfo) {
        homeMessageList.clear();
        if (baseResponseInfo.getCode() == 0) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            //baseResponseInfo.getData().toString()
            List<String> stringList = new Gson().fromJson(baseResponseInfo.getData().toString(), type);
            if (stringList.size() != 0) {
                if (stringList.size() == 1) {
                    homeMessageList.add("茫茫人海，感谢相遇");
                    homeMessageList.add("欢迎您加入工友家");
                } else if (stringList.size() == 2) {
                    homeMessageList.add("茫茫人海，感谢相遇");
                }
                homeMessageList.addAll(stringList);
            } else {
                homeMessageList.add("茫茫人海，感谢相遇");
                homeMessageList.add("欢迎您加入工友家");
                homeMessageList.add("快来注册吧，更多福利等着您哦！");
            }
        } else {
            homeMessageList.add("茫茫人海，感谢相遇");
            homeMessageList.add("欢迎您加入工友家");
            homeMessageList.add("快来注册吧，更多福利等着您哦！");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            getPresenter().getFunctionDivisionTwo();
        } else {
            openLogin();
        }
    }

    @Override
    public void setUserSignAddResult(RewardBean rewardBean) {
        hiddenLoading();
        if (rewardBean != null) {
            if (rewardBean.isCheckIn()) {
                new SignSuccessView(getContext(), recyclerViewHome, 1);
            } else {
                toast("签到失败");
            }
        } else {
            toast("签到失败");
        }
    }

    @Override
    public void setShopInfoResult(StoreInfoBean storeInfoBean) {
        hiddenLoading();
        if (!EmptyUtils.isEmpty(storeInfoBean)) {
            Log.i("storeInfoBean", "setShopInfoResult: " + storeInfoBean.toString());
            if (!EmptyUtils.isEmpty(storeInfoBean.getId()) && !EmptyUtils.isEmpty(storeInfoBean.getName())
                    && !EmptyUtils.isEmpty(storeInfoBean.getUserPoint()) && !EmptyUtils.isEmpty(storeInfoBean.getPointConvert())
                    && !EmptyUtils.isEmpty(storeInfoBean.getPointPercentage()) && (storeInfoBean.isWxPay() || storeInfoBean.isAliPay())) {
                Intent intent = new Intent(getContext(), UserPayActivity.class);
                intent.putExtra("storeInfoBean", storeInfoBean);
                startActivity(intent);
            } else if (!EmptyUtils.isEmpty(storeInfoBean.getUrl())) {
                Intent intentWeb = new Intent(getContext(), QuestionDetailesActivity.class);
                intentWeb.putExtra("url", storeInfoBean.getUrl());
                startActivity(intentWeb);
            }
        } else {
            toast("商户信息获取失败");
        }
    }

    @Override
    public void setHomeConfigurationInformation(List<HomeConfigurationInformationBean> homeConfigurationInformationBeanList) {
        if (homeConfigurationInformationBeanList != null && homeConfigurationInformationBeanList.size() > 0) {
            addHomeConfigurationInformationDataCache(homeConfigurationInformationBeanList);
            getHomeConfigurationInformationDataCache();
        }
    }

    @Override
    public void setFlickerScreenResult(List<FlickerScreenBean> flickerScreenBeans) {
        if (flickerScreenBeans != null && flickerScreenBeans.size() > 0) {
            addFlickerScreenDataCache(flickerScreenBeans);
        }
    }


    /**
     * 获取滑动的总距离
     *
     * @return
     */
    public int getScollYDistance() {
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        if (firstVisiableChildView != null) {
            int itemHeight = firstVisiableChildView.getHeight();
            return (position) * itemHeight - firstVisiableChildView.getTop();
        }
        return 0;
    }

    /**
     * 设置滑动距离时的效果
     */
    public void selectItem(int dy) {
        if (getScollYDistance() <= 0) {
            //静止并处于最顶端状态
            ivHomeScanningSx.setVisibility(View.GONE);
            ivHomeSignSx.setVisibility(View.GONE);
            ivHomeScanningSx.setAlpha(0.0f);//设置透明度数值from 0 to
            ivHomeSignSx.setAlpha(0.0f);//设置透明度数值from 0 to
            isscr = true;
        } else if (getScollYDistance() > 0 && getScollYDistance() <= 50) {//滑动在0-400距离的时候
            ivHomeScanningSx.setVisibility(View.VISIBLE);
            ivHomeSignSx.setVisibility(View.VISIBLE);
            ivHomeScanningSx.setAlpha(0.2f);//设置透明度数值from 0 to
            ivHomeSignSx.setAlpha(0.2f);//设置透明度数值from 0 to
        } else if (getScollYDistance() > 50 && getScollYDistance() <= 100) {//滑动在0-400距离的时候
            ivHomeScanningSx.setAlpha(0.4f);//设置透明度数值from 0 to
            ivHomeSignSx.setAlpha(0.4f);//设置透明度数值from 0 to
        } else if (getScollYDistance() > 100 && getScollYDistance() <= 150) {//滑动在0-400距离的时候
            ivHomeScanningSx.setAlpha(0.6f);//设置透明度数值from 0 to
            ivHomeSignSx.setAlpha(0.6f);//设置透明度数值from 0 to
        } else if (getScollYDistance() > 150 && getScollYDistance() <= 200) {//滑动在0-400距离的时候
            ivHomeScanningSx.setAlpha(0.8f);//设置透明度数值from 0 to
            ivHomeSignSx.setAlpha(0.8f);//设置透明度数值from 0 to
        } else {
            ivHomeScanningSx.setAlpha(1.0f);//设置透明度数值from 0 to
            ivHomeSignSx.setAlpha(1.0f);//设置透明度数值from 0 to
            ivHomeScanningSx.setVisibility(View.VISIBLE);
            ivHomeSignSx.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setHomeMessageOnClickListener() {
        if (login != null) {
            startActivity(new Intent(getContext(), UserMessageActivity.class));
        } else {
            openLogin();
        }
    }

    @Override
    public void setHomeServiceOnClickListener(int index, ConvenientFunctionsBean data) {
        if (data.isStatus()) {
            if (data.getNeedLogin() == false) {
                if (EmptyUtils.isEmpty(data.getTarget())) {
                    return;
                }
                if (data.getTarget().contains("gyj://")) {
                    if (data.getTarget().contains("main")) {
                        PackageManager manager = getContext().getPackageManager();
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
                        if (data.getTarget().contains("recharge")) {
                            startActivity(new Intent(getContext(), RechargersTelephoneChargesActivity.class));
                        } else if (data.getTarget().contains("exchange")) {
                            startActivity(new Intent(getContext(), CreditsExchangeActivity.class));
                        } else if (data.getTarget().contains("mall")) {
                            startActivity(new Intent(getContext(), PanicBuyingActivity.class));
                        } else if (data.getTarget().contains("food")) {
                            startActivity(new Intent(getContext(), TableReservationActivity.class));
                        } else if (data.getTarget().contains("news")) {
                            startActivity(new Intent(getContext(), InformationConsultingActivity.class));
                        } else if (data.getTarget().contains("feedback/submit")) {
                            startActivity(new Intent(getContext(), QuestionMessageActivity.class));
                        }
                    }
                } else {
                    Intent intentWeb = new Intent(getContext(), QuestionDetailesActivity.class);
                    intentWeb.putExtra("title", data.getName());
                    intentWeb.putExtra("url", data.getTarget());
                    startActivity(intentWeb);
                }
            } else {
                if (login != null) {
                    if (EmptyUtils.isEmpty(data.getTarget())) {
                        return;
                    }
                    if (data.getTarget().contains("gyj://")) {
                        if (data.getTarget().contains("main")) {
                            PackageManager manager = getContext().getPackageManager();
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
                            if (data.getTarget().contains("recharge")) {
                                startActivity(new Intent(getContext(), RechargersTelephoneChargesActivity.class));
                            } else if (data.getTarget().contains("exchange")) {
                                startActivity(new Intent(getContext(), CreditsExchangeActivity.class));
                            } else if (data.getTarget().contains("mall")) {
                                startActivity(new Intent(getContext(), PanicBuyingActivity.class));
                            } else if (data.getTarget().contains("food")) {
                                startActivity(new Intent(getContext(), TableReservationActivity.class));
                            } else if (data.getTarget().contains("news")) {
                                startActivity(new Intent(getContext(), InformationConsultingActivity.class));
                            } else if (data.getTarget().contains("feedback/submit")) {
                                startActivity(new Intent(getContext(), QuestionMessageActivity.class));
                            }
                        }
                    } else {
                        Intent intentWeb = new Intent(getContext(), QuestionDetailesActivity.class);
                        intentWeb.putExtra("title", data.getName());
                        intentWeb.putExtra("url", data.getTarget());
                        startActivity(intentWeb);
                    }
                } else {
                    openLogin();
                }
            }
        } else {
            toast("即将上线，敬请期待!");
        }
    }

    @Override
    public void setHomeFunctionTitleOnClickListener1(int type) {
        if (type == 1) {
            if (login != null) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
                }
             /*   Intent intent = new Intent(getContext(), UserPayActivity.class);
                intent.putExtra("storeId", 1);
                startActivity(intent);*/
            } else {
                openLogin();
            }
        } else if (type == 2) {
            if (login != null) {
                showLoading();
                getPresenter().getUserSignAddResult();
            } else {
                openLogin();
            }
        }
    }


    @OnClick({
            R.id.iv_home_sign_sx,
            R.id.iv_home_scanning_sx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_sign_sx:
                if (login != null) {
                    showLoading();
                    getPresenter().getUserSignAddResult();
                } else {
                    openLogin();
                }
                break;
            case R.id.iv_home_scanning_sx:
                if (login != null) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        goScanner();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
                    }
                } else {
                    openLogin();
                }
              /*  Intent intent = new Intent(getContext(), UserPayActivity.class);
                intent.putExtra("storeId", 1);
                startActivity(intent);*/
                break;
        }
    }

    private void goScanner() {
        Intent intent = new Intent(getContext(), ScannerGyjActivity.class);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISION_CODE_CAMARE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                }
                return;
            case 2:
                if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, 10012);
                }
                break;
        }
    }

    @Override
    public void setHomeFunctionTitleOnClickListener(int type) {
        if (type == 1) {
            if (login != null) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
                }
             /*   Intent intent = new Intent(getContext(), UserPayActivity.class);
                intent.putExtra("storeId", 1);
                startActivity(intent);*/
            } else {
                openLogin();
            }
        } else if (type == 2) {
            if (login != null) {
                showLoading();
                getPresenter().getUserSignAddResult();
            } else {
                openLogin();
            }
        }
    }

    @Override
    public void setOnBannerListener(int index) {
        if (homeBannerBeanList != null && homeBannerBeanList.size() > 0 && homeBannerBeanList.get(index) != null) {
            if (homeBannerBeanList.get(index).isNeedLogin() == false) {
                if (EmptyUtils.isEmpty(homeBannerBeanList.get(index).getTarget())) {
                    return;
                }
                if (homeBannerBeanList.get(index).getTarget().contains("gyj://")) {
                    if (homeBannerBeanList.get(index).getTarget().contains("main")) {
                        PackageManager manager = getContext().getPackageManager();
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
                                Intent intent = new Intent(getContext(), CreditsExchangeDetailedActivity.class);
                                intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/exchange?id=", "")).intValue());
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(getContext(), CreditsExchangeActivity.class));
                            }
                        } else if (homeBannerBeanList.get(index).getTarget().contains("home/mall")) {
                            if (homeBannerBeanList.get(index).getTarget().contains("home/mall?id")) {
                                Intent intent = new Intent(getContext(), CreditsExchangeDetailedActivity.class);
                                intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/mall?id=", "")).intValue());
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(getContext(), PanicBuyingActivity.class));
                            }
                        } else if (homeBannerBeanList.get(index).getTarget().contains("food")) {
                            startActivity(new Intent(getContext(), TableReservationActivity.class));
                        } else if (homeBannerBeanList.get(index).getTarget().contains("news")) {
                            startActivity(new Intent(getContext(), InformationConsultingActivity.class));
                        } else if (homeBannerBeanList.get(index).getTarget().contains("feedback/submit")) {
                            startActivity(new Intent(getContext(), QuestionMessageActivity.class));
                        }
                    }
                } else {
                    Intent intentWeb = new Intent(getContext(), QuestionDetailesActivity.class);
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
                            PackageManager manager = getContext().getPackageManager();
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
                                    Intent intent = new Intent(getContext(), CreditsExchangeDetailedActivity.class);
                                    intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/exchange?id=", "")).intValue());
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(getContext(), CreditsExchangeActivity.class));
                                }
                            } else if (homeBannerBeanList.get(index).getTarget().contains("home/mall")) {
                                if (homeBannerBeanList.get(index).getTarget().contains("home/mall?id")) {
                                    Intent intent = new Intent(getContext(), CreditsExchangeDetailedActivity.class);
                                    intent.putExtra("shoppingId", Integer.valueOf(homeBannerBeanList.get(index).getTarget().replace("gyj://home/mall?id=", "")).intValue());
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(getContext(), PanicBuyingActivity.class));
                                }
                            } else if (homeBannerBeanList.get(index).getTarget().contains("food")) {
                                startActivity(new Intent(getContext(), TableReservationActivity.class));
                            } else if (homeBannerBeanList.get(index).getTarget().contains("news")) {
                                startActivity(new Intent(getContext(), InformationConsultingActivity.class));
                            } else if (homeBannerBeanList.get(index).getTarget().contains("feedback/submit")) {
                                startActivity(new Intent(getContext(), QuestionMessageActivity.class));
                            }
                        }
                    } else {
                        Intent intentWeb = new Intent(getContext(), QuestionDetailesActivity.class);
                        intentWeb.putExtra("title", homeBannerBeanList.get(index).getName());
                        intentWeb.putExtra("url", homeBannerBeanList.get(index).getTarget() + "?userId=" + login.getId());
                        startActivity(intentWeb);
                    }
                } else {
                    openLogin();
                }
            }
        }
    }

    public class SignSuccessView extends PopupWindow {

        public SignSuccessView(Context mContext, View parent, int type) {

            View view = View.inflate(mContext, R.layout.sign_success_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            if (type == 2) {
                tv_content.setVisibility(View.GONE);
                tv_title.setText("您已经完成签到啦！");
            }
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dismiss();
                }
            });
        }
    }

    HomeConfigurationInformationBean hcShow;

    public void getHomeConfigurationInformationDataCache() {
        Type type = new TypeToken<List<HomeConfigurationInformationBean>>() {
        }.getType();
        RxCache.getInstance()
                .<List<HomeConfigurationInformationBean>>get("pagingInformationBeanList", false, type)
                .compose(RxUtil.<CacheResponse<List<HomeConfigurationInformationBean>>>io_main())
                .subscribe(new Consumer<CacheResponse<List<HomeConfigurationInformationBean>>>() {
                    @Override
                    public void accept(CacheResponse<List<HomeConfigurationInformationBean>> listCacheResponse) throws Exception {
                        if (EmptyUtils.isEmpty(listCacheResponse.getData()) || listCacheResponse.getData().size() == 0) {
                            getPresenter().getHomeConfigurationInformation();
                        } else {
                            for (int i = 0; i < listCacheResponse.getData().size(); i++) {
                                Date d1 = TimeUtils.string2Date(listCacheResponse.getData().get(i).getShowBegin(), "yyyy-MM-dd");
                                Date d2 = TimeUtils.string2Date(listCacheResponse.getData().get(i).getShowEnd(), "yyyy-MM-dd");
                                if ((new Date().compareTo(d1) == 1 || new Date().compareTo(d1) == i) && (new Date().compareTo(d2) == -1 || new Date().compareTo(d1) == 0)) {
                                    if (i == 0) {
                                        hcShow = listCacheResponse.getData().get(i);
                                    } else {
                                        if (!EmptyUtils.isEmpty(hcShow)) {
                                            if (hcShow.getArchive() < listCacheResponse.getData().get(i).getArchive()) {
                                                hcShow = listCacheResponse.getData().get(i);
                                            }
                                        } else {
                                            hcShow = listCacheResponse.getData().get(i);
                                        }
                                    }
                                }
                            }
                            if (!EmptyUtils.isEmpty(hcShow)) {
                                homeConfigurationInformationBean.setName(hcShow.getName());
                                homeConfigurationInformationBean.setHomeImg(hcShow.getHomeImg());
                                homeConfigurationInformationBean.setCheckInImg(hcShow.getCheckInImg());
                                homeConfigurationInformationBean.setTaskImg(hcShow.getTaskImg());
                                homeConfigurationInformationBean.setScanImg(hcShow.getScanImg());
                                homeConfigurationInformationBean.setTextColor(hcShow.getTextColor());
                                homeConfigurationInformationBean.setHomeIcon(hcShow.getHomeIcon());
                                adapter.notifyDataSetChanged();
                                Glide.with(getContext())
                                        .load(homeConfigurationInformationBean.getHomeImg())
                                        .asBitmap()
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                                Drawable drawable = new BitmapDrawable(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight() / 4));
                                                ivTitleBg.setBackground(drawable);
                                            }
                                        });
                                Glide.with(getContext())
                                        .load(homeConfigurationInformationBean.getScanImg())
                                        .asBitmap()
                                        .into(ivHomeScanningSx);
                                Glide.with(getContext())
                                        .load(homeConfigurationInformationBean.getCheckInImg())
                                        .asBitmap()
                                        .into(ivHomeSignSx);
                                Glide.with(getContext())
                                        .load(homeConfigurationInformationBean.getHomeIcon())
                                        .asBitmap()
                                        .into(iv_home_ico);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getPresenter().getHomeConfigurationInformation();
                    }
                });
    }

    public void addHomeConfigurationInformationDataCache(List<HomeConfigurationInformationBean> homeConfigurationInformationBeanList) {
        RxCache.getInstance()
                .put("pagingInformationBeanList", homeConfigurationInformationBeanList, 24 * 60 * 60 * 1000)  //key:缓存的key data:具体的数据 time:缓存的有效时间
                .compose(RxUtil.<Boolean>io_main()) //线程调度
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) Log.d("homedataListCache", "cache successful!");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public void addFlickerScreenDataCache(List<FlickerScreenBean> flickerScreenBeanList) {
        RxCache.getInstance()
                .put("flickerScreenBeanList", flickerScreenBeanList, 24 * 60 * 60 * 1000)  //key:缓存的key data:具体的数据 time:缓存的有效时间
                .compose(RxUtil.<Boolean>io_main()) //线程调度
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) Log.d("flickerScreenBeanList", "cache successful!");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public void getFlickerScreenDataCache() {
        Type type = new TypeToken<List<FlickerScreenBean>>() {
        }.getType();
        RxCache.getInstance()
                .<List<FlickerScreenBean>>get("flickerScreenBeanList", false, type)
                .compose(RxUtil.<CacheResponse<List<FlickerScreenBean>>>io_main())
                .subscribe(new Consumer<CacheResponse<List<FlickerScreenBean>>>() {
                    @Override
                    public void accept(CacheResponse<List<FlickerScreenBean>> listCacheResponse) throws Exception {
                        if (EmptyUtils.isEmpty(listCacheResponse.getData()) || listCacheResponse.getData().size() == 0) {
                            getPresenter().getFlickerScreenInfo();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getPresenter().getFlickerScreenInfo();
                    }
                });
    }

}
