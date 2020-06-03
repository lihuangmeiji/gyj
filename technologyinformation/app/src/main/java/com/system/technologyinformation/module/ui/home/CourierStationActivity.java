package com.system.technologyinformation.module.ui.home;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.CourierStationBean;
import com.system.technologyinformation.module.adapter.CourierStationAdapter;
import com.system.technologyinformation.module.adapter.UserIntegralAdapter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CourierStationActivity extends SimpleActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

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

    CourierStationAdapter courierStationAdapter;
    List<CourierStationBean> courierStationBeanList;
    @Override
    protected int getContentView() {
        return R.layout.activity_courier_station;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        courierStationBeanList=new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            CourierStationBean courierStationBean=new CourierStationBean();
            courierStationBeanList.add(courierStationBean);
        }
        toolbarTitle.setText("驿站");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        courierStationAdapter=new CourierStationAdapter(R.layout.item_courier_station);
        courierStationAdapter.setOnLoadMoreListener(this, recyclerView);
        courierStationAdapter.setEnableLoadMore(true);
        courierStationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(courierStationAdapter);
        courierStationAdapter.addData(courierStationBeanList);
        //refresh();
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

    }

    @Override
    public void onLoadMoreRequested() {

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
            openLogin();
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
}
