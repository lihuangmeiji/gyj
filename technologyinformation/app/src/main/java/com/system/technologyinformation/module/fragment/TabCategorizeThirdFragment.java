package com.system.technologyinformation.module.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseFragment;
import com.system.technologyinformation.common.ui.SimpleFragment;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.PayEntranceBean;
import com.system.technologyinformation.model.ScanCodeSignBean;
import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.entertainment.OnlineSupermarketActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.scanner.ScannerGyjActivity;
import com.system.technologyinformation.module.ui.users.UserPayActivity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class TabCategorizeThirdFragment extends SimpleFragment {
    private final static String TAG = "TabCategorizeThirdFragment";
    Intent intent;
    private final int REQUEST_PERMISION_CODE_CAMARE = 0;
    private final int RESULT_REQUEST_CODE = 13;

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_tab_categorize_third;
    }

    @OnClick({R.id.rel_sp_shop,
            R.id.rel_sp_repast,
            R.id.iv_sys
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_sp_repast:
                intent = new Intent(getContext(), TableReservationActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_sp_shop:
                intent = new Intent(getContext(), OnlineSupermarketActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_sys:
                if (login != null) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        goScanner();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
                    }
                /*    Intent intent = new Intent(MainActivity.this, UserPayActivity.class);
                    intent.putExtra("storeId", 1);
                    startActivity(intent);*/
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }

    private void goScanner() {
        Intent intent = new Intent(getContext(), ScannerGyjActivity.class);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISION_CODE_CAMARE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                }
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
        }
    }

}
