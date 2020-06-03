package com.system.technologyinformation.module.ui.users;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.AppApplication;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.CareerInfoBean;
import com.system.technologyinformation.model.ConstructionPlaceBean;
import com.system.technologyinformation.model.JsonBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProvinceAreasBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserAddressContract;
import com.system.technologyinformation.module.presenter.UserAddressPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.GetJsonDataUtil;
import com.system.technologyinformation.utils.LocationService;
import com.system.technologyinformation.utils.SoftUpdate;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


public class UserAddressActivity extends BaseActivity<UserAddressPresenter> implements UserAddressContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_address_detail)
    TextView tv_address_detail;


    List<ConstructionPlaceBean> constructionPlaceBeanList;
    int cpId = 0;
    int operation = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_address;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("我的工地");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setText("保存");
        if (login != null && login.getConstructionPlace() != null) {
            tv_address_detail.setText(login.getConstructionPlace().getName());
        }
        constructionPlaceBeanList = new ArrayList<>();
        operation = 1;
        getPresenter().getConstructionPlaceInfoResult();
    }

    @OnClick({R.id.toolbar,
            R.id.iv_right,
            R.id.tv_location,
            R.id.tv_address_detail,
            R.id.lin_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.iv_right:
            case R.id.lin_address:
                showLoading();
                if (EmptyUtils.isEmpty(tv_address_detail.getText().toString())) {
                    toast("请选择工地");
                    return;
                }
                getPresenter().getUserAddressResult(cpId);
                break;
            case R.id.tv_location:

                break;
            case R.id.tv_address_detail:
                if (!EmptyUtils.isEmpty(constructionPlaceBeanList) && constructionPlaceBeanList.size() > 0) {
                    ShowBankName();
                } else {
                    getPresenter().getConstructionPlaceInfoResult();
                }
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
        hiddenLoading();
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
    public void setUserAddressResult(String str) {
        hiddenLoading();
        toast("认证成功");
        Intent intent = new Intent();
        setResult(1, intent);
        Intent intentuserupdate = new Intent("userupdate");
        sendBroadcast(intentuserupdate);
        finish();
    }



    @Override
    public void setProvinceOrCityInfoResult(List<ProvinceBean> provinceBeanList) {
      /*  if (provinceBeanList != null && provinceBeanList.size() > 0) {
            ArrayList<String> cityList = new ArrayList<>();
            for (int i = 2; i < provinceBeanList.size(); i++) {
                options1Items.add(provinceBeanList.get(i));
                if (provinceBeanList.get(i).getProvinceAreas() != null && provinceBeanList.get(i).getProvinceAreas().size() > 0) {
                    for (int j = 0; j < provinceBeanList.get(i).getProvinceAreas().size(); j++) {
                        cityList.add(provinceBeanList.get(i).getProvinceAreas().get(j).getNetName());
                    }
                }
            }
            options2Items.add(cityList);
            initOptionPicker();
        }*/
    }

    @Override
    public void setConstructionPlaceInfoResult(List<ConstructionPlaceBean> constructionPlaceBeanList) {
        this.constructionPlaceBeanList.addAll(constructionPlaceBeanList);
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            if (operation == 1) {
                getPresenter().getConstructionPlaceInfoResult();
            } else {
                toast("网络异常，请重新提交!");
            }
        } else {
            openLogin();
        }
    }


    private void ShowBankName() {// 弹出条件选择器
        final List<String> mOptionsItems = new ArrayList<>();
        for (int i = 0; i < constructionPlaceBeanList.size(); i++) {
            mOptionsItems.add(constructionPlaceBeanList.get(i).getName());
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //String strBankName = mOptionsItems.get(options1);
                tv_address_detail.setText(mOptionsItems.get(options1));
                cpId = constructionPlaceBeanList.get(options1).getId();
            }
        })

                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setOutSideCancelable(true)// default is true
                .setCyclic(false, false, false)
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvOptions.setPicker(mOptionsItems);//条件选择器
        pvOptions.show();
    }

    @Override
    public void toast(String msg) {
        super.toast(msg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        operation = 0;
    }
}
