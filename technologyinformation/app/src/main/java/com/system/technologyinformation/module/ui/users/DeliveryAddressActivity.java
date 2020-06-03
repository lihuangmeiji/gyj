package com.system.technologyinformation.module.ui.users;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.DeliveryAddressBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.system.technologyinformation.module.contract.DeliveryAddressContract;
import com.system.technologyinformation.module.presenter.DeliveryAddressPresenter;
import com.system.technologyinformation.utils.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.GET;

public class DeliveryAddressActivity extends BaseActivity<DeliveryAddressPresenter> implements DeliveryAddressContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView ivRight;

    @BindView(R.id.tv_user_da_add)
    TextView tvUserDaAdd;
    @BindView(R.id.et_user_da_add_det)
    EditText etUserDaAddDet;
    @BindView(R.id.et_user_da_phone)
    EditText etUserDaPhone;
    @BindView(R.id.et_user_da_name)
    EditText etUserDaName;
    int operation = 0;
    private OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    Integer areaCode = null;
    int daId = 0;
    int selectId1 = 0;
    int selectId2 = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_delivery_address;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("收货地址");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setText("保存");
        ivRight.setTextColor(getResources().getColor(R.color.color37));
        operation = 1;
        getPresenter().getUserDeliveryAddressResult();
        getPresenter().getProvinceOrCityInfoResult();
    }

    @Override
    public void addUserDeliveryAddressResult(String str) {
        toast("添加成功！");
    }

    @Override
    public void updateUserDeliveryAddressResult(String str) {
        toast("修改成功！");
    }

    @Override
    public void setUserDeliveryAddressResult(List<DeliveryAddressBean> userDeliveryAddressResult) {
        if (userDeliveryAddressResult != null && userDeliveryAddressResult.size() > 0) {
            tvUserDaAdd.setText(userDeliveryAddressResult.get(0).getCityInfo());
            etUserDaAddDet.setText(userDeliveryAddressResult.get(0).getAddress());
            etUserDaPhone.setText(userDeliveryAddressResult.get(0).getPhone());
            etUserDaName.setText(userDeliveryAddressResult.get(0).getName());
            daId = userDeliveryAddressResult.get(0).getId();
            areaCode = userDeliveryAddressResult.get(0).getAreaCode();
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            if (operation == 1) {
                getPresenter().getUserDeliveryAddressResult();
                getPresenter().getProvinceOrCityInfoResult();
            } else {
                toast("服务器未响应，请重新操作！");
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setProvinceOrCityInfoResult(List<ProvinceBean> provinceBeanList) {
        if (provinceBeanList != null) {
            options1Items.addAll(provinceBeanList);
            for (int i = 0; i < provinceBeanList.size(); i++) {//遍历省份
                ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                if (provinceBeanList.get(i).getProvinceAreas() == null || provinceBeanList.get(i).getProvinceAreas().size() == 0) {
                    String cityName = "";
                    cityList.add(cityName);//添加城市
                } else {
                    for (int c = 0; c < provinceBeanList.get(i).getProvinceAreas().size(); c++) {//遍历该省份的所有城市
                        String cityName = provinceBeanList.get(i).getProvinceAreas().get(c).getNetName();
                        if (!EmptyUtils.isEmpty(tvUserDaAdd.getText().toString())) {
                            if(tvUserDaAdd.getText().toString().contains(cityName)){
                                selectId1 = i;
                                selectId2 = c;
                            }
                        }else{
                            if(cityName.contains("杭州市")){
                                selectId1 = i;
                                selectId2 = c;
                            }
                        }
                        cityList.add(cityName);//添加城市
                    }
                }
                /**
                 * 添加城市数据
                 */
                options2Items.add(cityList);
            }

            initOptionPicker();
        }
    }

    private void initOptionPicker() {//条件选择器初始化
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置  + options2Items.get(options1).get(options2)
                if (options1Items.get(options1).getProvinceAreas() == null || options1Items.get(options1).getProvinceAreas().size() == 0) {
                    areaCode = options1Items.get(options1).getAreaCode();
                    tvUserDaAdd.setText(options1Items.get(options1).getNetName() + " " + options1Items.get(options1).getNetName());
                } else {
                    areaCode = options1Items.get(options1).getProvinceAreas().get(options2).getAreaCode();
                    tvUserDaAdd.setText(options1Items.get(options1).getNetName() + " " + options2Items.get(options1).get(options2));
                }
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setCancelColor(Color.GRAY)
                .setSubmitColor(Color.GRAY)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setSelectOptions(selectId1, selectId2)
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .setBackgroundId(0xb0000000) //设置外部遮罩颜色 0x00000000
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    @OnClick({R.id.toolbar,
            R.id.tv_user_da_add,
            R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_user_da_add:
                hideInput();
                if (pvOptions != null) {
                    pvOptions.show();
                }
                break;
            case R.id.iv_right:
                operation = 2;
                if (daId == 0) {
                    if (EmptyUtils.isEmpty(etUserDaAddDet.getText().toString().trim())) {
                        toast("请输入详细地址");
                        return;
                    }
                    if (EmptyUtils.isEmpty(etUserDaPhone.getText().toString().trim())) {
                        toast("请输入手机号");
                        return;
                    }
                    if (!isMobile(etUserDaPhone.getText().toString().trim())) {
                        toast("手机号格式不正确");
                        return;
                    }
                    if (EmptyUtils.isEmpty(etUserDaName.getText().toString().trim())) {
                        toast("请输入姓名");
                        return;
                    }
                    if (EmptyUtils.isEmpty(tvUserDaAdd.getText().toString().trim())) {
                        toast("请选择省市信息");
                        return;
                    }
                    getPresenter().addUserDeliveryAddress(etUserDaName.getText().toString().trim(), etUserDaPhone.getText().toString().trim(), areaCode, etUserDaAddDet.getText().toString().trim());
                } else {
                    getPresenter().updateUserDeliveryAddress(daId, etUserDaName.getText().toString().trim(), etUserDaPhone.getText().toString().trim(), areaCode, etUserDaAddDet.getText().toString().trim());
                }
                break;
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
        }else if(code==-10){
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
    public void toast(String msg) {
        super.toast(msg);
    }
}
