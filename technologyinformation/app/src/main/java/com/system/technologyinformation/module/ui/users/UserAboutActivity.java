package com.system.technologyinformation.module.ui.users;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.di.AppCommonModule;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.module.contract.UserAboutContract;
import com.system.technologyinformation.module.presenter.UserAboutPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.CacheUtil;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.utils.MySharedPreferences;
import com.system.technologyinformation.utils.RestartAPPTool;

import butterknife.BindView;
import butterknife.OnClick;

public class UserAboutActivity extends BaseActivity<UserAboutPresenter> implements UserAboutContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView ivRight;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;
    int clicks = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_about;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("关于我们");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        ivRight.setVisibility(View.GONE);
        if(AppCommonModule.API_BASE_URL.contains("gyj-dev-api.idougong")){
            tvVersionCode.setText("测试版本号 v" + ConstUtils.getVersionName(getBaseContext()));
        }else{
            tvVersionCode.setText("版本号 v" + ConstUtils.getVersionName(getBaseContext()));
        }
        if(login==null){
            tvLogout.setVisibility(View.GONE);
        }else{
            tvLogout.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.toolbar,
            R.id.tv_logout,
            R.id.tv_clear_cache,
            R.id.iv_app_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_logout:
                showLoading();
                getPresenter().getUserLogoutResult();
                break;
            case R.id.tv_clear_cache:
                CacheUtil.clearAllCache(getBaseContext());
                toast("清除成功!");
                break;
            case R.id.iv_app_logo:
                clicks++;
                if (clicks >= 5) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请输入网络切换环境密码");
                    final EditText edit = new EditText(this);
                    edit.setHeight(150);
                    edit.setInputType(InputType.TYPE_CLASS_TEXT);
                    edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                    edit.setHint("请输入密码");
                    builder.setView(edit);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String password = edit.getText().toString();
                            if (password.equals("9527666")) {
                                clicks = 0;
                                hideInput();
                                new SwitchingNetworkView(getBaseContext(), tvVersionCode);
                                dialog.dismiss();
                            } else {
                                toast("密码不正确！");
                            }
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void toast(String msg) {
        super.toast(msg);
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
        if (code == -10) {
            return;
        }
        toast(msg);
    }

    @Override
    public void setUserLogoutResult(String str) {
        hiddenLoading();
        toast("退出成功");
        clearUserInfo();
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    String url;

    public class SwitchingNetworkView extends PopupWindow {

        public SwitchingNetworkView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.switching_network, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            RadioGroup rg_network = (RadioGroup) view.findViewById(R.id.rg_network);
            final EditText edContent = (EditText) view.findViewById(R.id.ed_content);
            rg_network.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    switch (checkedId) {
                        case R.id.radio0:
                            url = EnvConstant.API_RELEASE_URL;
                            edContent.setVisibility(View.GONE);
                            break;
                        case R.id.radio1:
                            edContent.setVisibility(View.GONE);
                            url = EnvConstant.API_DEVENV_URL;
                            break;
                        case R.id.radio2:
                            edContent.setVisibility(View.VISIBLE);
                            url = edContent.getText().toString().trim();
                            break;
                    }
                }
            });
            edContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    url=s.toString().trim();
                }
            });
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (EmptyUtils.isEmpty(url)) {
                        toast("请选择环境！");
                        return;
                    }
                    MySharedPreferences.setName(url, UserAboutActivity.this);
                    RestartAPPTool.restartAPP(getApplicationContext(),100);
                    dismiss();
                }
            });
        }
    }

}
