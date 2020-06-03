package com.system.technologyinformation.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.system.technologyinformation.common.ui.BaseActivity;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.WXPayEntryContract;
import com.system.technologyinformation.module.presenter.WXPayEntryPresenter;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, "wxb2911332cf274f1f");
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent;
            switch (resp.errCode) {
                case 0:
                    intent = new Intent("wxplay");
                    intent.putExtra("type", 1);
                    sendBroadcast(intent);
                    finish();
                    //Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    intent = new Intent("wxplay");
                    intent.putExtra("type", 2);
                    sendBroadcast(intent);
                    //Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -2:
                    intent = new Intent("wxplay");
                    intent.putExtra("type", 3);
                    sendBroadcast(intent);
                    finish();
                   // Toast.makeText(WXPayEntryActivity.this, "取消充值", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}