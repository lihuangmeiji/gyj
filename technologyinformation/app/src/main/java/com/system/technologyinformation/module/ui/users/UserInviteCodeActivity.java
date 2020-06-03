package com.system.technologyinformation.module.ui.users;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.module.contract.UserInviteCodeContract;
import com.system.technologyinformation.module.presenter.UserInviteCodePresenter;
import com.system.technologyinformation.module.ui.home.HomeDetailedActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.UmengText;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInviteCodeActivity extends BaseActivity<UserInviteCodePresenter> implements UserInviteCodeContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView ivRight;
    @BindView(R.id.tv_ic_content)
    TextView tvIcContent;


    String content;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_invite_code;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("我的邀请码");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        ivRight.setVisibility(View.GONE);
        if (login != null) {
            tvIcContent.setText(login.getInvCode() + "");
        } else {
            openLogin();
        }
        getPresenter().getUserShareContentResult();
    }

    @OnClick({R.id.toolbar,
            R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_share:
                new ShareView(getBaseContext(), tvIcContent);
             /*   //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("shareContnet", content);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                toast("复制成功");*/
                break;
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toast(String msg) {
        super.toast(msg);
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
        }else if(code==-10){
            return;
        }
        toast(msg);
    }

    @Override
    public void setUserShareContentResult(String str) {
        content = str;
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            getPresenter().getUserShareContentResult();
        } else {
            openLogin();
        }
    }

    @Override
    public void getUserShareResult(String str) {
        toast("分享成功");
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

        if (share_media == SHARE_MEDIA.QQ) {
            UMImage image = new UMImage(getBaseContext(), R.mipmap.ic_launcher);
            UMWeb web = new UMWeb("http://gyj-app.idougong.com");
            web.setTitle("邀请下载");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(content);//描述
            new ShareAction(this)
                    .withMedia(web)//分享内容
                    .setPlatform(share_media)
                    .setCallback(umShareListener)
                    .share();
        } else {
            new ShareAction(this)
                    .withText(content)//分享内容
                    .setPlatform(share_media)
                    .setCallback(umShareListener)
                    .share();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            getPresenter().addUserShareResult();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //toast("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //toast("分享取消了");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
