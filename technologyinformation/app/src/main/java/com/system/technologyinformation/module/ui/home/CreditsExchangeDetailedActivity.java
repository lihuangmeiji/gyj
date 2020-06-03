package com.system.technologyinformation.module.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.net.Constant;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.OnlineSupermarketOrderBean;
import com.system.technologyinformation.module.contract.CreditsExchangeDetailedContract;
import com.system.technologyinformation.module.presenter.CreditsExchangeDetailedPresenter;
import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.entertainment.UserConfirmAnOrderActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.utils.TextUtil;
import com.system.technologyinformation.widget.MyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class CreditsExchangeDetailedActivity extends BaseActivity<CreditsExchangeDetailedPresenter> implements CreditsExchangeDetailedContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.status_bar_view)
    View statusBarView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_shopping_name)
    TextView tvShoppingName;
    @BindView(R.id.tv_shopping_point)
    TextView tvShoppingPoint;
    @BindView(R.id.tv_shopping_original_integral)
    TextView tvShoppingOriginalIntegral;
    @BindView(R.id.tv_shopping_det_content)
    WebView tvShoppingDetContent;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    @BindView(R.id.sv_detaile)
    MyScrollView svDetaile;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.tv_confirm_ce)
    TextView tvConfirmCe;
    @BindView(R.id.tv_add_shopcar)
    TextView tvAddShopcar;
    @BindView(R.id.tv_shopping_buy)
    TextView tvShoppingBuy;
    int shoppingId = 0;
    int point = 0;
    int shoppingType = 0;
    List<OnlineSupermarketBean> tableReservationBeanList;
    List<OnlineSupermarketOrderBean> onlineSupermarketOrderBeanList;

    BroadcastReceiver broadcastReceiver;
    private long firstTime = 0;//第一次点击事件
    long secondTime;

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_credits_exchange_detailed;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        isClose = false;
        addUpdateInfoReceiver();
        toolbarTitle.setText("商品详情");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        linTitle.setVisibility(View.GONE);
        ActivityCollector.addActivity(this);
        tableReservationBeanList = new ArrayList<>();
        onlineSupermarketOrderBeanList = new ArrayList<>();
        WebSettings ws = tvShoppingDetContent.getSettings();
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setBuiltInZoomControls(false);//  隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//  排版适应屏幕
        ws.setUseWideViewPort(true);//
        ws.setLoadWithOverviewMode(true);//  setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);//  保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);// 设置可以访问文件
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setLoadsImagesAutomatically(true);//支持自动加载图片
        ws.setGeolocationEnabled(true);//  启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");//  设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        tvShoppingDetContent.setWebChromeClient(new WebChromeClient());
        tvShoppingDetContent.setWebViewClient(new MyWebViewClient());
        //syncCookie(getBaseContext(), url);
        shoppingId = getIntent().getIntExtra("shoppingId", 0);
        shoppingType = getIntent().getIntExtra("shoppingType", 0);
        svDetaile.setSVListener(new MyScrollView.SVListener() {
            @Override
            public void OnScrollChanger(MyScrollView scrollView, int l, int t, int oldl, int oldt) {
                if (t <= 0) {
                    ivGoBack.setVisibility(View.VISIBLE);
                    linTitle.setVisibility(View.GONE);
                } else if (t > 0 && t < 200) {
                    ivGoBack.setVisibility(View.GONE);
                    linTitle.setVisibility(View.VISIBLE);
                }
            }
        });

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
            try {
                shoppingId = Integer.valueOf(uri.getQueryParameter("id")).intValue();
            } catch (Exception e) {
                Log.i("shoppingId", "转换错误");
            }
        }
        getPresenter().getShoppingDetailedResult(shoppingId);
        if (login != null) {
            getPresenter().getUpdateUserInfoResult(0);
        }
    }

    @Override
    public void setShoppingDetailedResult(HomeShoppingSpreeBean homeShoppingSpreeBean) {
        if (homeShoppingSpreeBean != null) {
            String data = homeShoppingSpreeBean.getDetails();
            //data = data.replaceAll("width=\"\\d+\"", "width=\"100%\"").replaceAll("height=\"\\d+\"", "height=\"auto\"");
            //String varjs = "<script type='text/javascript'> window.onload = function() {var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
            String html = "<html><head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\" type=\"text/css\" href=\"home1.css\" /> <link rel=\"stylesheet\" type=\"text/css\" href=\"home2.css\" /> <link rel=\"stylesheet\" type=\"text/css\" href=\"c.css\" /> </head> <body> <div class='edit_wrap'>" + data + " </div></body></html>";
            tvShoppingDetContent.loadDataWithBaseURL("file:///android_asset/", html, "text/html; charset=UTF-8", null, null);
            tvShoppingName.setText(homeShoppingSpreeBean.getName());
            if (shoppingType == 1) {
                tvShoppingPoint.setText("¥" + homeShoppingSpreeBean.getCurrentPrice());
                tvShoppingOriginalIntegral.setText("¥" + homeShoppingSpreeBean.getSourcePrice());
                tvShoppingOriginalIntegral.setVisibility(View.VISIBLE);
                tvShoppingOriginalIntegral.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvConfirmCe.setVisibility(View.GONE);
                tvAddShopcar.setVisibility(View.VISIBLE);
                tvShoppingBuy.setVisibility(View.VISIBLE);
            } else {
                //tvShoppingPoint.setText(homeShoppingSpreeBean.getPoint() + "积分");
                if (!EmptyUtils.isEmpty(homeShoppingSpreeBean.getCurrentPrice()) && homeShoppingSpreeBean.getCurrentPrice() > 0) {
                    if (homeShoppingSpreeBean.getPoint() > 0) {
                        tvShoppingPoint.setText(TextUtil.FontHighlighting(getBaseContext(), "¥", "" + homeShoppingSpreeBean.getCurrentPrice(), "+" + homeShoppingSpreeBean.getPoint() + "积分", R.style.tv_ce_point1, R.style.tv_ce_point2, R.style.tv_ce_point1));
                    } else {
                        tvShoppingPoint.setText(TextUtil.FontHighlighting(getBaseContext(), "¥", "" + homeShoppingSpreeBean.getCurrentPrice(), R.style.tv_ce_point1, R.style.tv_ce_point2));
                    }
                } else {
                    if (homeShoppingSpreeBean.getPoint() > 0) {
                        tvShoppingPoint.setText(TextUtil.FontHighlighting(getBaseContext(), homeShoppingSpreeBean.getPoint() + "", "积分", R.style.tv_ce_point2, R.style.tv_ce_point1));
                    } else {
                        tvConfirmCe.setText("已售罄");
                        tvConfirmCe.setEnabled(false);
                        tvConfirmCe.setSelected(false);
                    }
                }
                tvShoppingOriginalIntegral.setVisibility(View.GONE);
                tvConfirmCe.setVisibility(View.VISIBLE);
                tvAddShopcar.setVisibility(View.GONE);
                tvShoppingBuy.setVisibility(View.GONE);
                if (homeShoppingSpreeBean.getInventory() == 0) {
                    tvConfirmCe.setText("已售罄");
                    tvConfirmCe.setEnabled(false);
                    tvConfirmCe.setSelected(false);
                } else {
                    tvConfirmCe.setText("立即兑换");
                    tvConfirmCe.setEnabled(true);
                    tvConfirmCe.setSelected(true);
                }
            }
            loadTestDatas(homeShoppingSpreeBean.getImageList());
            point = homeShoppingSpreeBean.getPoint();
            OnlineSupermarketOrderBean onlineSupermarketOrderBean = new OnlineSupermarketOrderBean();
            onlineSupermarketOrderBean.setNum(1);
            onlineSupermarketOrderBean.setProductId(homeShoppingSpreeBean.getId());
            onlineSupermarketOrderBeanList.add(onlineSupermarketOrderBean);
            OnlineSupermarketBean onlineSupermarketBean = new OnlineSupermarketBean();
            onlineSupermarketBean.setName(homeShoppingSpreeBean.getName());
            onlineSupermarketBean.setShopnumber(1);
            onlineSupermarketBean.setCurrentPrice(homeShoppingSpreeBean.getCurrentPrice());
            onlineSupermarketBean.setImage(homeShoppingSpreeBean.getImage());
            tableReservationBeanList.add(onlineSupermarketBean);
        } else {
            toast("数据加载失败");
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            getPresenter().getShoppingDetailedResult(shoppingId);
        } else {
            openLogin();
        }
    }

    @Override
    public void setOnlineSupermarketGoodsOreder(ConfirmOrderBean confirmOrderBean) {
        tvShoppingBuy.setEnabled(true);
        tvShoppingBuy.setSelected(true);
        if (confirmOrderBean != null) {
            Intent intent = new Intent(getBaseContext(), UserConfirmAnOrderActivity.class);
            intent.putExtra("onlineSupermarketBeanList", (Serializable) tableReservationBeanList);
            intent.putExtra("goodstype", 0);
            intent.putExtra("mtotalPrice", confirmOrderBean.getTotalPrice());
            intent.putExtra("deliveryId", confirmOrderBean.getDeliveryId());
            intent.putExtra("orderId", confirmOrderBean.getId());
            startActivityForResult(intent, 2);
        } else {
            toast("预订单生成失败");
        }
    }

    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
        } else {
            clearUserInfo();
            loadUserInfo();
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            syncCookie(getBaseContext(), s);
            return super.shouldOverrideUrlLoading(webView, s);
        }
    }

    /**
     * 给WebView同步Cookie
     *
     * @param context 上下文
     * @param url     可以使用[domain][host]
     */
    private void syncCookie(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除旧的[可以省略]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(tvShoppingDetContent, true);//TODO 跨域cookie读取
        }
        SPUtils spUtils = new SPUtils(Constant.COOKIE);
        String cookie = spUtils.getString(Constant.COOKIE);
        if (!EmptyUtils.isEmpty(cookie)) {
            cookieManager.setCookie(url, cookie);
            CookieSyncManager.getInstance().sync();
        }
    }

    private void loadTestDatas(List<String> stringList) {
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).error(R.mipmap.shoppingmr).fallback(R.mipmap.shoppingmr).into(imageView);
            }
        });
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置图片集合
        banner.setImages(stringList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @OnClick({R.id.toolbar,
            R.id.iv_go_back,
            R.id.tv_confirm_ce,
            R.id.tv_shopping_buy,
            R.id.tv_add_shopcar
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.tv_confirm_ce:
                if (login == null) {
                    openLogin();
                    return;
                }
                if (login.getPoint() < point || point == 0) {
                    toast("积分不够，去完成任务赚取积分！");
                    return;
                }
                Intent intent = new Intent(getBaseContext(), CreditsExchangeConfirmActivity.class);
                intent.putExtra("shoppingId", shoppingId);
                startActivity(intent);
                break;
            case R.id.tv_shopping_buy:
                if (login == null) {
                    openLogin();
                    return;
                }
                if (EmptyUtils.isEmpty(login.getConstructionPlace())) {
                    toast("请进行工地认证!");
                    return;
                }
                if (onlineSupermarketOrderBeanList.size() == 0) {
                    toast("商品信息获取失败!");
                    return;
                }
                tvShoppingBuy.setEnabled(false);
                tvShoppingBuy.setSelected(false);
                Gson gson = new Gson();
                getPresenter().getOnlineSupermarketGoodsOreder(gson.toJson(onlineSupermarketOrderBeanList));
                break;
            case R.id.tv_add_shopcar:
                toast("暂未开放");
                break;
        }
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
        tvShoppingBuy.setEnabled(true);
        tvShoppingBuy.setSelected(true);
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == 405) {
            svDetaile.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getShoppingDetailedResult(shoppingId);
                }
            });
        } else if (code == -10) {
            return;
        } else {
            svDetaile.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
        toast(msg);
    }


    @Override
    public void toast(String msg) {
        super.toast(msg);
    }


    private void addUpdateInfoReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getPresenter().getUpdateUserInfoResult(0);
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("userupdate");
        registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

}
