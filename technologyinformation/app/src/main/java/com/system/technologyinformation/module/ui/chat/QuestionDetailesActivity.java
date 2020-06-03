package com.system.technologyinformation.module.ui.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.net.Constant;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.QuestionDetailesContract;
import com.system.technologyinformation.module.presenter.QuestionDetailesPresenter;
import com.system.technologyinformation.utils.ActivityCollector;

import java.io.File;
import java.net.HttpCookie;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionDetailesActivity extends BaseActivity<QuestionDetailesPresenter> implements QuestionDetailesContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    @BindView(R.id.tv_question_det)
    WebView tvQuestionDet;
    String url;
    String title;

    @Override
    protected int getContentView() {
        return R.layout.activity_question_detailes;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        title = getIntent().getStringExtra("title");
        toolbarTitle.setText(title);
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        url = getIntent().getStringExtra("url");
        WebSettings ws = tvQuestionDet.getSettings();
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
        tvQuestionDet.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
                //title 就是网页的title
                if(!EmptyUtils.isEmpty(title)){
                    toolbarTitle.setText(title);
                }
            }
        });
        tvQuestionDet.setWebViewClient(new MyWebViewClient());
        syncCookie(getBaseContext(), url);
        tvQuestionDet.loadUrl(url);
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
            cookieManager.setAcceptThirdPartyCookies(tvQuestionDet, true);//TODO 跨域cookie读取
        }
        SPUtils spUtils = new SPUtils(Constant.COOKIE);
        String cookie = spUtils.getString(Constant.COOKIE);
        if (!EmptyUtils.isEmpty(cookie)) {
            cookieManager.setCookie(url, cookie);
            CookieSyncManager.getInstance().sync();
        } else {
            if (login != null) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
        }
    }


    /**
     * 给WebView同步Cookie
     *
     * @param context 上下文
     * @param
     */
    private void syncCookie(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);// 允许接受 Cookie
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookie();// 移除
        } else {
            cookieManager.removeSessionCookies(null);// 移除
        }
        SPUtils spUtils = new SPUtils(Constant.COOKIE);
        String cookie = spUtils.getString(Constant.COOKIE);
        if (!EmptyUtils.isEmpty(cookie)) {
            cookieManager.setCookie(url, cookie);
            CookieSyncManager.getInstance().sync();
        } else {
            if (login != null) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
        }
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
            if (url.equals("gyj://home/feedback/submit")) {
                startActivityForResult(new Intent(getBaseContext(), QuestionMessageActivity.class), 2);
                return true;
            } else {
                syncCookie(getBaseContext(), url);

            }
            return false;
        }

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
        } else if (code == -10) {
            return;
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
    public void refreshUserTimeResult(BaseResponseInfo result) {
        if (result.getCode() == 0) {
            syncCookie(getBaseContext(), url);
        } else {
            openLogin();
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            syncCookie(getBaseContext(), url);
            tvQuestionDet.loadUrl(url);
        } else {
            openLogin();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            syncCookie(getBaseContext(), url);
            tvQuestionDet.loadUrl(url);
        }
        if (requestCode == 2) {
            syncCookie(getBaseContext(), url);
            tvQuestionDet.loadUrl(url);
        }
    }
}
