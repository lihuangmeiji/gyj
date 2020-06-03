package com.system.technologyinformation.module.ui.users;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.IdentificationInfosGListBean;
import com.system.technologyinformation.model.IdentificationInfosPListBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.adapter.IdentificationContentGItemViewProvider;
import com.system.technologyinformation.module.adapter.IdentificationContentPItemViewProvider;
import com.system.technologyinformation.module.adapter.IdentificationInfosTitleItemViewProvider;
import com.system.technologyinformation.module.contract.UserProfessionalContract;
import com.system.technologyinformation.module.presenter.UserProfessionalPresenter;
import com.system.technologyinformation.utils.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class UserProfessionalActivity extends BaseActivity<UserProfessionalPresenter> implements UserProfessionalContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.rv_professional)
    RecyclerView rv_professional;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    List<IdentificationInfosBean> identificationInfosBeanList;
    List<IdentificationInfosBean> identificationInfosBeanList1;


    private Items items;
    private MultiTypeAdapter adapter;
    int operation = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_professional;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        ActivityCollector.addActivity(this);
        commonTheme();
        toolbarTitle.setText("选择工种");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setText("保存");
        iv_right.setTextColor(getBaseContext().getResources().getColor(R.color.color37));
        identificationInfosBeanList = new ArrayList<>();
        identificationInfosBeanList1 = new ArrayList<>();
        items = new Items();
        adapter = new MultiTypeAdapter(items);

        adapter.register(String.class, new IdentificationInfosTitleItemViewProvider());
        adapter.register(IdentificationInfosPListBean.class, new IdentificationContentPItemViewProvider());
        adapter.register(IdentificationInfosGListBean.class, new IdentificationContentGItemViewProvider());

        items.add("普通人员");

        IdentificationInfosPListBean identificationInfosPListBean = new IdentificationInfosPListBean();
        identificationInfosPListBean.setIdentificationInfosBeanList(identificationInfosBeanList);
        items.add(identificationInfosPListBean);

        items.add("管理人员");

        IdentificationInfosGListBean identificationInfosGListBean = new IdentificationInfosGListBean();
        identificationInfosGListBean.setIdentificationInfosBeanList(identificationInfosBeanList1);
        items.add(identificationInfosGListBean);

        rv_professional.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rv_professional.setAdapter(adapter);
        operation = 1;
        getPresenter().getProfessionalListResult();
    }

    @OnClick({R.id.toolbar,
            R.id.iv_right
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.iv_right:
                StringBuffer s = new StringBuffer();
                for (int i = 0; i < identificationInfosBeanList.size(); i++) {
                    if (identificationInfosBeanList.get(i).isUse()) {
                        s.append(identificationInfosBeanList.get(i).getId() + ",");
                    }
                }
                for (int i = 0; i < identificationInfosBeanList1.size(); i++) {
                    if (identificationInfosBeanList1.get(i).isUse()) {
                        s.append(identificationInfosBeanList1.get(i).getId() + ",");
                    }
                }
                if (EmptyUtils.isEmpty(s.toString())) {
                    toast("请先选择工种，在做保存！");
                    return;
                }
                showLoading();
                Log.i("ckIds", "onViewClicked: " + s.toString().substring(0, s.toString().lastIndexOf(",")));
                getPresenter().addProfessionalResult(s.toString().substring(0, s.toString().lastIndexOf(",")));
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
    public void toast(String msg) {
        super.toast(msg);
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
        } else if (code == 405) {
            rv_professional.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getProfessionalListResult();
                }
            });
        }else if(code==-10){
            return;
        } else {
            rv_professional.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
        toast(msg);
    }


    @Override
    public void setProfessionalListResult(List<IdentificationInfosBean> professionalTypeListResult) {
        if (professionalTypeListResult != null && professionalTypeListResult.size() > 0) {
            for (int i = 0; i < professionalTypeListResult.size(); i++) {
                if (professionalTypeListResult.get(i).getType() == 0) {
                    identificationInfosBeanList.add(professionalTypeListResult.get(i));
                } else {
                    identificationInfosBeanList1.add(professionalTypeListResult.get(i));
                }
            }
            adapter.notifyDataSetChanged();
            rv_professional.setVisibility(View.VISIBLE);
            vs_showerror.setVisibility(View.GONE);
        } else {
            rv_professional.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getProfessionalResult(String str) {
        hiddenLoading();
        toast("添加成功");
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            if (operation == 1) {
                getPresenter().getProfessionalListResult();
            } else {
                toast("网络异常，请重新提交!");
            }
        } else {
            openLogin();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        operation = 0;
    }
}
