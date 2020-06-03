package com.system.technologyinformation.module.ui.users;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.ProfessionalTypeBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.adapter.ProfessionalTypeAdapter;
import com.system.technologyinformation.module.contract.UserProfessionalAddContract;
import com.system.technologyinformation.module.presenter.UserProfessionalAddPresenter;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.PhotoUtils;
import com.system.technologyinformation.widget.PickerView;
import com.system.technologyinformation.widget.RecycleViewDivider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserProfessionalAddActivity extends BaseActivity<UserProfessionalAddPresenter> implements UserProfessionalAddContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.et_prifessional_type)
    TextView et_prifessional_type;
    @BindView(R.id.iv_prifessional)
    ImageView iv_prifessional;
    @BindView(R.id.lin_prifessional_img)
    LinearLayout lin_prifessional_img;
    @BindView(R.id.lin_prifessional_type)
    LinearLayout lin_prifessional_type;
    @BindView(R.id.tv_prifessional)
    TextView tv_prifessional;
    List<ProfessionalTypeBean> professionalTypeBeanList;
    private ProfessionalTypeBean professionalTypeBean;
    private IdentificationInfosBean identificationInfosBean;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    private File fileUri = null;
    private File fileCropUri = null;
    private Uri imageUri = null;
    private Uri cropImageUri = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_professional_add;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("选择工种");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        professionalTypeBeanList = new ArrayList<>();
        getPresenter().getProfessionalTypeListResult();
        identificationInfosBean = (IdentificationInfosBean) getIntent().getSerializableExtra("jobidentificationInfosBean");
        if (identificationInfosBean != null) {
            tv_prifessional.setText("修改认证");
            et_prifessional_type.setText(identificationInfosBean.getName());
            lin_prifessional_img.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.toolbar,
            R.id.et_prifessional_type,
            R.id.tv_prifessional,
            R.id.lin_prifessional_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.et_prifessional_type:
                //new ProfessionalTypeView(getBaseContext(), lin_prifessional_type);
                ShowBankName();
                break;
            case R.id.tv_prifessional:
                if (identificationInfosBean != null) {
                    if (identificationInfosBean.getName() != null ) {
                        toast("请修改信息重新认证");
                        return;
                    } else {
                      /*  if (cropImageUri == null) {
                            toast("请上传图片");
                            return;
                        }*/
                        if (EmptyUtils.isEmpty(et_prifessional_type.getText().toString())) {
                            toast("请选择职业类型");
                            return;
                        }
                        showLoading();
                        //getPresenter().getFileUploadResult(fileCropUri);
                        getPresenter().getProfessionalUpdateResult(identificationInfosBean.getId(), 1, professionalTypeBean.getId(), "");
                    }
                } else {
                 /*   if (cropImageUri == null) {
                        toast("请上传图片");
                        return;
                    }*/
                    if (EmptyUtils.isEmpty(et_prifessional_type.getText().toString())) {
                        toast("请选择职业类型");
                        return;
                    }
                    showLoading();
                    //getPresenter().getFileUploadResult(fileCropUri);
                    getPresenter().getProfessionalAddResult(1, professionalTypeBean.getId(), "");
                }
                break;
            case R.id.lin_prifessional_img:
                new PhotoView(getBaseContext(), lin_prifessional_img);
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
            openLogin();
            return;
        }else if(code==-10){
            return;
        }
        toast(msg);
    }

    @Override
    public void setProfessionalTypeListResult(List<ProfessionalTypeBean> professionalTypeListResult) {
        professionalTypeBeanList.addAll(professionalTypeListResult);
    }

    @Override
    public void setFileUploadResult(String str) {
        if (identificationInfosBean != null) {
            getPresenter().getProfessionalUpdateResult(identificationInfosBean.getId(), 1, professionalTypeBean.getId(), str);
        } else {
            getPresenter().getProfessionalAddResult(1, professionalTypeBean.getId(), str);
        }
    }

    @Override
    public void setProfessionalAddResult(String str) {
        hiddenLoading();
        toast("添加成功");
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    @Override
    public void setProfessionalUpdateResult(String str) {
        hiddenLoading();
        toast("修改成功");
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }


    public class ProfessionalTypeView extends PopupWindow {

        public ProfessionalTypeView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.view_professional_type, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
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
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            PickerView pickerView = view.findViewById(R.id.pi_professional_type);

            TextView btn_dialog_cancel = view.findViewById(R.id.btn_dialog_cancel);
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            TextView btn_dialog_confirm = view.findViewById(R.id.btn_dialog_confirm);
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (professionalTypeBean == null) {
                        toast("请选择你的工种");
                        return;
                    }
                    iv_prifessional.setVisibility(View.GONE);
                    lin_prifessional_img.setVisibility(View.VISIBLE);
                    if (identificationInfosBean != null) {
                        identificationInfosBean.setName(null);
                    }
                    et_prifessional_type.setText(professionalTypeBean.getName());
                }
            });
            pickerView.setData(professionalTypeBeanList);
            pickerView.setSelected(0);
            pickerView.setOnSelectListener(new PickerView.onSelectListener() {
                @Override
                public void onSelect(ProfessionalTypeBean text) {
                    professionalTypeBean = text;
                }
            });

            WheelView wheelView = view.findViewById(R.id.wheelview);
            wheelView.setCyclic(false);
            wheelView.setItemHeight(100);
            wheelView.setTextSize(16);
            wheelView.setCurrentItem(0);
            final List<String> mOptionsItems = new ArrayList<>();
            mOptionsItems.add("item0");
            mOptionsItems.add("item1");
            mOptionsItems.add("item2");
            mOptionsItems.add("item2");
            mOptionsItems.add("item2");
            mOptionsItems.add("item2");
            wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    toast(mOptionsItems.get(index));
                }
            });
        }
    }

    private void ShowBankName() {// 弹出条件选择器
        final List<String> mOptionsItems = new ArrayList<>();
        for (int i = 0; i < professionalTypeBeanList.size(); i++) {
            mOptionsItems.add(professionalTypeBeanList.get(i).getName());
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //String strBankName = mOptionsItems.get(options1);
               /* if (professionalTypeBean == null) {
                    toast("请选择你的工种");
                    return;
                }*/
                professionalTypeBean = professionalTypeBeanList.get(options1);
                iv_prifessional.setVisibility(View.GONE);
                lin_prifessional_img.setVisibility(View.VISIBLE);
                if (identificationInfosBean != null) {
                    identificationInfosBean.setName(null);
                }
                et_prifessional_type.setText(professionalTypeBean.getName());
                // et_prifessional_type.setText(strBankName);//将选中的数据返回设置在TextView 上。
            }
        })

                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setOutSideCancelable(true)// default is true
                .setCyclic(true, true, true)
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvOptions.setPicker(mOptionsItems);//条件选择器
        pvOptions.show();
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
                    fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo" + System.currentTimeMillis() + ".jpg");
                    fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo" + System.currentTimeMillis() + ".jpg");
                    autoObtainCameraPermission();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo" + System.currentTimeMillis() + ".jpg");
                    fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo" + System.currentTimeMillis() + ".jpg");
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
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;

                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        iv_prifessional.setImageBitmap(bitmap);
                        iv_prifessional.setVisibility(View.VISIBLE);
                        lin_prifessional_img.setVisibility(View.GONE);
                        if (identificationInfosBean != null) {
                            if (professionalTypeBean == null) {
                                et_prifessional_type.setText(null);
                            }
                        }
                    }
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.system.technologyinformation.fileProvider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        toast("设备没有SD卡！");
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
