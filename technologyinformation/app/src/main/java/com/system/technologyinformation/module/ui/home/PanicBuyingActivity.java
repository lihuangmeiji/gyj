package com.system.technologyinformation.module.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.adapter.PanicBuyingAdapter;
import com.system.technologyinformation.module.contract.PanicBuyingContract;
import com.system.technologyinformation.module.presenter.PanicBuyingPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PanicBuyingActivity extends BaseActivity<PanicBuyingPresenter> implements PanicBuyingContract.View,BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
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
    int pageNumber =10;
    PanicBuyingAdapter panicBuyingAdapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_panic_buying;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("抢购");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color37));
        panicBuyingAdapter=new PanicBuyingAdapter(R.layout.item_panic_buying);
        panicBuyingAdapter.setOnLoadMoreListener(this, recyclerView);
        panicBuyingAdapter.setEnableLoadMore(true);
        panicBuyingAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        recyclerView.addItemDecoration(new GridDividerItemDecoration(20, getBaseContext().getResources().getColor(R.color.color39)));
        recyclerView.setAdapter(panicBuyingAdapter);
        panicBuyingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getBaseContext(), CreditsExchangeDetailedActivity.class);
                intent.putExtra("shoppingId", panicBuyingAdapter.getItem(i).getId());
                intent.putExtra("shoppingType", 1);
                startActivity(intent);
            }
        });
        getPresenter().getHomeBannerResult();
        refresh();
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            // scheme部分
            String scheme = uri.getScheme();
            // host部分
            String host = uri.getHost();
            //port部分
            int port = uri.getPort();
            // 访问路径
            String path = uri.getPath();
        }
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
                panicBuyingAdapter.getData().clear();
                panicBuyingAdapter.notifyDataSetChanged();
                getPresenter().getHomeShoppingDataResult(currentPage, pageNumber);
            }
        }, 100);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setEnabled(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().getHomeShoppingDataResult(currentPage, pageNumber);
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
    public void showErrorMsg(String msg,int code) {
        super.showErrorMsg(msg,code);
        if(code==-1){
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        }else if(code==405){
            swipeLayout.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load=(LinearLayout)findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        }else if(code==-10){
            return;
        }else{
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
    public void toast(String msg) {
        super.toast(msg);
    }

    @Override
    public void setHomeShoppingDataResult(List<HomeShoppingSpreeBean> homeShoppingDataResult) {
        swipeLayout.setRefreshing(false);
        panicBuyingAdapter.setEnableLoadMore(true);
        swipeLayout.setEnabled(true);
        if (homeShoppingDataResult == null || homeShoppingDataResult.size() == 0) {
            panicBuyingAdapter.loadMoreEnd();
            if(currentPage==1){
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            }
            return;
        }
        panicBuyingAdapter.addData(homeShoppingDataResult);
        if (homeShoppingDataResult.size() < pageNumber) {
            panicBuyingAdapter.loadMoreEnd();
            return;
        }
        panicBuyingAdapter.loadMoreComplete();
        currentPage++;
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setHomeBannerResult(List<HomeBannerBean> homeBannerBeanList) {

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
