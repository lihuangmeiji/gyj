package com.system.technologyinformation.module.ui.users;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.UserMessage;
import com.system.technologyinformation.module.adapter.UserIntegralAdapter;
import com.system.technologyinformation.module.adapter.UserMessageAdapter;
import com.system.technologyinformation.module.contract.UserMessageContract;
import com.system.technologyinformation.module.presenter.UserMessagePresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.RecycleViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserMessageActivity extends BaseActivity<UserMessagePresenter> implements UserMessageContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.recycler_view_message)
    RecyclerView recycler_view_message;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;

    int currentPage;
    int pageNumber = 10;
    UserMessageAdapter userMessageAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_message;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        isClose = true;
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("消息中心");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        userMessageAdapter = new UserMessageAdapter(R.layout.item_user_message);
        userMessageAdapter.setOnLoadMoreListener(this, recycler_view_message);
        userMessageAdapter.setEnableLoadMore(true);
        userMessageAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recycler_view_message.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        //recycler_view_message.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL));
        recycler_view_message.setAdapter(userMessageAdapter);
        /*userMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(UserMessageActivity.this, UserMessageDetailedActivity.class);
                intent.putExtra("mesContent", userMessageAdapter.getItem(position).getContent());
                startActivity(intent);
            }
        });*/
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
        recycler_view_message.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                userMessageAdapter.getData().clear();
                userMessageAdapter.notifyDataSetChanged();
                getPresenter().getUserMessageListResult(currentPage, pageNumber);
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setEnabled(false);
        recycler_view_message.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().getUserMessageListResult(currentPage, pageNumber);
            }
        }, 100);
    }


    @OnClick({R.id.toolbar,
            R.id.vs_showerror})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.vs_showerror:
                refresh();
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
        } else if (code == 405) {
            swipeLayout.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
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

    @Override
    public void setUserMessageListResult(List<UserMessage> userMessageListResult) {
        swipeLayout.setRefreshing(false);
        userMessageAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (userMessageListResult == null || userMessageListResult.size() == 0) {
            userMessageAdapter.loadMoreEnd();
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        userMessageAdapter.addData(userMessageListResult);
        userMessageAdapter.loadMoreComplete();
        currentPage++;
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
        if (userMessageListResult.size() < pageNumber) {
            userMessageAdapter.loadMoreEnd();
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
            refresh();
        } else {
            openLogin();
        }
    }
}
