package com.system.technologyinformation.module.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.module.ui.users.UserOrderActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.DataKeeper;
import com.system.technologyinformation.utils.TextUtil;
import com.system.technologyinformation.widget.RoundImageView1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CreditsExchangeSuccessActivity extends SimpleActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.iv_sh_ico)
    RoundImageView1 ivShIco;
    @BindView(R.id.tv_sh_name)
    TextView tvShName;
    @BindView(R.id.tv_sh_point)
    TextView tvShPoint;
    @BindView(R.id.lin_type3)
    LinearLayout linType3;
    @BindView(R.id.lin_type1)
    LinearLayout linType1;
    @BindView(R.id.iv_save_qr_code)
    ImageView ivSaveQrCode;
    @BindView(R.id.tv_save_qr_code)
    TextView tvSaveQrCode;
    int type = 0;
    private long firstTime = 0;//第一次点击事件
    @Override
    protected int getContentView() {
        return R.layout.activity_credits_exchange_success;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("确认信息");
        toolbarTitle.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.color49));
        toolbar.setNavigationIcon(R.mipmap.ic_return_video);
        iv_right.setVisibility(View.GONE);
        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int point = getIntent().getIntExtra("point", 0);
        double currentPrice=getIntent().getDoubleExtra("currentPrice",0);
        type = getIntent().getIntExtra("type", 0);
        String qrcode = getIntent().getStringExtra("qrcode");
        Glide.with(getBaseContext()).load(image).into(ivShIco);
        tvShName.setText(name);
        //tvShPoint.setText(TextUtil.FontHighlighting(getBaseContext(), point + "", "积分", R.style.tvShPoint2, R.style.tvShPoint1));
        if (!EmptyUtils.isEmpty(currentPrice) && currentPrice > 0) {
            if (point > 0) {
                tvShPoint.setText(TextUtil.FontHighlighting(getBaseContext(), "¥", "" + currentPrice, "+" + point + "积分", R.style.tv_ce_point1, R.style.tv_ce_point2, R.style.tv_ce_point1));
            } else {
                tvShPoint.setText(TextUtil.FontHighlighting(getBaseContext(), "¥", "" + currentPrice, R.style.tv_ce_point1, R.style.tv_ce_point2));
            }
        } else {
            if (point > 0) {
                tvShPoint.setText(TextUtil.FontHighlighting(getBaseContext(), point + "", "积分", R.style.tv_ce_point2, R.style.tv_ce_point1));
            }
        }
        if (type == 3) {
            Glide.with(getBaseContext()).load(qrcode).into(ivSaveQrCode);
            linType3.setVisibility(View.VISIBLE);
            linType1.setVisibility(View.GONE);
        }else{
            linType1.setVisibility(View.VISIBLE);
            linType3.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.toolbar,
            R.id.tv_save_qr_code,
            R.id.lin_type1
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                Intent intentuserupdate = new Intent("userupdate");
                sendBroadcast(intentuserupdate);
                break;
            case R.id.tv_save_qr_code:
                CreditsExchangeSuccessActivityPermissionsDispatcher.showSaveImageWithCheck(this);
                break;
            case R.id.lin_type1:
                Intent intent = new Intent(getBaseContext(), UserOrderActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showSaveImage() {
        tvSaveQrCode.setVisibility(View.GONE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_save);
        saveMyBitmap("save-qr-qode" + TimeUtils.date2String(new Date()), createViewBitmap(linearLayout));
    }


    //将要存为图片的view传进来 生成bitmap对象

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public void saveMyBitmap(final String bitName, final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filePath = DataKeeper.imagePath;
                File file = new File(filePath + bitName + ".png");
                try {
                    file.createNewFile();


                    FileOutputStream fOut = null;
                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);


                    Message msg = Message.obtain();
                    msg.obj = file.getPath();
                    handler.sendMessage(msg);
                    //Toast.makeText(PayCodeActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length - 1];
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext()
                        .getContentResolver(), picFile, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                    + picFile)));
            toast("图片保存图库成功");
            tvSaveQrCode.setVisibility(View.VISIBLE);
            //tvSave.setClickable(true);
        }
    };


    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForSaveImage(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("二维码保存")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForSaveImage() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForSaveImage() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法
        CreditsExchangeSuccessActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
