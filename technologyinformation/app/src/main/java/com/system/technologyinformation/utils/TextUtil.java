package com.system.technologyinformation.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.di.AppCommonModule;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;

public class TextUtil {
    public static SpannableStringBuilder FontHighlighting(Context context, String src1, String src2, int appearance1, int appearance2) {
        String src = src1 + src2;
        SpannableStringBuilder spanStr = new SpannableStringBuilder(src);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance1), 0, src1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance2), src1.length(), src.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    public static SpannableStringBuilder FontHighlighting(Context context, String src1, String src2,String src3, int appearance1, int appearance2,int appearance3) {
        String content1 = src1 + src2 + src3;
        String content2 = src1 + src2;
        SpannableStringBuilder spanStr = new SpannableStringBuilder(content1);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance1), 0, src1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance2), src1.length(),content2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance3), content2.length(), content1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    public static SpannableStringBuilder FontHighlighting4(final Context context, String src1, String src2, String src3, String src4, int appearance1, final int appearance2) {
        String content = src1 + src2 + src3 + src4;
        String content1 = src1 + src2 + src3;
        String content2 = src1 + src2;
        SpannableStringBuilder spanStr = new SpannableStringBuilder(content);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance1), 0, src1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance2), src1.length(), content2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(R.color.color37));
                //设置是否有下划线
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View view) {
                Intent intentWeb = new Intent(context, QuestionDetailesActivity.class);
                intentWeb.putExtra("title", "用户协议");
                intentWeb.putExtra("url", AppCommonModule.API_BASE_URL + "/protocol/agreement.html");
                intentWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentWeb);
            }
        }, src1.length(), content2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance1), content2.length(), content1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new TextAppearanceSpan(context, appearance1), content1.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(R.color.color37));
                //设置是否有下划线
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View view) {
                Intent intentWeb = new Intent(context, QuestionDetailesActivity.class);
                intentWeb.putExtra("title", "隐私政策");
                intentWeb.putExtra("url", AppCommonModule.API_BASE_URL + "protocol/privacy.html");
                intentWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentWeb);
            }
        }, content1.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

}
