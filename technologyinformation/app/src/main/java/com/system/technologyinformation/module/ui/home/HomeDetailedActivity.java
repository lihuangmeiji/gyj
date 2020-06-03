package com.system.technologyinformation.module.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;



import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.FeedbacksBean;
import com.system.technologyinformation.model.HomeDetailedBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.VoteBean;
import com.system.technologyinformation.model.VoteOptionListBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.FeedBacksAdapter;
import com.system.technologyinformation.module.adapter.Vote1Adapter;
import com.system.technologyinformation.module.adapter.VoteAdapter;
import com.system.technologyinformation.module.contract.HomeDetailedContract;
import com.system.technologyinformation.module.presenter.HomeDetailedPresenter;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.widget.CircleImageView;
import com.system.technologyinformation.widget.GridDividerItemDecoration;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class HomeDetailedActivity extends BaseActivity<HomeDetailedPresenter> implements HomeDetailedContract.View, VoteAdapter.CheckInterface, Vote1Adapter.CheckInterface {

    @BindView(R.id.status_bar_view)
    View status_bar_view;
    @BindView(R.id.tv_close)
    ImageView tv_close;
    @BindView(R.id.tv_info_share_announcer)
    TextView tv_info_share_announcer;
    @BindView(R.id.tv_home_det_title)
    TextView tv_home_det_title;
    @BindView(R.id.iv_home_det_userico)
    CircleImageView iv_home_det_userico;
    @BindView(R.id.tv_home_det_username)
    TextView tv_home_det_username;
    @BindView(R.id.tv_home_det_reltime)
    TextView tv_home_det_reltime;
    @BindView(R.id.tv_home_det_content)
    WebView tv_home_det_content;
    @BindView(R.id.recycler_view_feedbacks)
    RecyclerView recycler_view_feedbacks;
    @BindView(R.id.recycler_view_vote)
    RecyclerView recycler_view_vote;
    @BindView(R.id.tv_vote)
    TextView tv_vote;
    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard videoplayer;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.rel_content)
    RelativeLayout rel_content;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;

    @BindView(R.id.nsv_content)
    NestedScrollView nsvContent;
    int homeDetailedId = 0;

    FeedBacksAdapter feedBacksAdapter;
    VoteAdapter voteAdapter;
    Vote1Adapter vote1Adapter;
    List<VoteOptionListBean> voteOptionListBeanList;
    private List<Integer> selectVoteId;
    private VoteBean voteBean;

    Intent intent;
    final int PLAY_VIDEO = 11;

    UMImage urlImage;
    UMWeb web;
    String title = "分享标题";
    String sharUrl = "";

    int isUpdate = 0;
    GridLayoutManager gridLayoutManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_home_detailed;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        voteOptionListBeanList = new ArrayList<>();
        selectVoteId = new ArrayList<>();
        homeDetailedId = getIntent().getIntExtra("homeDetailedId", 0);
        showLoading();
        getPresenter().getHomeDetailedResult(homeDetailedId, true);
        WebSettings ws = tv_home_det_content.getSettings();
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setBuiltInZoomControls(false);//  隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//  排版适应屏幕
        ws.setUseWideViewPort(true);//
        ws.setLoadWithOverviewMode(true);//  setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);//  保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setLoadsImagesAutomatically(true);//支持自动加载图片
        ws.setGeolocationEnabled(true);//  启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");//  设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        // ws.setTextSize(WebSettings.TextSize.SMALLER);
        tv_home_det_content.setVerticalScrollBarEnabled(false);
        tv_home_det_content.setVerticalScrollbarOverlay(false);
        tv_home_det_content.setHorizontalScrollBarEnabled(false);
        tv_home_det_content.setHorizontalScrollbarOverlay(false);
        tv_home_det_content.setBackgroundColor(0); // 设置背景色
        tv_home_det_content.addJavascriptInterface(this, "App");
        tv_home_det_content.setWebChromeClient(new WebChromeClient());
        tv_home_det_content.setWebViewClient(new xWebViewClientent());
        feedBacksAdapter = new FeedBacksAdapter(R.layout.item_feedbacks);
        gridLayoutManager = new GridLayoutManager(getBaseContext(), 2);
        recycler_view_feedbacks.setLayoutManager(gridLayoutManager);
        recycler_view_feedbacks.setAdapter(feedBacksAdapter);
        feedBacksAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (login != null) {
                    FeedbacksBean feedbacksBean = feedBacksAdapter.getItem(position);
                    getPresenter().getHomeDetailedAddFeedbackResult(homeDetailedId, feedbacksBean.getId(), position);
                } else {
                    intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });

        voteAdapter = new VoteAdapter(R.layout.item_vote);
        voteAdapter.setCheckInterface(this);


        vote1Adapter = new Vote1Adapter(R.layout.item_vote1);
        vote1Adapter.setCheckInterface(this);

        recycler_view_vote.addItemDecoration(new GridDividerItemDecoration(10, getBaseContext().getResources().getColor(R.color.white)));
        recycler_view_vote.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recycler_view_vote.setNestedScrollingEnabled(false);//禁止滑动

    }

    @Override
    public void setHomeDetailedResult(HomeDetailedBean homeDetailedBean) {
        //hiddenLoading();
        if (homeDetailedBean != null) {
            if (isUpdate == 0) {
                title = homeDetailedBean.getTitle();
                sharUrl = homeDetailedBean.getShareLink();
                tv_home_det_title.setText(homeDetailedBean.getTitle());
                if (homeDetailedBean.getOriginIcon() != null) {
                    Glide.with(getBaseContext()).load(homeDetailedBean.getOriginIcon()).into(iv_home_det_userico);
                } else {
                    iv_home_det_userico.setImageResource(R.mipmap.userphotomr);
                }
                tv_home_det_username.setText(homeDetailedBean.getOriginName());
                tv_home_det_reltime.setText(homeDetailedBean.getCreateAt());
                String data = homeDetailedBean.getContent();
                //data = data.replaceAll("width=\"\\d+\"", "width=\"100%\"").replaceAll("height=\"\\d+\"", "height=\"auto\"");
                data = data.replaceAll("pre", "a");
                //String varjs = "<script type='text/javascript'> window.onload = function() {var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
                String html = "<html><head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\" type=\"text/css\" href=\"home1.css\" /> <link rel=\"stylesheet\" type=\"text/css\" href=\"home2.css\" /> <link rel=\"stylesheet\" type=\"text/css\" href=\"c.css\" /> </head> <body> <div class='edit_wrap'>" + data + " </div></body></html>";
                tv_home_det_content.loadDataWithBaseURL("file:///android_asset/", html, "text/html; charset=UTF-8", null, null);
                //tv_home_det_content.loadUrl("http://gyj-api.idougong.com/consultation/share.html?id="+homeDetailedBean.getId()+"&from=groupmessage&isappinstalled=0");
                voteBean = homeDetailedBean.getVote();
                showVote(homeDetailedBean);
                if (homeDetailedBean.getVideoUrl() != null && !homeDetailedBean.getVideoUrl().equals("")) {
                    videoplayer.setVisibility(View.VISIBLE);
                    iv_close.setVisibility(View.VISIBLE);
                    tv_close.setVisibility(View.GONE);
                    status_bar_view.setVisibility(View.GONE);
                    videoplayer.setUp(homeDetailedBean.getVideoUrl()
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, homeDetailedBean.getTitle());
                    /*List<String> homeImg = homeDetailedBean.getImgList();
                    if (homeImg != null && homeImg.size() > 0) {*/
                    Glide.with(HomeDetailedActivity.this).load(homeDetailedBean.getVideoImg()).into(videoplayer.thumbImageView);
                    /*  }*/
                    //JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                    videoplayer.loadingProgressBar.setVisibility(View.GONE);
                    videoplayer.backButton.setVisibility(View.GONE);
                    videoplayer.bottomProgressBar.setVisibility(View.GONE);
                    videoplayer.bottomProgressBar.setAlpha(0);
                    videoplayer.currentTimeTextView.setVisibility(View.GONE);
                    videoplayer.fullscreenButton.setVisibility(View.VISIBLE);
                    videoplayer.bottomContainer.setVisibility(View.GONE);
                } else {
                    videoplayer.setVisibility(View.GONE);
                    iv_close.setVisibility(View.GONE);
                    tv_close.setVisibility(View.VISIBLE);
                    status_bar_view.setVisibility(View.VISIBLE);
                }
                feedBacksAdapter.setSelfFeedBackId(homeDetailedBean.getSelfFeedBackId());
                feedBacksAdapter.addData(homeDetailedBean.getFeedbacks());
                feedBacksAdapter.notifyDataSetChanged();
            } else if (isUpdate == 1) {
                isUpdate = 0;
                feedBacksAdapter.getData().clear();
                feedBacksAdapter.setSelfFeedBackId(homeDetailedBean.getSelfFeedBackId());
                feedBacksAdapter.addData(homeDetailedBean.getFeedbacks());
                feedBacksAdapter.notifyDataSetChanged();
            } else if (isUpdate == 2) {
                isUpdate = 0;
                voteOptionListBeanList.clear();
                voteAdapter.getData().clear();
                voteAdapter.notifyDataSetChanged();
                voteBean = homeDetailedBean.getVote();
                showVote(homeDetailedBean);
            }
            rel_content.setVisibility(View.VISIBLE);
            vs_showerror.setVisibility(View.GONE);
        } else {
            rel_content.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void setHomeDetailedAddVoteResult(String str) {
        toast("投票成功");
        isUpdate = 2;
        getPresenter().getHomeDetailedResult(homeDetailedId, false);

    }

    @Override
    public void setHomeDetailedAddFeedbackResult(String str, int position) {
        //getPresenter().getHomeDetailedUpdateFeedbackResult(homeDetailedId);
        isUpdate = 1;
        getPresenter().getHomeDetailedResult(homeDetailedId, false);
    }

    @Override
    public void setHomeDetailedAddForwardResult(String str) {
        toast("分享成功");
    }

    @Override
    public void setHomeDetailedUpdateFeedbackResult(List<FeedbacksBean> feedbacksBeanList) {
    }


    private void showVote(HomeDetailedBean homeDetailedBean) {
        if (voteBean != null) {
            boolean isShow;
            if (homeDetailedBean.getSelfVote() != null && homeDetailedBean.getSelfVote().size() > 0) {
                isShow = true;
            } else {
                isShow = false;
            }
            int sunnum = 0;
            if (voteBean.getVoteOptionList() != null && voteBean.getVoteOptionList().size() > 0) {
                for (int i = 0; i < voteBean.getVoteOptionList().size(); i++) {
                    VoteOptionListBean voteOptionListBean = voteBean.getVoteOptionList().get(i);
                    if (isShow && homeDetailedBean.getSelfVote().toString().contains(String.valueOf(voteOptionListBean.getId()))) {
                        voteOptionListBean.setChoosed(true);
                    } else {
                        voteOptionListBean.setChoosed(false);
                    }
                    voteOptionListBeanList.add(voteOptionListBean);
                    sunnum = sunnum + voteOptionListBean.getNum();
                }
            }
            if (homeDetailedBean.getVote().getNum() > 1) {
                recycler_view_vote.setAdapter(vote1Adapter);
                vote1Adapter.setShow(isShow);
                vote1Adapter.setSunnum(sunnum);
                vote1Adapter.addData(voteOptionListBeanList);
            } else {
                recycler_view_vote.setAdapter(voteAdapter);
                voteAdapter.setShow(isShow);
                voteAdapter.setSunnum(sunnum);
                voteAdapter.addData(voteOptionListBeanList);
            }
            tv_vote.setVisibility(View.VISIBLE);
        } else {
            tv_vote.setVisibility(View.GONE);
        }
    }

    public static String listToString(List<Integer> list) {

        if (list == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        //第一个前面不拼接","
        for (Integer integer : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(integer);
        }
        return result.toString();
    }

    @OnClick({R.id.tv_close,
            R.id.tv_vote,
            R.id.iv_close,
            R.id.tv_home_det_share,
            R.id.iv_info_share
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_close:
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_vote:
                if (login != null) {
                    if (selectVoteId == null || selectVoteId.size() == 0) {
                        toast("请选择投票内容");
                        return;
                    }
                    if (voteBean == null) {
                        toast("暂无投票选项");
                        return;
                    }
                    String voteIdArray = null;
                    for (int i = 0; i < selectVoteId.size(); i++) {
                        if (i == 0) {
                            voteIdArray = selectVoteId.get(i) + "";
                        } else {
                            voteIdArray = voteIdArray + "," + selectVoteId.get(i);
                        }
                    }
                    getPresenter().getHomeDetailedAddVoteResult(voteIdArray, voteBean.getId());
                } else {
                    intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_home_det_share:
            case R.id.iv_info_share:
                new ShareView(getBaseContext(), tv_home_det_content);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
            openLogin();
            return;
        } else if (code == 405) {
            rel_content.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getHomeDetailedResult(homeDetailedId, true);
                }
            });
        } else if (code == -10) {
            return;
        } else {
            rel_content.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
        toast(msg);
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        if (isChecked == true) {
            selectVoteId.add(groupPosition);
        } else {
            selectVoteId.remove(Integer.valueOf(groupPosition));
        }
    }

    @Override
    public void dcheckGroup(int groupPosition, boolean isChecked) {
        for (int i = 0; i < voteOptionListBeanList.size(); i++) {
            VoteOptionListBean voteOptionListBean = voteOptionListBeanList.get(i);
            if (voteOptionListBean.getId() == groupPosition) {
                voteOptionListBean.setChoosed(isChecked);
            } else {
                voteOptionListBean.setChoosed(false);
            }
        }
        voteAdapter.getData().clear();
        voteAdapter.notifyDataSetChanged();
        voteAdapter.addData(voteOptionListBeanList);
        selectVoteId.clear();
        if (isChecked == true) {
            selectVoteId.add(groupPosition);
        }
    }

    public class xWebViewClientent extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if (url == null)
                return false;
            try {
                if (url.startsWith("upwrp://")
                    //其他自定义的scheme
                        ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else if (url.contains("callback.action?transNumber")) {
                    finish();
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return false;
            }

            //处理http和https开头的url
            tv_home_det_content.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 加载完成
            hiddenLoading();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
            // 加载开始
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    //Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    //Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    //Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    //Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    //Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    //Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    //Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    //Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    //Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    //Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    //Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    //Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    //Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    //Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    //Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }


    public class ShareView extends PopupWindow {

        public ShareView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.user_share, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setWidth(RelativeLayout.LayoutParams.FILL_PARENT);
            setHeight(RelativeLayout.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            urlImage = new UMImage(getBaseContext(), R.mipmap.ic_launcher);
            web = new UMWeb(sharUrl + "?id=" + homeDetailedId);
            web.setTitle(getBaseContext().getResources().getString(R.string.app_name));//标题
            web.setThumb(urlImage);  //缩略图
            web.setDescription(title);//描述
            LinearLayout wxlin = (LinearLayout) view.findViewById(R.id.wxlin);
            LinearLayout wblin = (LinearLayout) view.findViewById(R.id.wblin);
            LinearLayout pqlin = (LinearLayout) view.findViewById(R.id.pqlin);
            LinearLayout qqlin = (LinearLayout) view.findViewById(R.id.qqlin);
            TextView bt3 = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
            wxlin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.WEIXIN);
                    dismiss();
                }
            });
            wblin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.SINA);
                    dismiss();
                }
            });
            pqlin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.WEIXIN_CIRCLE);
                    dismiss();
                }
            });
            qqlin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.QQ);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private void ShareImage(SHARE_MEDIA share_media) {

        new ShareAction(HomeDetailedActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .setCallback(umShareListener)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            toast("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            toast("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            toast("分享取消了");
        }
    };

    /*// 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS})
    void showShare(SHARE_MEDIA share_media) {

    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS})
    void showRationaleForShare(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("分享内容")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS})
    void showDeniedForShare() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS})
    void showNeverAskForShare() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults!=null&&grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
        }
        // 代理权限处理到自动生成的方法
        //MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }*/

}
