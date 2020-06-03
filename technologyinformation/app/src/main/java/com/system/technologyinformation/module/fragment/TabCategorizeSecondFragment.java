package com.system.technologyinformation.module.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.di.AppCommonModule;
import com.system.technologyinformation.common.ui.BaseLazyFragment;
import com.system.technologyinformation.model.CalendarBean;
import com.system.technologyinformation.model.DailyMissionBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.SignBean;
import com.system.technologyinformation.model.TaskBasisBean;
import com.system.technologyinformation.model.TaskPromotionBean;
import com.system.technologyinformation.model.TaskRecordBean;
import com.system.technologyinformation.module.adapter.IntegralDisplayItemViewProvider;
import com.system.technologyinformation.module.adapter.TaskBasisItemViewProvider;
import com.system.technologyinformation.module.adapter.TaskPromotionItemViewProvider;
import com.system.technologyinformation.module.adapter.TaskRecordItemViewProvider;
import com.system.technologyinformation.module.contract.TabCategorizeSecondContract;
import com.system.technologyinformation.module.presenter.TabCategorizeSecondPresenter;
import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.users.UserAddressActivity;
import com.system.technologyinformation.module.ui.users.UserInviteCodeActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationAddActivity;
import com.system.technologyinformation.module.ui.users.UserPerfectInformationActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalActivity;
import com.system.technologyinformation.widget.SpaceItemDecoration;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TabCategorizeSecondFragment extends BaseLazyFragment<TabCategorizeSecondPresenter> implements TabCategorizeSecondContract.View, TaskRecordItemViewProvider.TaskRecordInterface, TaskBasisItemViewProvider.TaskRecordInterface, TaskPromotionItemViewProvider.TaskPromotionInterface {

    @BindView(R.id.recycler_view_task)
    RecyclerView recyclerViewTask;

    private Items items;
    private MultiTypeAdapter adapter;

    List<CalendarBean> calendarList;
    List<DailyMissionBean> taskBasisList;
    List<DailyMissionBean> taskPromotionList;
    SpaceItemDecoration spaceItemDecoration;
    IntegralDisplayItemViewProvider integralDisplayItemViewProvider;
    TaskRecordItemViewProvider taskRecordItemViewProvider;
    TaskPromotionItemViewProvider taskPromotionItemViewProvider;
    Intent intent;
    String content;
    int operation = 0;
    int isShow = 0;

    @Override
    protected void loadLazyData() {
        loadUserInfo();
        if (login != null) {
            operation = 1;
            isShow=0;
            getPresenter().getUpdateUserInfoResult(login.getId());
        } else {
            openLogin();
        }
    }

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        addUpdateInfoReceiver();
        calendarList = new ArrayList<>();
        taskBasisList = new ArrayList<>();
        taskPromotionList = new ArrayList<>();

        items = new Items();
        adapter = new MultiTypeAdapter(items);
        integralDisplayItemViewProvider = new IntegralDisplayItemViewProvider();
        integralDisplayItemViewProvider.setLoginBean(login);
        adapter.register(String.class, integralDisplayItemViewProvider);
        taskRecordItemViewProvider = new TaskRecordItemViewProvider();
        taskRecordItemViewProvider.setTaskRecordInterface(this);
        taskRecordItemViewProvider.setLoginBean(login);
        adapter.register(TaskRecordBean.class, taskRecordItemViewProvider);
        TaskBasisItemViewProvider taskBasisItemViewProvider = new TaskBasisItemViewProvider();
        taskBasisItemViewProvider.setTaskRecordInterface(this);
        adapter.register(TaskBasisBean.class, taskBasisItemViewProvider);
        taskPromotionItemViewProvider = new TaskPromotionItemViewProvider();
        taskPromotionItemViewProvider.setTaskPromotionInterface(this);
        adapter.register(TaskPromotionBean.class, taskPromotionItemViewProvider);

        items.add("积分展示");

        TaskRecordBean taskRecordBean = new TaskRecordBean();
        taskRecordBean.setCalendarBeanList(calendarList);
        items.add(taskRecordBean);

        TaskBasisBean taskBasisBean = new TaskBasisBean();
        taskBasisBean.setDailyMissionBeanList(taskBasisList);
        items.add(taskBasisBean);

        TaskPromotionBean taskPromotionBean = new TaskPromotionBean();
        taskPromotionBean.setDailyMissionBeanList(taskPromotionList);
        items.add(taskPromotionBean);
        spaceItemDecoration = new SpaceItemDecoration(0, 30);
        recyclerViewTask.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTask.addItemDecoration(spaceItemDecoration);
        recyclerViewTask.setAdapter(adapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_tab_categorize_second;
    }


    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void setUserSignAddResult(RewardBean rewardBean) {
        hiddenLoading();
        if (rewardBean != null) {
            if (rewardBean.isCheckIn()) {
                toast("签到成功");
                if (login != null) {
                    isShow = 1;
                    getPresenter().getUpdateUserInfoResult(login.getId());
                }
            }
            if (rewardBean.isFull()) {
                new SignQuanSuccessView(getContext(), recyclerViewTask);
            }
        } else {
            toast("签到失败");
        }
    }

    @Override
    public void setUpdateUserInfoResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            taskRecordItemViewProvider.setLoginBean(login);
            integralDisplayItemViewProvider.setLoginBean(login);
            operation = 4;
            getPresenter().getUserSignMonthInfoResult(Calendar.getInstance(), 1);
            if (isShow == 0) {
                getPresenter().getDailyMissionListResult();
                getPresenter().getUserShareContentResult();
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserSignDayInfoResult(List<SignBean> signDayInfoResult) {

    }

    @Override
    public void setUserSignMonthInfoResult(List<String> signMonthInfoResult, int type, Calendar date) {
        hiddenLoading();
        if (signMonthInfoResult != null) {
            calendarList.clear();
            addCalendar("日", 99);
            addCalendar("一", 99);
            addCalendar("二", 99);
            addCalendar("三", 99);
            addCalendar("四", 99);
            addCalendar("五", 99);
            addCalendar("六", 99);
            int mYear = date.get(Calendar.YEAR); // 获取当前年份
            int mMonth = date.get(Calendar.MONTH) + 1;// 获取当前月份
            emptyDataAdd(mYear + "-" + mMonth + "-" + "01");
            for (int i = 1; i < getMonthLastDay(mYear, mMonth) + 1; i++) {
                int number = 0;
                if (signMonthInfoResult != null && signMonthInfoResult.size() > 0) {
                    for (int j = 0; j < signMonthInfoResult.size(); j++) {
                        String[] strDay = signMonthInfoResult.get(j).split("-");
                        if (i < 10) {
                            if (strDay.length == 3 && strDay[2].equals("0" + String.valueOf(i))) {
                                number = 1;
                                Log.i("number", "setUserSignMonthInfoResult: " + number);
                                break;
                            }
                        } else {
                            if (strDay.length == 3 && strDay[2].equals(String.valueOf(i))) {
                                number = 1;
                                Log.i("number", "setUserSignMonthInfoResult: " + number);
                                break;
                            }
                        }
                    }
                }
                addCalendar(i + "", number);
            }
            taskRecordItemViewProvider.setType(type);
            adapter.notifyDataSetChanged();
        } else {
            toast("当月签到信息获取失败");
        }
    }

    @Override
    public void getUserOpenRewardInfoResult(RewardBean rewardBean) {

    }

    @Override
    public void setUserSignRepairAddResult(RewardBean rewardBean) {
        hiddenLoading();
        if (rewardBean != null) {
            if (rewardBean.isCheckIn()) {
                toast("补签成功");
                if (login != null) {
                    isShow = 1;
                    getPresenter().getUpdateUserInfoResult(login.getId());
                }
            }
            if (rewardBean.isFull()) {
                new SignQuanSuccessView(getContext(), recyclerViewTask);
            }
        } else {
            toast("补签失败");
        }
    }

    @Override
    public void setUserLoginResult(LoginBean loginBean) {
        if (loginBean != null) {
            clearUserInfo();
            SPUtils spUtils = new SPUtils("saveUser");
            spUtils.put("userInfo", new Gson().toJson(loginBean));
            loadUserInfo();
            taskRecordItemViewProvider.setLoginBean(login);
            integralDisplayItemViewProvider.setLoginBean(login);
            if (operation == 1) {
                isShow = 0;
                getPresenter().getUpdateUserInfoResult(login.getId());
            } else if (operation == 2) {
                getPresenter().getUserSignAddResult();
            } else if (operation == 3) {
                getPresenter().addUserShareResult();
            } else if (operation == 4) {
                getPresenter().getUserSignMonthInfoResult(Calendar.getInstance(), 1);
                getPresenter().getDailyMissionListResult();
                getPresenter().getUserShareContentResult();
            } else {
                toast("服务未响应，请稍后！");
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setDailyMissionListResult(List<DailyMissionBean> dailyMissionBeanList) {
        if (dailyMissionBeanList != null && dailyMissionBeanList.size() > 0) {
            taskBasisList.clear();
            taskPromotionList.clear();
            for (int i = 0; i < dailyMissionBeanList.size(); i++) {
                DailyMissionBean dailyMissionBean = dailyMissionBeanList.get(i);
                if (dailyMissionBean.getType() == 1) {
                    taskBasisList.add(dailyMissionBean);
                } else {
                    taskPromotionList.add(dailyMissionBean);
                }
            }
            boolean islock = false;
            for (int i = 0; i < taskBasisList.size(); i++) {
                DailyMissionBean dailyMissionBean = taskBasisList.get(i);
                if (dailyMissionBean.isAccomplish() == false) {
                    islock = false;
                    break;
                } else {
                    islock = true;
                }
            }
            taskPromotionItemViewProvider.setIslock(islock);
            adapter.notifyDataSetChanged();
        } else {
            toast("任务列表获取失败");
        }
    }

    @Override
    public void setUserShareContentResult(String str) {
        content = str;
    }

    @Override
    public void getUserShareResult(String str) {
        toast("分享成功");
        getPresenter().getDailyMissionListResult();
        getPresenter().getUserShareContentResult();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1) {
            loadUserInfo();
            taskRecordItemViewProvider.setLoginBean(login);
            integralDisplayItemViewProvider.setLoginBean(login);
            getPresenter().getUserSignMonthInfoResult(Calendar.getInstance(), 1);
            getPresenter().getDailyMissionListResult();
            getPresenter().getUserShareContentResult();
        }
        if (requestCode == 1 && resultCode == 2) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.categoryselect(0);
        }
    }

    public void emptyDataAdd(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int wek = c.get(Calendar.DAY_OF_WEEK);
        if (wek == 1) {
            //mWay = "天";
        } else if (wek == 2) {
            //mWay = "一";
            addCalendar("", 99);
        } else if (wek == 3) {
            //mWay = "二";
            addCalendar("", 99);
            addCalendar("", 99);
        } else if (wek == 4) {
            // mWay = "三";
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
        } else if (wek == 5) {
            //mWay = "四";
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
        } else if (wek == 6) {
            //mWay = "五";
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
        } else if (wek == 7) {
            //mWay = "六";
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
            addCalendar("", 99);
        }
    }


    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public void addCalendar(String content, int number) {
        CalendarBean calendarBean = new CalendarBean();
        calendarBean.setCalendarContent(content);
        calendarBean.setCalendarNumber(number);
        calendarList.add(calendarBean);
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        hiddenLoading();
        if (code == -1) {
            if (login != null && showLog == false) {
                showLog = true;
                getPresenter().getUserLoginResult(login.getPhone(), "", "", login.getToken());
            } else {
                openLogin();
            }
            return;
        } else if (code == -10) {
            return;
        }
        toast(msg);
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
    public void toast(String msg) {
        super.toast(msg);
    }

    @Override
    public void setDailyTaskCheckInOnClickListener() {
        if (login != null) {
            showLoading();
            operation = 2;
            getPresenter().getUserSignAddResult();
        } else {
            openLogin();
        }
    }


    @Override
    public void setTimeReplaceLOnClickListener(Calendar calendar) {
        if (login != null) {
            getPresenter().getUserSignMonthInfoResult(calendar, 2);
        } else {
            openLogin();
        }
    }

    @Override
    public void setTimeReplaceROnClickListener(Calendar calendar) {
        if (login != null) {
            getPresenter().getUserSignMonthInfoResult(calendar, 2);
        } else {
            openLogin();
        }
    }

    @Override
    public void setIntegralRulesClickListener() {
        Intent intent = new Intent(getContext(), QuestionDetailesActivity.class);
        intent.putExtra("title", "积分规则");
        //ApiService.HOST+"gd/index.html#/manage?userId="+login.getId()
        intent.putExtra("url", AppCommonModule.API_BASE_URL + "exchange/index.html");
        startActivity(intent);
    }

    @Override
    public void setCalendarItemClickListener(CalendarBean calendarBean, Calendar calendar) {
        if (login != null) {
            if (calendarBean.getCalendarNumber() == 1) {
                //toast("当前日期已签到，无法补签！");
            } else if (calendarBean.getCalendarNumber() == 99) {
                //填补数据无需处理
            } else {
                Calendar ctime = Calendar.getInstance();
                int mYear = ctime.get(Calendar.YEAR); // 获取当前年份
                int mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
                int day = ctime.get(Calendar.DAY_OF_MONTH);// 获取当前天数
                int content = Integer.valueOf(calendarBean.getCalendarContent()).intValue();
                int selectMonth = calendar.get(Calendar.MONTH) + 1;
                if (content < day && selectMonth == mMonth) {
                    new RetroactiveView(getContext(), recyclerViewTask, 5, mYear + "-" + mMonth + "-" + calendarBean.getCalendarContent());
                } else if (content == day) {
                    getPresenter().getUserSignAddResult();
                } else {
                    //toast("当前日期不能补签");
                }
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setTaskBasisOnClickListener(int index, boolean accomplish, DailyMissionBean dailyMissionBean) {
        if (login != null) {
            if (EmptyUtils.isEmpty(dailyMissionBean.getCode())) {
                return;
            }
            if (dailyMissionBean.getCode().contains("share.link")) {
                if (dailyMissionBean.getShareTotal() < dailyMissionBean.getMaxShare()) {
                    new ShareView(getContext(), recyclerViewTask);
                }
            } else {
                if (!accomplish) {
                    if (EmptyUtils.isEmpty(dailyMissionBean.getCode())) {
                        return;
                    }
                    if (dailyMissionBean.getCode().contains("user.info")) {
                        intent = new Intent(getContext(), UserPerfectInformationActivity.class);
                        startActivityForResult(intent, 2);
                    } else if (dailyMissionBean.getCode().contains("user.construction.place")) {
                        intent = new Intent(getContext(), UserAddressActivity.class);
                        startActivityForResult(intent, 2);
                    } else if (dailyMissionBean.getCode().contains("user.career")) {
                        intent = new Intent(getContext(), UserProfessionalActivity.class);
                        startActivityForResult(intent, 2);
                    }
                }
            }
        } else {
            openLogin();
        }
    }

    @Override
    public void setTaskPromotionOnClickListener(int index, boolean islock, boolean accomplish, DailyMissionBean dailyMissionBean) {
        if (login != null) {
            if (islock) {
                if (accomplish) {
                    return;
                }
                if (dailyMissionBean.getCode().contains("user.identification")) {
                    intent = new Intent(getContext(), UserNameCertificationAddActivity.class);
                    startActivityForResult(intent, 2);
                } else if (dailyMissionBean.getCode().contains("invite.register")) {
                    intent = new Intent(getContext(), UserInviteCodeActivity.class);
                    startActivityForResult(intent, 2);
                }
            } else {
                toast("此任务需要完成基础任务解锁！");
            }
        } else {
            openLogin();
        }
    }


    public class RetroactiveView extends PopupWindow {

        public RetroactiveView(Context mContext, View parent, int point, final String time) {

            View view = View.inflate(mContext, R.layout.user_prompt_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
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
            String contnet = "本次补签需消耗" + point + "积分";
            int end = contnet.length();
            Spannable textSpan = new SpannableStringBuilder(contnet);
            textSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color50)), 7, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tv_content.setText(textSpan);
            btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showLoading();
                    getPresenter().getUserSignRepairAddResult(time);
                    dismiss();
                }
            });
        }
    }

    public class SignQuanSuccessView extends PopupWindow {

        public SignQuanSuccessView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.sign_success_quan_loading, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));

            setWidth(WindowManager.LayoutParams.FILL_PARENT);
            setHeight(WindowManager.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(false);
            setOutsideTouchable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.CENTER, 0, 0);
            TextView btn_dialog_confirm = (TextView) view.findViewById(R.id.btn_dialog_confirm);
            btn_dialog_confirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dismiss();
                }
            });
        }
    }

    public class ShareView extends PopupWindow {

        public ShareView(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.user_share, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.filter));
            // LinearLayout ll_popup = (LinearLayout) view
            // .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_1));
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setWidth(RelativeLayout.LayoutParams.FILL_PARENT);
            setHeight(RelativeLayout.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);

            LinearLayout wxlin = (LinearLayout) view.findViewById(R.id.wxlin);
            LinearLayout wblin = (LinearLayout) view.findViewById(R.id.wblin);
            LinearLayout pqlin = (LinearLayout) view.findViewById(R.id.pqlin);
            LinearLayout qqlin = (LinearLayout) view.findViewById(R.id.qqlin);
            TextView bt3 = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
            wxlin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.WEIXIN);
                    dismiss();
                }
            });
            wblin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.SINA);
                    dismiss();
                }
            });
            pqlin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.WEIXIN_CIRCLE);
                    dismiss();
                }
            });
            qqlin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ShareImage(SHARE_MEDIA.QQ);
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

    private void ShareImage(SHARE_MEDIA share_media) {
        if (share_media == SHARE_MEDIA.QQ) {
            UMImage image = new UMImage(getActivity(), R.mipmap.ic_launcher);
            UMWeb web = new UMWeb("http://gyj-app.idougong.com");
            web.setTitle("邀请下载");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(content);//描述
            new ShareAction(getActivity())
                    .withMedia(web)//分享内容
                    .setPlatform(share_media)
                    .setCallback(umShareListener)
                    .share();
        } else {
            new ShareAction(getActivity())
                    .withText(content)//分享内容
                    .setPlatform(share_media)
                    .setCallback(umShareListener)
                    .share();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            operation = 3;
            getPresenter().addUserShareResult();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //toast("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //toast("分享取消了");
        }
    };




    BroadcastReceiver broadcastReceiver;

    private void addUpdateInfoReceiver() {
        broadcastReceiver = new BroadcastReceiver()

        {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadUserInfo();
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("userupdate");
        getContext().registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            getContext().unregisterReceiver(broadcastReceiver);
        }
    }
}
