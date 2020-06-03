package com.system.technologyinformation.module.ui.scanner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;

import butterknife.BindView;
import butterknife.OnClick;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerGyjActivity extends SimpleActivity implements ZBarScannerView.ResultHandler{
    private ZBarScannerView mScannerView;
    @BindView(R.id.status_bar_view)
    View status_bar_view;
    @Override
    protected int getContentView() {
        return R.layout.activity_scanner_gyj;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        status_bar_view.setBackgroundColor(getResources().getColor(R.color.grid_state_pressed1));
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @OnClick({R.id.tv_scanner_cancel
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scanner_cancel:
                finish();
                break;
        }
    }

    @Override
    public void handleResult(Result rawResult) {
   /*     Toast.makeText(this, "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();*/
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Intent data = new Intent();
        data.putExtra("result_content", rawResult.getContents());
        setResult(RESULT_OK, data);
        finish();
    }
}
