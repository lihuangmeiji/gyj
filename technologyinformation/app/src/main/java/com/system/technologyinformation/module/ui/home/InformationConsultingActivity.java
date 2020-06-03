package com.system.technologyinformation.module.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.HomeFunctionListBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.adapter.HomeDataType;
import com.system.technologyinformation.module.contract.InformationConsultingContract;
import com.system.technologyinformation.module.presenter.InformationConsultingPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.RecycleViewDivider;
import com.wenld.multitypeadapter.MultiTypeAdapter;
import com.wenld.multitypeadapter.base.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;

public class InformationConsultingActivity extends BaseActivity<InformationConsultingPresenter> implements InformationConsultingContract.View, SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = "InformationConsultingActivity";

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


    List<HomeDataBean> homeDataBeanList;
    private MultiTypeAdapter adapter;
    int currentPage = 1;
    int pageNumber = 10;
    boolean isLoading;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_information_consulting;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        homeDataBeanList = new ArrayList<>();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("资讯中心");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        adapter = new MultiTypeAdapter();
        adapter.register(HomeDataBean.class, new HomeDataType(getBaseContext()));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, Object o, int i) {
                if (o instanceof HomeDataBean) {
                    if (homeDataBeanList != null && homeDataBeanList.size() > 0) {
                        HomeDataBean homeDataBean = (HomeDataBean) homeDataBeanList.get(i);
                        Intent intent = new Intent(getBaseContext(), HomeDetailedActivity.class);
                        intent.putExtra("homeDetailedId", homeDataBean.getId());
                        startActivity(intent);
                    }
                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, Object o, int i) {
                return false;
            }
        });
        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getBaseContext(), LinearLayoutManager.VERTICAL, 1, getBaseContext().getResources().getColor(R.color.color40)));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.getFirstFloor() != null) {
                    JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.getFirstFloor();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            //当RecyclerView的滑动状态改变时触发
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /**
             * 当RecyclerView滑动时触发
             * 类似点击事件的MotionEvent.ACTION_MOVE
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取可见item个数
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    //加载
                    boolean isRefreshing = swipeLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentPage++;
                                getPresenter().getHomeDataResult(currentPage, pageNumber);
                            }
                        }, 1000);
                    }
                }

            }
        });
        adapter.setItems(homeDataBeanList);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        onRefresh();
    }

    @Override
    public void setHomeDataResult(List<HomeDataBean> homeDataBeanList1) {
        swipeLayout.setRefreshing(false);
        if (homeDataBeanList1 == null || homeDataBeanList1.size() == 0) {
            if (currentPage == 1) {
                vs_showerror.setLayoutResource(R.layout.layout_empty);
                vs_showerror.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
            } else {
                toast("暂无更多数据");
            }
            return;
        }
        for (int i = 0; i < homeDataBeanList1.size(); i++) {
            HomeDataBean homeDataBean = homeDataBeanList1.get(i);
            if (homeDataBean.getVideoUrl() != null && !homeDataBean.getVideoUrl().equals("")) {
                homeDataBean.setContextType(2);
            } else if (homeDataBean.getImgList() != null && homeDataBean.getImgList().size() > 0) {
                if (homeDataBean.getImgList().size() >= 3) {
                    homeDataBean.setContextType(4);
                } else {
                    homeDataBean.setContextType(1);
                }
            } else {
                homeDataBean.setContextType(0);
            }
        }
        //homeDataBeanList.addAll(homeDataBeanList1);
        homeDataBeanList.addAll(homeDataBeanList1);
        adapter.notifyDataSetChanged();
        vs_showerror.setVisibility(View.GONE);
        swipeLayout.setVisibility(View.VISIBLE);
        isLoading = false;
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
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                homeDataBeanList.clear();
                getPresenter().getHomeDataResult(currentPage, pageNumber);
            }
        }, 100);
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
                    onRefresh();
                }
            });
        } else if (code == -10) {
            return;
        } else {
            swipeLayout.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
        toast(msg);
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
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
