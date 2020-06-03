package com.system.technologyinformation.module.demo2;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.utils.DataKeeper;
import com.system.technologyinformation.utils.InstallApk;
import com.system.technologyinformation.utils.SoftUpdate;

import java.io.File;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by wujiajun on 2017/10/23.
 */

@RuntimePermissions
public class Demo2Activity extends SimpleActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_demo2;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        Demo2ActivityPermissionsDispatcher.showSoftWithCheck(this);
    }


    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showSoft() {
        SoftUpdate manager = new SoftUpdate(Demo2Activity.this, "下载地址");
        manager.showDownloadDialog();
    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("软件更新")
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
    void showDeniedForCamera() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法
        Demo2ActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10012:
                Log.d("resultCode", resultCode + "");
                if (Build.VERSION.SDK_INT >= 26) {
                    boolean b = getBaseContext().getPackageManager().canRequestPackageInstalls();
                    if (b) {
                        new InstallApk(this)
                                .installApk(new File(DataKeeper.fileRootPath, "appname.apk"));
                    } else {
                        ToastUtils.showLongToast("请赋予权限后在操作！");
                        // 退出程序
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                }
                break;
            default:
                break;
        }
    }
}
