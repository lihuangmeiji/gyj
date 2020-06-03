package com.system.technologyinformation.module.fragment;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.di.AppCommonModule;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.ui.BaseFragment;
import com.system.technologyinformation.common.ui.BaseLazyFragment;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.UserFunctionListBean;
import com.system.technologyinformation.model.UserServiceFunctionListBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.UserFunctionItemViewProvider;
import com.system.technologyinformation.module.adapter.UserServiceFunctionItemViewProvider;
import com.system.technologyinformation.module.adapter.UserServiceFunctionItemViewProvider.UserServiceFunctionInterface;
import com.system.technologyinformation.module.contract.DeliveryAddressContract;
import com.system.technologyinformation.module.contract.TabCategorizeFourthContract;
import com.system.technologyinformation.module.presenter.TabCategorizeFourthPresenter;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.users.BusinessCooperationActivity;
import com.system.technologyinformation.module.ui.users.DeliveryAddressActivity;
import com.system.technologyinformation.module.ui.users.ShopUserActivity;
import com.system.technologyinformation.module.ui.users.UserAboutActivity;
import com.system.technologyinformation.module.ui.users.UserAddressActivity;
import com.system.technologyinformation.module.ui.users.UserConsumptionActivity;
import com.system.technologyinformation.module.ui.users.UserCustomerServiceActivity;
import com.system.technologyinformation.module.ui.users.UserIntegralActivity;
import com.system.technologyinformation.module.ui.users.UserInviteCodeActivity;
import com.system.technologyinformation.module.ui.users.UserMessageActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationAddActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationSuccessActivity;
import com.system.technologyinformation.module.ui.users.UserOrderActivity;
import com.system.technologyinformation.module.ui.users.UserPerfectInformationActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalAddActivity;
import com.system.technologyinformation.utils.PhotoUtilsf;
import com.system.technologyinformation.widget.CircleImageView;
import com.system.technologyinformation.widget.RecycleViewDivider;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TabCategorizeFourthFragment extends BaseLazyFragment<TabCategorizeFourthPresenter> implements TabCategorizeFourthContract.View, UserFunctionItemViewProvider.UserFunctionInterface, UserServiceFunctionInterface {
    @BindView(R.id.iv_userico)
    CircleImageView ivUserico;
    @BindView(R.id.tv_log_reg)
    TextView tvLogReg;
    @BindView(R.id.tv_log_reg_ts)
    TextView tvLogRegTs;
    @BindView(R.id.recycler_view_user)
    RecyclerView recyclerViewUser;

    List<UserFunctionListBean.UserFunctionBean> userFunctionBeanList;
    List<UserServiceFunctionListBean.UserServiceFunctionBean> userServiceFunctionBeansList;
    private Items items;
    private MultiTypeAdapter adapter;
    private Intent intent;


    @Override
    protected void loadLazyData() {
        userInfo(1);
    }

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        addUpdateInfoReceiver();
        userFunctionBeanList = new ArrayList<>();
        addUserFunction("我的积分", R.mipmap.userintegral);
        addUserFunction("我的消费", R.mipmap.userconsumption);
        addUserFunction("我的订单", R.mipmap.user_order);
        userServiceFunctionBeansList = new ArrayList<>();
        //addUserServiceFunction("我的心愿单", R.mipmap.user_service_function1);
        items = new Items();
        adapter = new MultiTypeAdapter(items);

        UserFunctionItemViewProvider userFunctionItemViewProvider = new UserFunctionItemViewProvider();
        userFunctionItemViewProvider.setUserFunctionInterface(this);
        adapter.register(UserFunctionListBean.class, userFunctionItemViewProvider);

        UserServiceFunctionItemViewProvider userServiceFunctionItemViewProvider = new UserServiceFunctionItemViewProvider();
        userServiceFunctionItemViewProvider.setUserServiceFunctionInterface(this);
        adapter.register(UserServiceFunctionListBean.class, userServiceFunctionItemViewProvider);

        UserFunctionListBean userFunctionListBean = new UserFunctionListBean();
        userFunctionListBean.setUserFunctionBeanList(userFunctionBeanList);
        items.add(userFunctionListBean);

        UserServiceFunctionListBean userServiceFunctionListBean = new UserServiceFunctionListBean();
        userServiceFunctionListBean.setUserFunctionBeanList(userServiceFunctionBeansList);
        items.add(userServiceFunctionListBean);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUser.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.color39)));
        recyclerViewUser.setAdapter(adapter);
        userInfo(1);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_tab_categorize_fourth;
    }

    private void addUserFunction(String name, int ico) {
        UserFunctionListBean.UserFunctionBean userFunctionBean = new UserFunctionListBean.UserFunctionBean();
        userFunctionBean.setUf_ico(ico);
        userFunctionBean.setUf_name(name);
        userFunctionBeanList.add(userFunctionBean);
    }

    private void addUserServiceFunction(String name, int ico) {
        UserServiceFunctionListBean.UserServiceFunctionBean userFunctionBean = new UserServiceFunctionListBean.UserServiceFunctionBean();
        userFunctionBean.setUf_ico(ico);
        userFunctionBean.setUf_name(name);
        userServiceFunctionBeansList.add(userFunctionBean);
    }


    public void userInfo(int cztype) {
        loadUserInfo();
        if (login != null) {
            if (login.getIcon() != null) {
                Glide.with(getContext()).load(login.getIcon()).error(R.mipmap.userphotomr).into(ivUserico);
            } else {
                ivUserico.setImageResource(R.mipmap.userphotomr);
            }
            tvLogReg.setText(login.getNickName());
            tvLogRegTs.setVisibility(View.GONE);
            if (!EmptyUtils.isEmpty(login.getShopUser())) {
                userServiceFunctionBeansList.clear();
                addUserServiceFunction("收货地址", R.mipmap.user_service_function2);
                addUserServiceFunction("工单管理", R.mipmap.user_service_function3);
                addUserServiceFunction("客服热线", R.mipmap.user_service_function4);
                addUserServiceFunction("邀请码", R.mipmap.user_service_function7);
                addUserServiceFunction("商务合作", R.mipmap.user_service_function8);
                addUserServiceFunction("我是商家", R.mipmap.user_service_function9);
                addUserServiceFunction("关于工友家", R.mipmap.user_service_function5);
                adapter.notifyDataSetChanged();
            } else {
                userServiceFunctionBeansList.clear();
                addUserServiceFunction("收货地址", R.mipmap.user_service_function2);
                addUserServiceFunction("工单管理", R.mipmap.user_service_function3);
                addUserServiceFunction("客服热线", R.mipmap.user_service_function4);
                addUserServiceFunction("邀请码", R.mipmap.user_service_function7);
                addUserServiceFunction("商务合作", R.mipmap.user_service_function8);
                addUserServiceFunction("关于工友家", R.mipmap.user_service_function5);
                adapter.notifyDataSetChanged();
            }
            if (cztype == 1) {
                getPresenter().getUpdateUserInfoResult(login.getId());
            }
        } else {
            ivUserico.setImageResource(R.mipmap.userphotomr);
            tvLogReg.setText("登录/注册");
            tvLogRegTs.setVisibility(View.VISIBLE);
            userServiceFunctionBeansList.clear();
            addUserServiceFunction("收货地址", R.mipmap.user_service_function2);
            addUserServiceFunction("工单管理", R.mipmap.user_service_function3);
            addUserServiceFunction("客服热线", R.mipmap.user_service_function4);
            addUserServiceFunction("邀请码", R.mipmap.user_service_function7);
            addUserServiceFunction("商务合作", R.mipmap.user_service_function8);
            addUserServiceFunction("关于工友家", R.mipmap.user_service_function5);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.lin_login
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_login:
                if (login != null) {
                    intent = new Intent(getContext(), UserPerfectInformationActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    openLogin();
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
                clearUserInfo();
                userInfo(2);
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
    public void toast(String msg) {
        super.toast(msg);
    }

    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            userInfo(2);
        } else {
            clearUserInfo();
            userInfo(2);
        }
    }

    @Override
    public void setUserLogoutResult(String str) {
        toast("退出成功");
        clearUserInfo();
        loadUserInfo();
        userInfo(2);
    }

    @Override
    public void setUserSalarytResult(String str) {
        hiddenLoading();
        toast("保存成功");
        getPresenter().getUpdateUserInfoResult(login.getId());
    }

    @Override
    public void setUpdateUserIcoResult(String str) {
        toast("更新成功");
        getPresenter().getUpdateUserInfoResult(login.getId());

    }

    @Override
    public void setFileUploadResult(String str) {
        getPresenter().getUpdateUserIcoResult(str);
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            Intent intentuserupdate = new Intent("userupdate");
            getActivity().sendBroadcast(intentuserupdate);
            userInfo(2);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            userInfo(2);
        }
        if (requestCode == 2 && resultCode == 1) {
            userInfo(1);
        }
    }

    BroadcastReceiver broadcastReceiver;

    private void addUpdateInfoReceiver() {
        broadcastReceiver = new BroadcastReceiver()

        {
            @Override
            public void onReceive(Context context, Intent intent) {
                userInfo(2);
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
    public void setOnClickListener(int index) {
        if (login != null) {
            if (index == 0) {
                intent = new Intent(getContext(), UserIntegralActivity.class);
                startActivity(intent);
            } else if (index == 1) {
                intent = new Intent(getContext(), UserConsumptionActivity.class);
                startActivity(intent);
            } else if (index == 2) {
                intent = new Intent(getContext(), UserOrderActivity.class);
                startActivity(intent);
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setServiceFunctionOnClickListener(UserServiceFunctionListBean.UserServiceFunctionBean userServiceFunctionBean) {
        if (login != null) {
            if (userServiceFunctionBean.getUf_name().trim().equals("收货地址")) {
                intent = new Intent(getContext(), DeliveryAddressActivity.class);
                startActivity(intent);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("工单管理")) {
                intent = new Intent(getContext(), QuestionDetailesActivity.class);
                intent.putExtra("title", "工单管理");
                //gd/index.html#/manage
                intent.putExtra("url", AppCommonModule.API_BASE_URL + "gd/index.html#/manage?userId=" + login.getId());
                startActivity(intent);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("客服热线")) {
                intent = new Intent(getContext(), UserCustomerServiceActivity.class);
                startActivity(intent);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("邀请码")) {
                intent = new Intent(getContext(), UserInviteCodeActivity.class);
                startActivity(intent);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("关于工友家")) {
                intent = new Intent(getContext(), UserAboutActivity.class);
                startActivityForResult(intent, 2);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("商务合作")) {
                intent = new Intent(getContext(), BusinessCooperationActivity.class);
                startActivityForResult(intent, 2);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("我是商家")) {
                intent = new Intent(getContext(), ShopUserActivity.class);
                intent.putExtra("url", AppCommonModule.API_BASE_URL + "shopowner-v2/index.html");
                startActivityForResult(intent, 2);
            }
        } else {
            if (userServiceFunctionBean.getUf_name().trim().equals("客服热线")) {
                intent = new Intent(getContext(), UserCustomerServiceActivity.class);
                startActivity(intent);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("关于工友家")) {
                intent = new Intent(getContext(), UserAboutActivity.class);
                startActivityForResult(intent, 2);
            } else if (userServiceFunctionBean.getUf_name().trim().equals("商务合作")) {
                intent = new Intent(getContext(), BusinessCooperationActivity.class);
                startActivityForResult(intent, 2);
            } else {
                openLogin();
            }
        }
    }
}
