package com.system.technologyinformation.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.EmptyUtils;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.chat.QuestionMessageActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeDetailedActivity;
import com.system.technologyinformation.module.ui.home.InformationConsultingActivity;
import com.system.technologyinformation.module.ui.home.PanicBuyingActivity;
import com.system.technologyinformation.module.ui.users.BusinessCooperationActivity;
import com.system.technologyinformation.module.ui.users.ShopUserActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SchemeJump {
    public static void  schemeJump(Context context,String path,int shoppingId,String title){
        if (!EmptyUtils.isEmpty(path)) {
            Intent intent=null;
            Log.i("path", "initEventAndData: " + path);
            if (path.equals("/home/mall")) {
                intent=new Intent(context, PanicBuyingActivity.class);
            } else if (path.equals("/home/mall/details")) {
                intent = new Intent(context, CreditsExchangeDetailedActivity.class);
                intent.putExtra("shoppingId", shoppingId);
            } else if (path.equals("/home/food")) {
                intent=new Intent(context, TableReservationActivity.class);
            } else if (path.equals("/home/exchange")) {
                intent=new Intent(context, CreditsExchangeActivity.class);
            } else if (path.equals("/home/exchange/details")) {
                intent = new Intent(context, CreditsExchangeDetailedActivity.class);
                intent.putExtra("shoppingId", shoppingId);
            } else if (path.equals("/home/news")) {
                intent=new Intent(context, InformationConsultingActivity.class);
            } else if (path.equals("/home/feedback/submit")) {
                intent =new Intent(context, QuestionMessageActivity.class);
            } else if (path.equals("/home/recharge")) {

            } else if (path.equals("/home/message")) {

            } else if (path.equals("/home/scan")) {

            } else if (path.equals("/task/checkin")) {

            } else if (path.equals("/mine/userinfo")) {

            } else if (path.equals("/mine/constructionplace")) {

            } else if (path.equals("/mine/identification")) {

            } else if (path.equals("/mine/usercareer")) {

            } else if (path.equals("/mine/invitation")) {

            } else if (path.equals("/mine/hotline")) {

            } else if (path.equals("/mine/about")) {

            } else if (path.equals("/mine/address")) {

            } else if (path.equals("/mine/histroy/point")) {

            } else if (path.equals("/mine/histroy/consume")) {

            } else if (path.equals("/share/link")) {

            } else if (path.equals("/shop/income")) {
                intent=new Intent(context, ShopUserActivity.class);
            }else {
                intent = new Intent(context, QuestionDetailesActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("url", path);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
