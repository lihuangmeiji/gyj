package com.system.technologyinformation.module.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.AdvertiseInfoBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.TabBean;
import com.system.technologyinformation.model.VersionShowBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.HomePageAdapter;
import com.system.technologyinformation.module.contract.MainContract;
import com.system.technologyinformation.module.fragment.TabCategorizeFirstFragment;
import com.system.technologyinformation.module.fragment.TabCategorizeFourthFragment;
import com.system.technologyinformation.module.fragment.TabCategorizeSecondFragment;
import com.system.technologyinformation.module.presenter.MainPresenter;
import com.system.technologyinformation.module.push.PushMessageDbOpenHelper;
import com.system.technologyinformation.module.push.PushMessageDbOperation;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.chat.QuestionMessageActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeDetailedActivity;
import com.system.technologyinformation.module.ui.home.InformationConsultingActivity;
import com.system.technologyinformation.module.ui.home.PanicBuyingActivity;
import com.system.technologyinformation.module.ui.scanner.ScannerGyjActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.utils.DataKeeper;
import com.system.technologyinformation.utils.ImageLoader;
import com.system.technologyinformation.utils.InstallApk;
import com.system.technologyinformation.utils.NotificationsUtils;
import com.system.technologyinformation.utils.SchemeJump;
import com.system.technologyinformation.utils.SoftUpdate;
import com.system.technologyinformation.widget.CircleImageView;
import com.system.technologyinformation.widget.CustomizeTabLayout;
import com.system.technologyinformation.widget.ImageView_286_383;
import com.system.technologyinformation.widget.NoScrollViewPager;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.umeng.message.PushAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.system.technologyinformation.R2.id.advertiseImage;

/**
 * 主页
 *
 * @author wujiajun
 */
@RuntimePermissions
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private final static String TAG = "MainActivity";
    @BindView(R.id.tabLayout)
    CustomizeTabLayout tabLayout;
    @BindView(R.id.vp_home)
    NoScrollViewPager vp_home;
    @BindView(R.id.status_bar_view)
    View status_bar_view;


    private ArrayList<Fragment> fragments;
    private ArrayList<TabBean> mTabbeans = new ArrayList<>();

    private String[] mTitles = {"首页", "任务", "我的"};

    private int[] mUnSelectIcons = {
            R.mipmap.tab1wx, R.mipmap.tab2wx, R.mipmap.tab4wx};
    private int[] mSelectIcons = {
            R.mipmap.tab1yx, R.mipmap.tab2yx, R.mipmap.tab4yx};

    private int mSelectColor = R.color.black;
    private int mUnSelectColor = R.color.black;

    private final int REQUEST_PERMISION_CODE_CAMARE = 0;
    private final int RESULT_REQUEST_CODE = 1;
    BroadcastReceiver broadcastReceiver;
    boolean isOpen = false;
    private int mCurrentTab;


    Intent intent;

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        initFragments();
        boolean weekend = isWeekend(Calendar.getInstance());
        SPUtils spUtils = new SPUtils(ConstUtils.CLEARDBNAME);
        if (weekend && spUtils.getInt(ConstUtils.CLEARDBKEY, 0) == 0) {
            spUtils.put(ConstUtils.CLEARDBKEY, 1);
            PushMessageDbOpenHelper pushMessageDbOpenHelper = new PushMessageDbOpenHelper(getBaseContext(), com.system.technologyinformation.common.net.Constant.pushMessageDbName, null, com.system.technologyinformation.common.net.Constant.pushMessageDbVersion);
            PushMessageDbOperation.deletePushMessage(pushMessageDbOpenHelper.getReadableDatabase(), null, null);
        }
        if (isMonday(Calendar.getInstance())) {
            spUtils.put(ConstUtils.CLEARDBKEY, 0);
        }
        if (!NotificationsUtils.isPermissionOpen(this)) {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.show();
            View view = View.inflate(this, R.layout.user_prompt_loading, null);
            dialog.setContentView(view);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText("通知栏权限");
            TextView context = (TextView) view.findViewById(R.id.tv_content);
            context.setText("检测到您没有打开通知权限，是否去打开");
            TextView confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            confirm.setText("确定");
            confirm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                    NotificationsUtils.openPermissionSetting(getBaseContext());
                }
            });

            TextView cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        addUpdateInfoReceiver();
        getPresenter().getVersionResult();
        MainActivityPermissionsDispatcher.showReadOrWriteWithCheck(MainActivity.this, "", 2);
        status_bar_view.setVisibility(View.GONE);
        schemeTz();
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        Log.i(TAG, "rid: " + rid);
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.w(Constants.LogTag, "+++ register push sucess. token:" + data + "flag" + flag);
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.w(Constants.LogTag,
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);
                    }
                });

        // 获取token
        String XGtoken = XGPushConfig.getToken(this);
        Log.i(TAG, "XGtoken: " + XGtoken);

        getPresenter().addPushMessageToken(XGPushConfig.getToken(this), PushAgent.getInstance(this).getRegistrationId(), JPushInterface.getRegistrationID(getApplicationContext()));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
        schemeTz();
    }

    public void schemeTz() {
        // 访问路径
        String path = getIntent().getStringExtra("path");
        String title = getIntent().getStringExtra("title");
        int shoppingId = getIntent().getIntExtra("shopId", 0);
        if (!EmptyUtils.isEmpty(path)) {
            Log.i("path", "initEventAndData: " + path);
            SchemeJump.schemeJump(getBaseContext(), path, shoppingId, title);
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.rel_sm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_sm:
                if (login != null) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        goScanner();
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
                    }
                /*    Intent intent = new Intent(MainActivity.this, UserPayActivity.class);
                    intent.putExtra("storeId", 1);
                    startActivity(intent);*/
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }


    private boolean isWeekend(Calendar cal) {
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {//0代表周日
            return true;
        }
        return false;
    }

    private boolean isMonday(Calendar cal) {
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 1) {//1代表周一
            return true;
        }
        return false;
    }

    /**
     * 初始化主页5个fragment
     */
    private void initFragments() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            addTab(i);
        }
        fragments.add(new TabCategorizeFirstFragment());
        fragments.add(new TabCategorizeSecondFragment());
        fragments.add(new TabCategorizeFourthFragment());
        //设置viewpager的适配器
        vp_home.setAdapter(new HomePageAdapter(getSupportFragmentManager(), fragments));
        //viewpager会初始化左右两边各3个
        vp_home.setOffscreenPageLimit(2);
        tabLayout.setTabDate(mTabbeans);
        tabLayout.setmListener(new CustomizeTabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    status_bar_view.setVisibility(View.GONE);
                } else {
                    status_bar_view.setVisibility(View.VISIBLE);
                    status_bar_view.setBackgroundColor(getBaseContext().getResources().getColor(R.color.color37));
                    mCurrentTab = position;
                }
                vp_home.setCurrentItem(position, false);
            }

            /**
             * 连续点击调用此方法
             */
            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    public void addTab(int index) {
        TabBean tabBean1 = new TabBean();
        tabBean1.setTitle(mTitles[index]);
        tabBean1.setSelectUrl(null);
        tabBean1.setUnSelectUrl(null);
        tabBean1.setmSelectColor(getBaseContext().getResources().getColor(mSelectColor));
        tabBean1.setmUnSelectColor(getBaseContext().getResources().getColor(mUnSelectColor));
        tabBean1.setSelectIcons(mSelectIcons[index]);
        tabBean1.setUnSelectIcon(mUnSelectIcons[index]);
        mTabbeans.add(tabBean1);
    }


    private void goScanner() {
        Intent intent = new Intent(this, ScannerGyjActivity.class);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }


    public void categoryselect(int position) {
        if (position == 0) {
            status_bar_view.setVisibility(View.GONE);
        }
        vp_home.setCurrentItem(position);
        tabLayout.setCurrentTab(position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 0) {
            categoryselect(mCurrentTab);
        }

        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
            categoryselect(mCurrentTab);
        }

        if (requestCode == 2 && resultCode == 2) {
            categoryselect(1);
        }
        if (requestCode == 10012) {
            if (Build.VERSION.SDK_INT >= 26) {
                boolean b = getBaseContext().getPackageManager().canRequestPackageInstalls();
                if (b) {
                    new InstallApk(this)
                            .installApk(new File(DataKeeper.fileRootPath, "gyj.apk"));
                } else {
                    toast("请赋予权限后在操作！");
                    // 退出程序
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        if (code == -1) {
            clearUserInfo();
            return;
        }
        toast(msg);
    }

    @Override
    public void setVersionResult(VersionShowBean versionShowBean) {
        if (versionShowBean != null) {
            if (versionShowBean.getVersion() > ConstUtils.getVersioncode(getBaseContext())) {
                new DownLoadingView(getBaseContext(), vp_home, versionShowBean);

            } else {
                getPresenter().getAdvertiseInfo();
            }
        } else {
            getPresenter().getAdvertiseInfo();
        }
    }


    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean != null) {
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            userInfo(2);
        } else {
            clearUserInfo();
            userInfo(2);
        }
    }

    @Override
    public void addPushMessageTokenResult(String str) {
        //String content = "因为您关闭了工友家APP的读写手机权限\n请在设置中打开工友家APP的读写手机存储权限";
    }

    @Override
    public void setAdvertiseWindow(List<AdvertiseInfoBean> advertiseInfoBeans) {
        try {
            if (advertiseInfoBeans != null && advertiseInfoBeans.size()>0&&!EmptyUtils.isEmpty(advertiseInfoBeans.get(0).getImg())) {
                new AdvertiseWindows(getBaseContext(), vp_home, advertiseInfoBeans.get(0));
            } 
        }catch (Exception e){
            Log.i(TAG, "setAdvertiseWindow: 获取数据失败！");
        }
    }

    public void userInfo(int cztype) {
        loadUserInfo();
    }

    public class DownLoadingView extends PopupWindow {

        public DownLoadingView(Context mContext, View parent, final VersionShowBean versionShowBean) {

            View view = View.inflate(mContext, R.layout.down_loading, null);
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
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_content.setText(versionShowBean.getMsg().replace("\\n", "\n"));
            if (versionShowBean.getForceUpdate() == 1) {
                btn_dialog_cancel.setVisibility(View.GONE);
            } else {
                btn_dialog_cancel.setVisibility(View.VISIBLE);
            }
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    //String url = versionShowBean.getUrl();
                    dismiss();
                    MainActivityPermissionsDispatcher.showReadOrWriteWithCheck(MainActivity.this, versionShowBean.getUrl(), 1);
                }
            });
        }
    }


    public class ReminderLoadingView extends PopupWindow {

        public ReminderLoadingView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.confirm_loading, null);
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
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            String content = "因为您关闭了工友家APP的读写手机权限\n请在设置中打开工友家APP的读写手机存储权限";
            tv_content.setText(content.replace("\\n", "\n"));
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText("无法自动更新");
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //String url = versionShowBean.getUrl();
                    dismiss();
                    finish();
                }
            });
        }
    }

    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showReadOrWrite(String url, int type) {
        if (type == 1) {
            SoftUpdate manager = new SoftUpdate(MainActivity.this, url);
            manager.showDownloadDialog();
        }
    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForReadOrWrite(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("软件更新")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForReadOrWrite() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForReadOrWrite() {
        //Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();
        new ReminderLoadingView(getBaseContext(), vp_home);
        //finish();
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
        // 代理权限处理到自动生成的方法
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    public class PayWayView extends PopupWindow {

        public PayWayView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_pay_way, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(ActionBar.LayoutParams.FILL_PARENT);

            setHeight(ActionBar.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);

            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            TextView tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
            TextView tv_pay_integral = (TextView) view.findViewById(R.id.tv_pay_integral);
            LinearLayout lin_pay_wx = (LinearLayout) view.findViewById(R.id.lin_pay_wx);
            LinearLayout lin_pay_zfb = (LinearLayout) view.findViewById(R.id.lin_pay_zfb);
            View view_pay = view.findViewById(R.id.view_pay);
            final ImageView iv_wx_select = (ImageView) view.findViewById(R.id.iv_wx_select);
            final ImageView iv_zfb_select = (ImageView) view.findViewById(R.id.iv_zfb_select);
            //tv_pay_money.setText("¥"+storeOrderResult.getFinalPrice());
            //double dk=storeOrderResult.getTotalPrice()-storeOrderResult.getFinalPrice();
            /*if(dk==0.0){
                tv_pay_integral.setVisibility(View.GONE);
            }else{
                tv_pay_integral.setVisibility(View.VISIBLE);
                DecimalFormat df = new DecimalFormat("#.00");
                tv_pay_integral.setText("已抵扣¥"+ df.format(dk));
            }*/
            lin_pay_wx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfyx);
                    iv_zfb_select.setImageResource(R.mipmap.zfwx);
                    //getPresenter().getUserRechargeWx(storeOrderResult.getId());
                    dismiss();
                }
            });
            lin_pay_zfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_wx_select.setImageResource(R.mipmap.zfwx);
                    iv_zfb_select.setImageResource(R.mipmap.zfyx);
                }
            });

            view_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    private void addUpdateInfoReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadUserInfo();
                getPresenter().addPushMessageToken(XGPushConfig.getToken(getBaseContext()), PushAgent.getInstance(getBaseContext()).getRegistrationId(), JPushInterface.getRegistrationID(getBaseContext()));
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


    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
    private long firstTime = 0;//第一次返回按钮计时


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    toast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    public void ShareImage(SHARE_MEDIA share_media, String content) {
        new ShareAction(this)
                .withText(content)//分享内容
                .setPlatform(share_media)
                .share();
    }

    public class AdvertiseWindows extends PopupWindow {

        public AdvertiseWindows(Context mContext, View parent, final AdvertiseInfoBean advertiseInfoBean) {
            View view = View.inflate(mContext, R.layout.popupwindow_advertise, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));

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
            ImageView_286_383 advertiseImage = view.findViewById(R.id.advertiseImage);
            Glide.with(getBaseContext()).load(advertiseInfoBean.getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(advertiseImage);
            advertiseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (advertiseInfoBean.isNeedLogin() == false) {
                        if (EmptyUtils.isEmpty(advertiseInfoBean.getTarget())) {
                            return;
                        }
                        if (advertiseInfoBean.getTarget().contains("gyj://")) {
                            if (advertiseInfoBean.getTarget().contains("main")) {
                                PackageManager manager = getBaseContext().getPackageManager();
                                String scheme;
                                if (advertiseInfoBean.getTarget().contains("?id=")) {
                                    scheme = advertiseInfoBean.getTarget() + "&lytype=bd";
                                } else {
                                    scheme = advertiseInfoBean.getTarget() + "?lytype=bd";
                                }
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(scheme));
                                List list = manager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
                                if (list != null && list.size() > 0) {
                                    startActivity(intent);
                                }
                            } else {
                                if (advertiseInfoBean.getTarget().contains("home/exchange")) {
                                    if (advertiseInfoBean.getTarget().contains("home/exchange?id")) {
                                        Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                        intent.putExtra("shoppingId", Integer.valueOf(advertiseInfoBean.getTarget().replace("gyj://home/exchange?id=", "")).intValue());
                                        startActivity(intent);
                                    } else {
                                        startActivity(new Intent(getBaseContext(), CreditsExchangeActivity.class));
                                    }
                                } else if (advertiseInfoBean.getTarget().contains("home/mall")) {
                                    if (advertiseInfoBean.getTarget().contains("home/mall?id")) {
                                        Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                        intent.putExtra("shoppingId", Integer.valueOf(advertiseInfoBean.getTarget().replace("gyj://home/mall?id=", "")).intValue());
                                        startActivity(intent);
                                    } else {
                                        startActivity(new Intent(getBaseContext(), PanicBuyingActivity.class));
                                    }
                                } else if (advertiseInfoBean.getTarget().contains("food")) {
                                    startActivity(new Intent(getBaseContext(), TableReservationActivity.class));
                                } else if (advertiseInfoBean.getTarget().contains("news")) {
                                    startActivity(new Intent(getBaseContext(), InformationConsultingActivity.class));
                                } else if (advertiseInfoBean.getTarget().contains("feedback/submit")) {
                                    startActivity(new Intent(getBaseContext(), QuestionMessageActivity.class));
                                }
                            }
                        } else {
                            Intent intentWeb = new Intent(getBaseContext(), QuestionDetailesActivity.class);
                            intentWeb.putExtra("title", advertiseInfoBean.getName());
                            intentWeb.putExtra("url", advertiseInfoBean.getTarget());
                            startActivity(intentWeb);
                        }
                    } else {
                        if (login != null) {
                            if (EmptyUtils.isEmpty(advertiseInfoBean.getTarget())) {
                                return;
                            }
                            if (advertiseInfoBean.getTarget().contains("gyj://")) {
                                if (advertiseInfoBean.getTarget().contains("main")) {
                                    PackageManager manager = getBaseContext().getPackageManager();
                                    String scheme;
                                    if (advertiseInfoBean.getTarget().contains("?id=")) {
                                        scheme = advertiseInfoBean.getTarget() + "&lytype=bd";
                                    } else {
                                        scheme = advertiseInfoBean.getTarget() + "?lytype=bd";
                                    }
                                    Intent intent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(scheme));
                                    List list = manager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
                                    if (list != null && list.size() > 0) {
                                        startActivity(intent);
                                    }
                                } else {
                                    if (advertiseInfoBean.getTarget().contains("home/exchange")) {
                                        if (advertiseInfoBean.getTarget().contains("home/exchange?id")) {
                                            Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                            intent.putExtra("shoppingId", Integer.valueOf(advertiseInfoBean.getTarget().replace("gyj://home/exchange?id=", "")).intValue());
                                            startActivity(intent);
                                        } else {
                                            startActivity(new Intent(getBaseContext(), CreditsExchangeActivity.class));
                                        }
                                    } else if (advertiseInfoBean.getTarget().contains("home/mall")) {
                                        if (advertiseInfoBean.getTarget().contains("home/mall?id")) {
                                            Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                                            intent.putExtra("shoppingId", Integer.valueOf(advertiseInfoBean.getTarget().replace("gyj://home/mall?id=", "")).intValue());
                                            startActivity(intent);
                                        } else {
                                            startActivity(new Intent(getBaseContext(), PanicBuyingActivity.class));
                                        }
                                    } else if (advertiseInfoBean.getTarget().contains("food")) {
                                        startActivity(new Intent(getBaseContext(), TableReservationActivity.class));
                                    } else if (advertiseInfoBean.getTarget().contains("news")) {
                                        startActivity(new Intent(getBaseContext(), InformationConsultingActivity.class));
                                    } else if (advertiseInfoBean.getTarget().contains("feedback/submit")) {
                                        startActivity(new Intent(getBaseContext(), QuestionMessageActivity.class));
                                    }
                                }
                            } else {
                                Intent intentWeb = new Intent(getBaseContext(), QuestionDetailesActivity.class);
                                intentWeb.putExtra("title", advertiseInfoBean.getName());
                                intentWeb.putExtra("url", advertiseInfoBean.getTarget() + "?userId=" + login.getId());
                                startActivity(intentWeb);
                            }
                        } else {
                            openLogin();
                        }
                    }
                    dismiss();
                }
            });
            CircleImageView advertiseCloseimage = view.findViewById(R.id.advertise_closeimage);
            advertiseCloseimage.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                        }
                    }
            );
        }
    }


}