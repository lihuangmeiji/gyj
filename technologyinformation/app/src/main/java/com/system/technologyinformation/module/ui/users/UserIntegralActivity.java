package com.system.technologyinformation.module.ui.users;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.module.adapter.UserIntegralAdapter;
import com.system.technologyinformation.module.contract.UserIntegralContract;
import com.system.technologyinformation.module.presenter.UserIntegralPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserIntegralActivity extends BaseActivity<UserIntegralPresenter> implements UserIntegralContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    int currentPage;
    int pageNumber = 10;
    UserIntegralAdapter userIntegralAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_integral;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("积分历史");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        userIntegralAdapter = new UserIntegralAdapter(R.layout.item_user_integral, getBaseContext());
        userIntegralAdapter.setOnLoadMoreListener(this, recyclerView);
        userIntegralAdapter.setEnableLoadMore(true);
        userIntegralAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL,1,getBaseContext().getResources().getColor(R.color.color40)));
        recyclerView.setAdapter(userIntegralAdapter);
        refresh();
    }

    private void refresh() {
        swipeLayout.setRefreshing(true);
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                userIntegralAdapter.getData().clear();
                getPresenter().getUserIntegralListResult(currentPage, pageNumber, "");
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setEnabled(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().getUserIntegralListResult(currentPage, pageNumber, "");
            }
        }, 100);
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

    @Override
    public void setUserIntegralListResult(List<UserIntegralBean> userIntegralListResult) {
        swipeLayout.setRefreshing(false);
        userIntegralAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (userIntegralListResult == null || userIntegralListResult.size() == 0) {
            userIntegralAdapter.loadMoreEnd();
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        userIntegralAdapter.addData(userIntegralListResult);
        userIntegralAdapter.notifyDataSetChanged();
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
        userIntegralAdapter.loadMoreComplete();
        currentPage++;
        if (userIntegralListResult.size() < pageNumber) {
            userIntegralAdapter.loadMoreEnd();
            return;
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            onRefresh();
        } else {
            openLogin();
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
        } else if (code == 405) {
            swipeLayout.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        }else if(code==-10){
            return;
        } else {
            swipeLayout.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
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
}
