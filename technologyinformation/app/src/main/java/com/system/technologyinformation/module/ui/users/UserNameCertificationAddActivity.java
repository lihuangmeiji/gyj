package com.system.technologyinformation.module.ui.users;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.Word;
import com.baidu.ocr.sdk.model.WordSimple;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.CardInfoBean;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.UserNameInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.UserNameCertificationAddContract;
import com.system.technologyinformation.module.presenter.UserNameCertificationAddPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.DataKeeper;
import com.system.technologyinformation.utils.PhotoUtils;
import com.system.technologyinformation.utils.PreventKeyboardBlockUtil;
import com.system.technologyinformation.widget.RoundImageView2;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class UserNameCertificationAddActivity extends BaseActivity<UserNameCertificationAddPresenter> implements UserNameCertificationAddContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.et_nc_name)
    EditText et_nc_name;
    @BindView(R.id.et_nc_identity_number)
    EditText et_nc_identity_number;
    @BindView(R.id.tv_nc)
    TextView tv_nc;
    @BindView(R.id.iv_nc_identity_number_zm)
    RoundImageView2 iv_nc_identity_number_zm;
    @BindView(R.id.rel_nc_identity_number_zm)
    RelativeLayout rel_nc_identity_number_zm;
    @BindView(R.id.iv_nc_identity_number_fm)
    RoundImageView2 iv_nc_identity_number_fm;
    @BindView(R.id.rel_nc_identity_number_fm)
    RelativeLayout rel_nc_identity_number_fm;
    @BindView(R.id.tv_rz_info)
    TextView tvRzInfo;
    @BindView(R.id.rel_nc_botoom)
    RelativeLayout relNcBotoom;
    @BindView(R.id.et_nc_identity_time)
    EditText et_nc_identity_time;
    private IdentificationInfosBean nameIdentificationInfosBean = null;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    private File fileUri = null;
    private File fileCropUri = null;
    private Uri imageUri = null;
    private Uri cropImageUri = null;

    public static ArrayList<File> mSelectPath = new ArrayList<File>();
    //正反面标识
    private int zjtype = 0;

    //正面文件
    private File fileUriz = null;
    private String zphotopath = null;
    //反面文件
    private File fileUrif = null;

    private CardInfoBean cardInfoBean;
    private static final String TAG = "UserNameCertificationAddActivity";

    @Override
    protected int getContentView() {
        return R.layout.activity_user_name_certification_add;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        cardInfoBean = new CardInfoBean();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("实名认证");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        tv_nc.setEnabled(true);
        tv_nc.setSelected(true);
        getPresenter().getNameCertificationInfoResult();
        et_nc_name.setFocusableInTouchMode(false);
        et_nc_identity_number.setFocusableInTouchMode(false);
        et_nc_identity_time.setFocusableInTouchMode(false);
    }

    @OnClick({R.id.toolbar,
            R.id.tv_nc,
            R.id.iv_nc_identity_number_zm,
            R.id.rel_nc_identity_number_zm,
            R.id.iv_nc_identity_number_fm,
            R.id.rel_nc_identity_number_fm,
            R.id.iv_nc_name,
            R.id.iv_nc_identity_number,
            R.id.iv_nc_identity_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.iv_nc_name:
                et_nc_name.setFocusableInTouchMode(true);
                et_nc_name.setFocusable(true);
                et_nc_name.setFocusableInTouchMode(true);
                et_nc_name.requestFocus();
                break;
            case R.id.iv_nc_identity_number:
                et_nc_identity_number.setFocusableInTouchMode(true);
                et_nc_identity_number.setFocusable(true);
                et_nc_identity_number.setFocusableInTouchMode(true);
                et_nc_identity_number.requestFocus();
                break;
            case R.id.iv_nc_identity_time:
                et_nc_identity_time.setFocusableInTouchMode(true);
                et_nc_identity_time.setFocusable(true);
                et_nc_identity_time.setFocusableInTouchMode(true);
                et_nc_identity_time.requestFocus();
                break;
            case R.id.tv_nc:
                if (EmptyUtils.isEmpty(fileUriz)) {
                    toast("请上传身份证正面照");
                    return;
                }
                if (EmptyUtils.isEmpty(fileUrif)) {
                    toast("请上传身份证反面照");
                    return;
                }

                if (EmptyUtils.isEmpty(et_nc_name.getText().toString())) {
                    toast("请填写真实姓名");
                    return;
                }
                if (EmptyUtils.isEmpty(et_nc_identity_number.getText().toString())) {
                    toast("请填写身份证号");
                    return;
                }
                if (EmptyUtils.isEmpty(et_nc_identity_time.getText().toString())) {
                    toast("请填写有效期限");
                    return;
                }
                showLoading();
                getPresenter().getFileUploadResult(fileUriz, 1);

                break;
            case R.id.iv_nc_identity_number_zm:
            case R.id.rel_nc_identity_number_zm:
                zjtype = 1;
                hideInput();
                new PhotoView(getBaseContext(), rel_nc_identity_number_zm);
                break;
            case R.id.iv_nc_identity_number_fm:
            case R.id.rel_nc_identity_number_fm:
                zjtype = 2;
                hideInput();
                new PhotoView(getBaseContext(), rel_nc_identity_number_fm);
                break;
        }
    }


    @Override
    public void setFileUploadResult(String str, int type) {
        if (type == 1) {
            getPresenter().getFileUploadResult(fileUrif, 2);
            zphotopath = str;
        } else if (type == 2) {
            FileUtils.deleteDir(fileUriz);
            FileUtils.deleteDir(fileUrif);
            Gson gson = new Gson();
            getPresenter().getNameCertificationAddResult(0, et_nc_name.getText().toString(), et_nc_identity_number.getText().toString(), zphotopath, str, gson.toJson(cardInfoBean), et_nc_identity_time.getText().toString().trim());
        }
    }

    @Override
    public void setNameCertificationAddResult(String str) {
        hiddenLoading();
        toast("提交成功");
        setResult(1);
        finish();
    }

    @Override
    public void setNameCertificationUpdateResult(String str) {

    }

    @Override
    public void setNameCertificationInfoResult(UserNameInfoBean userNameInfoBean) {
        if (userNameInfoBean != null) {
            if (userNameInfoBean.getStatus() == 1) {
                //tvRzInfo.setText("您已完成实名认证！");
                tv_nc.setEnabled(false);
                tv_nc.setSelected(false);
                Glide.with(getBaseContext()).load(userNameInfoBean.getImgFront()).error(R.mipmap.identity_number_zm).into(iv_nc_identity_number_zm);
                Glide.with(getBaseContext()).load(userNameInfoBean.getImgBack()).error(R.mipmap.identity_number_fm).into(iv_nc_identity_number_fm);
                relNcBotoom.setVisibility(View.GONE);
                et_nc_name.setEnabled(false);
                et_nc_identity_number.setEnabled(false);
                et_nc_identity_time.setEnabled(false);
            } else if (userNameInfoBean.getStatus() == 0) {
                //tvRzInfo.setText("您的实名认证正在审核中...");
                new UserInfoAuditPromptView(getBaseContext(), tvRzInfo, 1, null);
                tv_nc.setEnabled(false);
                tv_nc.setSelected(false);
                Glide.with(getBaseContext()).load(userNameInfoBean.getImgFront()).error(R.mipmap.identity_number_zm).into(iv_nc_identity_number_zm);
                iv_nc_identity_number_zm.setEnabled(false);
                rel_nc_identity_number_zm.setEnabled(false);
                Glide.with(getBaseContext()).load(userNameInfoBean.getImgBack()).error(R.mipmap.identity_number_fm).into(iv_nc_identity_number_fm);
                iv_nc_identity_number_fm.setEnabled(false);
                rel_nc_identity_number_fm.setEnabled(false);
                et_nc_name.setText(userNameInfoBean.getRealName());
                et_nc_name.setEnabled(false);
                et_nc_identity_number.setText(userNameInfoBean.getIdCode());
                et_nc_identity_number.setEnabled(false);
                et_nc_identity_time.setText(userNameInfoBean.getValidityPeriod());
                et_nc_identity_time.setEnabled(false);
                relNcBotoom.setVisibility(View.GONE);
            } else if (userNameInfoBean.getStatus() == 2) {
                //tvRzInfo.setText("您提交的实名认证审核失败！");
                new UserInfoAuditPromptView(getBaseContext(), tvRzInfo, 2, userNameInfoBean.getRejectsInfo());
                tv_nc.setEnabled(true);
                tv_nc.setSelected(true);
                relNcBotoom.setVisibility(View.VISIBLE);
            }
        } else {
            tv_nc.setEnabled(true);
            tv_nc.setSelected(true);
            relNcBotoom.setVisibility(View.VISIBLE);
        }
    }


    public class PhotoView extends PopupWindow {

        public PhotoView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.toast_keynote, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(ActionBar.LayoutParams.FILL_PARENT);
            setHeight(ActionBar.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_camera);
            TextView bt2 = (TextView) view.findViewById(R.id.item_popupwindows_Photo);
            TextView bt3 = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    fileUri = new File(DataKeeper.imagePath + "photo" + System.currentTimeMillis() + ".jpg");
                    fileCropUri = new File(DataKeeper.imagePath + "crop_photo" + System.currentTimeMillis() + ".jpg");
                    autoObtainCameraPermission();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    fileUri = new File(DataKeeper.imagePath + "photo" + System.currentTimeMillis() + ".jpg");
                    fileCropUri = new File(DataKeeper.imagePath + "crop_photo" + System.currentTimeMillis() + ".jpg");
                    autoObtainStoragePermission();
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
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
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

            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    toast("请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 360;
    private static final int OUTPUT_Y = 120;

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
                        if (zjtype == 1) {
                            iv_nc_identity_number_zm.setImageBitmap(bitmap);
                            fileUriz = fileUri;
                            ocr(fileUriz, IDCardParams.ID_CARD_SIDE_FRONT);
                        } else if (zjtype == 2) {
                            iv_nc_identity_number_fm.setImageBitmap(bitmap);
                            fileUrif = fileUri;
                            ocr(fileUrif, IDCardParams.ID_CARD_SIDE_BACK);
                        }
                    }
                    //PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;

                case CODE_RESULT_REQUEST:
                   /* Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        if (zjtype == 1) {
                            iv_nc_identity_number_zm.setImageBitmap(bitmap);
                            rel_nc_identity_number_zm.setVisibility(View.GONE);
                            fileUriz = fileCropUri;
                        } else if (zjtype == 2) {
                            iv_nc_identity_number_fm.setImageBitmap(bitmap);
                            rel_nc_identity_number_fm.setVisibility(View.GONE);
                            fileUrif = fileCropUri;
                        }
                    }*/
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        fileUri = new File(newUri.getPath());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.system.technologyinformation.fileProvider", fileUri);
                        }
                        //PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                        Bitmap bitmap1 = PhotoUtils.getBitmapFromUri(newUri, this);
                        if (bitmap1 != null) {
                            if (zjtype == 1) {
                                iv_nc_identity_number_zm.setImageBitmap(bitmap1);
                                fileUriz = fileUri;
                                ocr(fileUriz, IDCardParams.ID_CARD_SIDE_FRONT);
                            } else if (zjtype == 2) {
                                iv_nc_identity_number_fm.setImageBitmap(bitmap1);
                                fileUrif = fileUri;
                                ocr(fileUrif, IDCardParams.ID_CARD_SIDE_BACK);
                            }
                        }
                    } else {
                        toast("设备没有SD卡！");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void ocr(File filePath, final String idCardSide) {
        // 通用文字识别参数设置

        IDCardParams param = new IDCardParams();
        param.setImageFile(filePath);
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_FRONT)) {
                        if (cardInfoBean != null) {
                            if (!EmptyUtils.isEmpty(result.getAddress())) {
                                cardInfoBean.setAddress(result.getAddress().toString());
                            }
                            if (!EmptyUtils.isEmpty(result.getBirthday())) {
                                cardInfoBean.setBirthday(result.getBirthday().toString());
                            }
                            if (!EmptyUtils.isEmpty(result.getGender())) {
                                cardInfoBean.setGender(result.getGender().toString());
                            }
                            if (!EmptyUtils.isEmpty(result.getEthnic())) {
                                cardInfoBean.setEthnic(result.getEthnic().toString());
                            }
                        }
                        if (!EmptyUtils.isEmpty(result.getEthnic())) {
                            cardInfoBean.setEthnic(result.getEthnic().toString());
                        }
                        if (!EmptyUtils.isEmpty(result.getName())) {
                            et_nc_name.setText(result.getName().toString());
                        }
                        if (!EmptyUtils.isEmpty(result.getIdNumber())) {
                            et_nc_identity_number.setText(result.getIdNumber().toString());
                        }
                    } else if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_BACK)) {
                        if (cardInfoBean != null) {
                            if (!EmptyUtils.isEmpty(result.getSignDate())) {
                                cardInfoBean.setSignDate(result.getSignDate().toString());
                            }
                            if (!EmptyUtils.isEmpty(result.getExpiryDate())) {
                                cardInfoBean.setExpiryDate(result.getExpiryDate().toString());
                            }
                            if (!EmptyUtils.isEmpty(result.getIssueAuthority())) {
                                cardInfoBean.setIssueAuthority(result.getIssueAuthority().toString());
                            }
                        }
                        if (!EmptyUtils.isEmpty(result.getSignDate()) && !EmptyUtils.isEmpty(result.getExpiryDate())) {
                            if (result.getSignDate().toString().length() == 8 && result.getExpiryDate().toString().length() == 8) {
                                et_nc_identity_time.setText(result.getExpiryDate().toString().substring(0, 4) + "." + result.getExpiryDate().toString().substring(4, 6) + "." + result.getExpiryDate().toString().substring(6, 8));
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
               /* if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_FRONT)) {
                    iv_nc_identity_number_zm.setImageResource(R.mipmap.identity_number_zm);
                } else if (idCardSide.equals(IDCardParams.ID_CARD_SIDE_BACK)) {
                    iv_nc_identity_number_fm.setImageResource(R.mipmap.identity_number_fm);
                }*/
                toast("证件解析失败，请手动输入！");
            }
        });
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
        } else if (code == -10) {
            tv_nc.setEnabled(true);
            tv_nc.setSelected(true);
            return;
        }
        toast(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreventKeyboardBlockUtil.getInstance(this).setBtnView(relNcBotoom).register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreventKeyboardBlockUtil.getInstance(this).unRegister();
    }

    public class UserInfoAuditPromptView extends PopupWindow {

        public UserInfoAuditPromptView(Context mContext, View parent, int type, String content) {

            View view = View.inflate(mContext, R.layout.user_info_audit_prompt_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            TextView btn_dialog_cancel = (TextView) view.findViewById(R.id.btn_dialog_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            if (type == 1) {
                tv_content.setText("您提交的实名认证正在审核中...");
            } else if (type == 2) {
                String c;
                if (!EmptyUtils.isEmpty(content)) {
                    c = "您提交的实名认证审核失败！\\n请重新提交\\n失败原因：" + content;
                }else{
                    c = "您提交的实名认证审核失败！\\n请重新提交";
                }
                tv_content.setText(c.replace("\\n", "\n"));
            }
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
}
