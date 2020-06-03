package com.system.technologyinformation.module.ui.account;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.RegisterContract;
import com.system.technologyinformation.module.presenter.RegisterPresenter;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.et_phone)
    ClearEditText et_phone;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.et_code)
    ClearEditText et_code;
    @BindView(R.id.et_pwd)
    ClearEditText et_pwd;
    @BindView(R.id.tv_register)
    TextView tv_register;

    private int countDownTime = 60;
    private static final int COUNT_DOWN = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        Observable<CharSequence> phoneObservable = RxTextView.textChanges(et_phone);
        Observable<CharSequence> codeObservable = RxTextView.textChanges(et_code);
        Observable<CharSequence> pwdObservable = RxTextView.textChanges(et_pwd);
        Observable.combineLatest(phoneObservable, codeObservable,pwdObservable, new Function3<CharSequence, CharSequence,CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2,@NonNull CharSequence charSequence3) throws Exception {
                return TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(charSequence2)||TextUtils.isEmpty(charSequence3);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                tv_register.setEnabled(!aBoolean);
                tv_register.setSelected(!aBoolean);
            }
        });


        Observable.combineLatest(phoneObservable, phoneObservable, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2) throws Exception {
                return TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(charSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                tv_get_code.setEnabled(!aBoolean);
            }
        });
    }

    @OnClick({R.id.tv_register,R.id.tv_get_code,R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                getPresenter().registerUserInfo(et_phone.getText().toString().trim(),et_pwd.getText().toString(),et_code.getText().toString().trim());
                break;
            case R.id.tv_get_code:
                getPresenter().registerCheckPhone(et_phone.getText().toString().trim());
                break;
            case R.id.tv_close:
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void registerUserInfoResult(BaseResponseInfo baseResponseInfo) {
        if(baseResponseInfo.getCode()==0){
            ToastUtils.showLongToast("注册成功");
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(baseResponseInfo.getData()));
            Intent intent = new Intent("userupdate");
            sendBroadcast(intent);
            finish();
        }else{
            ToastUtils.showLongToast(baseResponseInfo.getDetail());
        }
    }


    @Override
    public void registerUserInfoSmsResult(BaseResponseInfo baseResponseInfo) {
        if(baseResponseInfo.getCode()==0){
            ToastUtils.showLongToast("发送成功");
            countDownTime = 60;//下次点击时倒计时重新置为原倒计时数
            setCountDown(tv_get_code, COUNT_DOWN);
        }else{
            ToastUtils.showLongToast(baseResponseInfo.getDetail());
        }
    }

    @Override
    public void registerCheckPhoneResult(BaseResponseInfo baseResponseInfo) {
        if(baseResponseInfo.getCode()==0){
            boolean cp= Boolean.valueOf(baseResponseInfo.getData().toString());
            if(cp){
                getPresenter().registerUserInfoSms(et_phone.getText().toString().trim(),"1");
            }else{
                toast("该手机号已注册");
            }
        }else{
            toast(baseResponseInfo.getDetail());
        }
    }

    /**
     * 设置倒计时
     */
    private void setCountDown(TextView tv, int code) {
        tv.setEnabled(false);
        if (code == COUNT_DOWN) {
            tv.setText(countDownTime + "s");
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
            if (!TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                tv_get_code.setEnabled(true);
            } else {
                tv_get_code.setEnabled(false);
            }
            tv.setText("重新发送");
        } else {
            mHandler.removeMessages(msgCode);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNT_DOWN:
                    handleCountMessage(countDownTime, COUNT_DOWN, tv_get_code);
                    break;
            }
        }
    };


    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hiddenLoading() {
        super.hiddenLoading();
    }

    @Override
    public void showErrorMsg(String msg,int code) {
        super.showErrorMsg(msg,code);
        toast(msg);
    }
}
