package com.system.technologyinformation.module.demo4;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;

import butterknife.BindView;

/**
 * Created by wujiajun on 2017/10/23.
 */

public class Demo4Activity extends SimpleActivity {
    @BindView(R.id.wv_enrollment)
    WebView wv_enrollment;
    @Override
    protected void initEventAndData() {
        commonTheme();
        WebSettings ws = wv_enrollment.getSettings();
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setBuiltInZoomControls(false);//  隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//  排版适应屏幕
        ws.setUseWideViewPort(true);//
        ws.setLoadWithOverviewMode(true);//  setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);//  保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setLoadsImagesAutomatically(true);//支持自动加载图片
        ws.setGeolocationEnabled(true);//  启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");//  设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        wv_enrollment.setBackgroundColor(0); // 设置背景色
        wv_enrollment.setWebChromeClient(new WebChromeClient());
        wv_enrollment.loadUrl("https://h5.xuexi.cn/page/download.html");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_demo4;
    }
}
