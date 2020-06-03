package com.system.technologyinformation.module.ui.account;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.LoginContract;
import com.system.technologyinformation.module.presenter.LoginPresenter;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.utils.LocationService;
import com.system.technologyinformation.utils.PreventKeyboardBlockUtil;
import com.system.technologyinformation.utils.SmsObserver;
import com.system.technologyinformation.utils.TextUtil;
import com.system.technologyinformation.widget.ClearEditText;
import com.system.technologyinformation.widget.VerificationCodeView;


import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, VerificationCodeView.OnCodeFinishListener {

    private static final String TAG = "LoginActivity";
    private int isShowType = 0;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.lin_content1)
    LinearLayout linContent1;
    @BindView(R.id.lin_content2)
    LinearLayout linContent2;
    @BindView(R.id.tv_show_phone)
    TextView tvShowPhone;
    @BindView(R.id.tv_show_code_hint)
    TextView tvShowCodeHint;
    @BindView(R.id.tv_code_error_info)
    TextView tvCodeErrorInfo;
    @BindView(R.id.et_code_show1)
    EditText etCodeShow1;
    @BindView(R.id.et_code_show2)
    EditText etCodeShow2;
    @BindView(R.id.et_code_show3)
    EditText etCodeShow3;
    @BindView(R.id.et_code_show4)
    EditText etCodeShow4;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    @BindView(R.id.cb_user_agreement)
    CheckBox cbUserAgreement;
    @BindView(R.id.verificationcodeview)
    VerificationCodeView verificationcodeview;
    @BindView(R.id.v_jptq)
    View v_jptq;
    private int countDownTime = 60;
    private static final int COUNT_DOWN = 1;
    //展示手机尾号的Tv
    private TextView[] tailTvs = new TextView[4];

    public static final int MSG_RECEIVED_CODE = 11;
    private SmsObserver mObserver;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        isShowType = 0;
        linContent1.setVisibility(View.VISIBLE);
        linContent2.setVisibility(View.GONE);
        tvUserAgreement.setVisibility(View.VISIBLE);
        cbUserAgreement.setVisibility(View.VISIBLE);
        Observable<CharSequence> phoneObservable = RxTextView.textChanges(etPhone);
        Observable<CharSequence> codeObservable = RxTextView.textChanges(etPhone);
        Observable.combineLatest(phoneObservable, codeObservable, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2) throws Exception {
                return TextUtils.isEmpty(charSequence) && TextUtils.isEmpty(charSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                tvGetCode.setEnabled(!aBoolean);
                tvGetCode.setSelected(!aBoolean);
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 11) {
                    getPresenter().registerCheckPhone(s.toString().trim());
                }
            }
        });

        verificationcodeview.setOnCodeFinishListener(this);
        tvUserAgreement.setText(TextUtil.FontHighlighting4(getBaseContext(), "同意", "工友家用户协议", "、", "隐私政策", R.style.tvUserAgreement1, R.style.tvUserAgreement2));
        //一定要记得设置这个方法  不是不起作用
        tvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        LoginActivityPermissionsDispatcher.readSmsWithCheck(this);
    }

    @OnClick({R.id.tv_get_code, R.id.iv_close, R.id.tv_show_code_hint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (!isMobile(etPhone.getText().toString().trim())) {
                    toast("手机号格式不正确");
                    return;
                }
                if (cbUserAgreement.isChecked() == false) {
                    toast("请勾选用户协议和隐私协议");
                    return;
                }
                isShowType = 1;
                linContent1.setVisibility(View.GONE);
                linContent2.setVisibility(View.VISIBLE);
                tvUserAgreement.setVisibility(View.GONE);
                cbUserAgreement.setVisibility(View.GONE);
                ivClose.setImageResource(R.mipmap.ic_return);
                hideInput();
                tvShowPhone.setText("验证码已发送至  " + etPhone.getText().toString().substring(0, 3) + "    " + etPhone.getText().toString().substring(3, 7) + "    " + etPhone.getText().toString().substring(7, 11));
                getPresenter().registerUserInfoSms(etPhone.getText().toString().trim());
                break;
            case R.id.iv_close:
                if (isShowType == 0) {
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                } else {
                    isShowType = 0;
                    linContent1.setVisibility(View.VISIBLE);
                    linContent2.setVisibility(View.GONE);
                    tvUserAgreement.setVisibility(View.VISIBLE);
                    cbUserAgreement.setVisibility(View.VISIBLE);
                    ivClose.setImageResource(R.mipmap.login_close);
                    mHandler.removeCallbacksAndMessages(null);
                    etCodeShow1.setText("");
                    etCodeShow2.setText("");
                    etCodeShow3.setText("");
                    etCodeShow4.setText("");
                    tvCodeErrorInfo.setText("");
                    tvShowCodeHint.setText("");
                }
                break;
            case R.id.tv_show_code_hint:
                getPresenter().registerUserInfoSms(etPhone.getText().toString().trim());
                break;
        }
    }


    @Override
    public void setUserLoginResult(final LoginBean loginBean) {
        if (loginBean != null) {
            toast("登录成功");
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
            Intent intentuserupdate = new Intent("userupdate");
            sendBroadcast(intentuserupdate);
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        } else {
            toast("用户信息错误");
        }
    }

    @Override
    public void registerCheckPhoneResult(BaseResponseInfo baseResponseInfo) {
        if (baseResponseInfo.getCode() == 0) {
            boolean cp = Boolean.valueOf(baseResponseInfo.getData().toString());
            if (cp) {
                etInviteCode.setVisibility(View.VISIBLE);
            } else {
                etInviteCode.setVisibility(View.GONE);
            }
        } else {
            toast(baseResponseInfo.getDetail());
        }
    }

    @Override
    public void registerUserInfoSmsResult(BaseResponseInfo baseResponseInfo) {
        if (baseResponseInfo.getCode() == 0) {
            tvCodeErrorInfo.setText("发送成功");
            countDownTime = 60;//下次点击时倒计时重新置为原倒计时数
            setCountDown(tvShowCodeHint, COUNT_DOWN);
        } else {
            tvCodeErrorInfo.setText(baseResponseInfo.getMsg());
        }
    }


    /**
     * 设置倒计时
     */
    private void setCountDown(TextView tv, int code) {
        if(EmptyUtils.isEmpty(tv)){
            tv=findViewById(R.id.tv_show_code_hint);
        }
        tv.setEnabled(false);
        if (code == COUNT_DOWN) {
            tv.setText(countDownTime + "秒后重获取验证码");
        }
        mHandler.sendEmptyMessageDelayed(code, 1000);
    }


    /**
     * 处理倒计时的msg
     *
     * @param time    对应哪个倒计时
     * @param msgCode 消息的code码
     * @param tv      哪个textview对应变化
     */
    private void handleCountMessage(int time, int msgCode, TextView tv) {
        if (msgCode == COUNT_DOWN) {
            countDownTime--;
        }
        time--;
        if (time > 0) {
            setCountDown(tv, msgCode);
        } else if (time == 0) {
            //防止在验证码倒计时结束后，按钮不可点击的问题
            if (!TextUtils.isEmpty(tvShowPhone.getText().toString().trim())) {
                tvShowCodeHint.setEnabled(true);
            } else {
                tvShowCodeHint.setEnabled(false);
            }
            tv.setText("重新获取验证码");
        } else {
            mHandler.removeMessages(msgCode);
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT_DOWN:
                    handleCountMessage(countDownTime, COUNT_DOWN, tvShowCodeHint);
                    break;
                case MSG_RECEIVED_CODE:
                    String code = (String) msg.obj;
                    if (code.length() == 4) {
                        Log.i(TAG, "handleMessage: " + code);
                        //verificationcodeview.setCode(code);
                    }
                    break;
            }
        }
    };

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
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
    public void onTextChange(View view, String content) {

    }

    @Override
    public void onComplete(View view, String content) {
        if (view == verificationcodeview && content.length() == 4) {
            getPresenter().getUserLoginResult(etPhone.getText().toString().trim(), etInviteCode.getText().toString().trim(), content, "");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreventKeyboardBlockUtil.getInstance(this).setBtnView(v_jptq).register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreventKeyboardBlockUtil.getInstance(this).unRegister();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler = null;
                }
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void readSms() {
        mObserver = new SmsObserver(LoginActivity.this, mHandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, mObserver);
    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void showRationaleForReadSms(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("验证码获取")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void showDeniedForReadSms() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void showNeverAskForReadSms() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
