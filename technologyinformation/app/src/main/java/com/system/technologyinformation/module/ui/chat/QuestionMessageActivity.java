package com.system.technologyinformation.module.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.QuestionBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.QuestionMessageContract;
import com.system.technologyinformation.module.presenter.QuestionMessagePresenter;
import com.system.technologyinformation.module.ui.users.UserNameCertificationAddActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.DataKeeper;
import com.system.technologyinformation.utils.PhotoUtils;
import com.system.technologyinformation.widget.RoundImageView1;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionMessageActivity extends BaseActivity<QuestionMessagePresenter> implements QuestionMessageContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.tv_question_content)
    EditText tv_question_content;
    @BindView(R.id.iv_question_img)
    RoundImageView1 iv_question_img;
    @BindView(R.id.tv_question)
    TextView tv_question;
    String groupCode;
    private File fileUri = null;
    private Uri imageUri = null;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    int userRole;

    @Override
    protected int getContentView() {
        return R.layout.activity_question_message;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        userRole = getIntent().getIntExtra("userRole", 3);
        groupCode = getIntent().getStringExtra("groupCode");
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("报告问题");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        hideInput();
    }


    @OnClick({R.id.toolbar,
            R.id.tv_question,
            R.id.iv_question_img
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.tv_question:
                if (EmptyUtils.isEmpty(tv_question_content.getText().toString())) {
                    toast("请填写内容!");
                    return;
                }
                showLoading();
                if (fileUri != null) {
                    getPresenter().getFileUploadResult(fileUri);
                } else {
                    getPresenter().addquestionMessage(null, tv_question_content.getText().toString(), groupCode);
                }
                break;
            case R.id.iv_question_img:
                hideInput();
                fileUri = new File(DataKeeper.imagePath + "questionphoto" + System.currentTimeMillis() + ".jpg");
                autoObtainCameraPermission();
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
        } else if (code == -10) {
            return;
        }
        toast(msg);
    }

    @Override
    public void addquestionMessageResult(QuestionBean questionBean) {
        hiddenLoading();
        if (questionBean != null) {
            toast("提交成功!");
            tv_question_content.setText("");
            if (fileUri != null) {
                fileUri.delete();
            }
            fileUri = null;
            iv_question_img.setImageResource(R.mipmap.question_img);
        } else {
            toast("数据解析失败");
        }
    }

    @Override
    public void setFileUploadResult(String str) {
        FileUtils.deleteDir(fileUri);
        getPresenter().addquestionMessage(str, tv_question_content.getText().toString(), groupCode);
    }

    @Override
    public void refreshUserTimeResult(BaseResponseInfo result) {
        if (result.getCode() == 0) {
            if (fileUri != null) {
                getPresenter().getFileUploadResult(fileUri);
            } else {
                getPresenter().addquestionMessage(null, tv_question_content.getText().toString(), groupCode);
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            if (fileUri != null) {
                getPresenter().getFileUploadResult(fileUri);
            } else {
                getPresenter().addquestionMessage(null, tv_question_content.getText().toString(), groupCode);
            }
        } else {
            openLogin();
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                toast("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(getBaseContext(), "com.system.technologyinformation.fileProvider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                toast("设备没有SD卡！");
            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(getBaseContext(), "com.system.technologyinformation.fileProvider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        toast("设备没有SD卡！");
                    }
                } else {
                    toast("请允许打开相机！！");
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(imageUri, this);
                    if (bitmap != null) {
                        iv_question_img.setImageBitmap(bitmap);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
