package com.system.technologyinformation.module.ui.users;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.SimpleActivity;
import com.system.technologyinformation.utils.ActivityCollector;
import com.system.technologyinformation.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class IntegralRulesActivity extends SimpleActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_right)
    TextView iv_right;
    @BindView(R.id.tv_q1)
    TextView tv_q1;
    @BindView(R.id.tv_a1)
    TextView tv_a1;
    @BindView(R.id.tv_q2)
    TextView tv_q2;
    @BindView(R.id.tv_a2)
    TextView tv_a2;
    @BindView(R.id.tv_q3)
    TextView tv_q3;
    @BindView(R.id.tv_a3)
    TextView tv_a3;
    @BindView(R.id.tv_q4)
    TextView tv_q4;
    @BindView(R.id.tv_a4)
    TextView tv_a4;
    @BindView(R.id.tv_q5)
    TextView tv_q5;
    @BindView(R.id.tv_a5)
    TextView tv_a5;
    @BindView(R.id.tv_q6)
    TextView tv_q6;
    @BindView(R.id.tv_a6)
    TextView tv_a6;
    @BindView(R.id.tv_q7)
    TextView tv_q7;
    @BindView(R.id.tv_a7)
    TextView tv_a7;
    @BindView(R.id.tv_q8)
    TextView tv_q8;
    @BindView(R.id.tv_a8)
    TextView tv_a8;
    @Override
    protected int getContentView() {
        return R.layout.activity_integral_rules;
    }

    @Override
    protected void initEventAndData() {
        commonTheme();
        ActivityCollector.addActivity(this);
        toolbarTitle.setText("积分详情");
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        iv_right.setVisibility(View.GONE);
        tv_q1.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","什么是积分？",R.style.integralRules1,R.style.integralRules2));
        tv_a1.setText(TextUtil.FontHighlighting(getBaseContext(),"答:"," 积分是存在于工友家APP内的一种虚拟货币，可以用来抵扣现金、兑换虚拟或实体商品，或折算成其他权益，但不能流通。",R.style.integralRules1,R.style.integralRules2));
        tv_q2.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","如何使用积分？",R.style.integralRules1,R.style.integralRules2));
        tv_a2.setText(TextUtil.FontHighlighting(getBaseContext(),"答:","在工友家合作的商店使用本APP支付时，积分可以自动抵扣部分现金；你也可以直接在积分商城兑换相应的商品。",R.style.integralRules1,R.style.integralRules2));
        tv_q3.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","如何获取积分",R.style.integralRules1,R.style.integralRules2));
        tv_a3.setText(TextUtil.FontHighlighting(getBaseContext(),"答:"," 用户可通过进入任务页面完成包括“每日签到”、“基础任务”、“进阶任务”等内容，获取相应积分奖励。",R.style.integralRules1,R.style.integralRules2));
        tv_q4.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","积分的有效期是多久？" ,R.style.integralRules1,R.style.integralRules2));
        tv_a4.setText(TextUtil.FontHighlighting(getBaseContext(),"答:","积分的有效期为1年，即从获得积分开始至次年同月最后一天到期，逾期作废。",R.style.integralRules1,R.style.integralRules2));
        tv_q5.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","如何查看积分获取历史？",R.style.integralRules1,R.style.integralRules2));
        tv_a5.setText(TextUtil.FontHighlighting(getBaseContext(),"答:","用户可通过进入“我的”页面，点击“我的积分”进行查看积分获取历史。",R.style.integralRules1,R.style.integralRules2));
        tv_q6.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","积分可以转给其他用户吗？",R.style.integralRules1,R.style.integralRules2));
        tv_a6.setText(TextUtil.FontHighlighting(getBaseContext(),"答:","不可以，积分不支持账户之间的相互转换。",R.style.integralRules1,R.style.integralRules2));
        tv_q7.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","积分日历如何补签？",R.style.integralRules1,R.style.integralRules2));
        tv_a7.setText(TextUtil.FontHighlighting(getBaseContext(),"答:","什么是积分？",R.style.integralRules1,R.style.integralRules2));
        tv_q8.setText(TextUtil.FontHighlighting(getBaseContext(),"问:","什么是积分？",R.style.integralRules1,R.style.integralRules2));
        tv_a8.setText(TextUtil.FontHighlighting(getBaseContext(),"答:","什么是积分？",R.style.integralRules1,R.style.integralRules2));
    }

    @OnClick({R.id.toolbar
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
        }
    }
}
