package com.system.technologyinformation.module.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.EnrollmentBean;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.EnrollmentContract;
import com.system.technologyinformation.module.presenter.EnrollmentPresenter;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.users.UserAddressActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationAddActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalAddActivity;
import com.system.technologyinformation.utils.ActivityCollector;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;

public class EnrollmentActivity extends BaseActivity<EnrollmentPresenter> implements EnrollmentContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    LinearLayout lin_em_name;
    TextView tv_em_name;
    LinearLayout lin_em_address;
    TextView tv_em_address;
    LinearLayout lin_em_profession;
    TextView tv_em_profession;
    LinearLayout lin_em_program;
    EditText et_em_program;
    @BindView(R.id.tv_em)
    TextView tv_em;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_rule)
    TextView tv_rule;
    @BindView(R.id.tv_process)
    TextView tv_process;
    @BindView(R.id.sv_content)
    ScrollView sv_content;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;
    int raceId = 0;
    private String profession;
    private String name;
    private String address;
    Intent intent;

    @Override
    protected int getContentView() {
        return R.layout.activity_enrollment;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("比赛报名");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        getPresenter().getEnrollmentInfoResult();
        tv_em.setEnabled(true);
        tv_em.setSelected(true);
    }


    public void userInfo(int cztype) {
        loadUserInfo();
       /* if (login != null) {
            if (login.getCareerInfo() != null) {
                if (login.getCareerInfo().getAddressProvince() != null) {
                    address = login.getCareerInfo().getAddressDetail();
                    tv_em_address.setText(login.getCareerInfo().getAddressDetail());
                    lin_em_address.setEnabled(false);
                } else {
                    tv_em_address.setText("去填写");
                    lin_em_address.setEnabled(true);
                }
            } else {
                tv_em_address.setText("去填写");
                lin_em_address.setEnabled(true);
            }

            if (login.getIdentificationInfos() != null && login.getIdentificationInfos().size() > 0) {
                for (int i = 0; i < login.getIdentificationInfos().size(); i++) {
                    if (login.getIdentificationInfos().get(i).getType() == 1) {
                        if (login.getIdentificationInfos().get(i).getStatus() == 1 && profession == null) {
                            profession = login.getIdentificationInfos().get(i).getCkName();
                        }
                    } else if (login.getIdentificationInfos().get(i).getType() == 0) {
                        if (login.getIdentificationInfos().get(i).getStatus() == 1) {
                            name = login.getIdentificationInfos().get(i).getRealname();
                        }
                    }
                }
                if (!EmptyUtils.isEmpty(name)) {
                    tv_em_name.setText(name);
                    lin_em_name.setEnabled(false);
                } else {
                    tv_em_name.setText("去认证");
                    lin_em_name.setEnabled(true);
                }
                if (!EmptyUtils.isEmpty(profession)) {
                    tv_em_profession.setText(profession);
                    lin_em_profession.setEnabled(false);
                } else {
                    tv_em_profession.setText("去认证");
                    lin_em_profession.setEnabled(true);
                }
            } else {
                tv_em_profession.setText("去认证");
                tv_em_name.setText("去认证");
                lin_em_name.setEnabled(true);
                lin_em_profession.setEnabled(true);
            }
            if (cztype == 1) {
                getPresenter().getUpdateUserInfoResult(login.getId());
            }
        } else {
            tv_em_address.setText("去填写");
            lin_em_address.setEnabled(true);
            tv_em_profession.setText("去认证");
            lin_em_profession.setEnabled(true);
            tv_em_name.setText("去认证");
            lin_em_name.setEnabled(true);
        }*/
    }

    @Override
    public void setEnrollmentInfoResult(EnrollmentBean enrollmentBean) {
        if (enrollmentBean != null) {
            raceId = enrollmentBean.getId();
            tv_address.setText(enrollmentBean.getAddress());
            tv_content.setText(enrollmentBean.getContent());
            tv_rule.setText(enrollmentBean.getRule().replace("\\n", "\n"));
            tv_process.setText(enrollmentBean.getProcess().replace("\\n", "\n"));
            vs_showerror.setVisibility(View.GONE);
            sv_content.setVisibility(View.VISIBLE);
            getPresenter().getWhetherTheRegistration(raceId);
        } else {
            sv_content.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean!=null) {
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            userInfo(2);
        } else {
            clearUserInfo();
            userInfo(2);
        }
    }

    @Override
    public void setWhetherTheRegistrationIsSuccessful(String str) {
        toast("报名成功");
        tv_em.setText("已报名");
        tv_em.setTextColor(getResources().getColor(R.color.white));
        tv_em.setEnabled(false);
    }

    @Override
    public void setWhetherTheRegistration(String str) {
        if (!EmptyUtils.isEmpty(str)) {
            if (Boolean.valueOf(str) == true) {
                tv_em.setText("已报名");
                tv_em.setTextColor(getResources().getColor(R.color.white));
            } else {
                tv_em.setText("立即报名");
                tv_em.setTextColor(getResources().getColor(R.color.black));
            }
            tv_em.setEnabled(!Boolean.valueOf(str));
            tv_em.setSelected(!Boolean.valueOf(str));
        } else {
            tv_em.setEnabled(true);
            tv_em.setSelected(true);
        }
    }


    @OnClick({R.id.toolbar,
            R.id.tv_em
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_em:
                new EnrollmentView(getBaseContext(), sv_content);
                break;
        }
    }

    public class EnrollmentView extends PopupWindow {
        public EnrollmentView(Context mContext, View parent) {
            View view = View.inflate(mContext, R.layout.view_enrollment, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView tv_dialog_confirm = (TextView) view.findViewById(R.id.tv_dialog_confirm);
            lin_em_name = (LinearLayout) view.findViewById(R.id.lin_em_name);
            tv_em_name = (TextView) view.findViewById(R.id.tv_em_name);
            lin_em_address = (LinearLayout) view.findViewById(R.id.lin_em_address);
            tv_em_address = (TextView) view.findViewById(R.id.tv_em_address);
            lin_em_profession = (LinearLayout) view.findViewById(R.id.lin_em_profession);
            tv_em_profession = (TextView) view.findViewById(R.id.tv_em_profession);
            lin_em_program = (LinearLayout) view.findViewById(R.id.lin_em_program);
            et_em_program = (EditText) view.findViewById(R.id.et_em_program);
            ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            userInfo(1);
            lin_em_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (login != null) {
                        intent = new Intent(getBaseContext(), UserNameCertificationAddActivity.class);
                        startActivityForResult(intent, 2);
                    } else {
                        intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
            });
            lin_em_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (login != null) {
                        intent = new Intent(getBaseContext(), UserAddressActivity.class);
                        startActivityForResult(intent, 2);
                    } else {
                        intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
            });
            lin_em_profession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (login != null) {
                        intent = new Intent(getBaseContext(), UserProfessionalAddActivity.class);
                        startActivityForResult(intent, 2);
                    } else {
                        intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
            });

            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //String url = versionShowBean.getUrl();
                    if (EmptyUtils.isEmpty(name)) {
                        toast("请先认证个人信息");
                        return;
                    }
                    if (EmptyUtils.isEmpty(address)) {
                        toast("请先认证工地");
                        return;
                    }
                    if (EmptyUtils.isEmpty(profession)) {
                        toast("请先认证工种");
                        return;
                    }
                    if (EmptyUtils.isEmpty(et_em_program.getText().toString().trim())) {
                        toast("请填写参赛项目");
                        return;
                    }
                    getPresenter().getWhetherTheRegistrationIsSuccessful(raceId, et_em_program.getText().toString().trim());
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
            userInfo(2);
            intent = new Intent("userupdate");
            sendBroadcast(intent);
        }
        if (requestCode == 2 && resultCode == 1) {
            loadUserInfo();
            userInfo(1);
            intent = new Intent("userupdate");
            sendBroadcast(intent);
        }
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        if (code == -1) {
            openLogin();
            return;
        } else if (code == 405) {
            sv_content.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = (LinearLayout) findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getEnrollmentInfoResult();
                }
            });
        } else {
            sv_content.setVisibility(View.GONE);
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
