package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.CalendarBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.TaskRecordBean;
import com.system.technologyinformation.model.UserFunctionListBean;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import me.drakeet.multitype.ItemViewBinder;

public class TaskRecordItemViewProvider extends ItemViewBinder<TaskRecordBean, TaskRecordItemViewProvider.ViewHolder> {

    private TaskRecordInterface taskRecordInterface;

    public TaskRecordInterface getTaskRecordInterface() {
        return taskRecordInterface;
    }

    public void setTaskRecordInterface(TaskRecordInterface taskRecordInterface) {
        this.taskRecordInterface = taskRecordInterface;
    }

    private LoginBean loginBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_task_record, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull TaskRecordBean taskRecordBean) {
        viewHolder.setPosts(taskRecordBean.getCalendarBeanList(), getTaskRecordInterface(), getType());
        if (getLoginBean() != null) {
            if (getLoginBean().isCheckIn()) {
                viewHolder.tvDailyTaskCheckIn.setText("今日已签到");
                viewHolder.tvDailyTaskCheckIn.setEnabled(false);
                viewHolder.tvDailyTaskCheckIn.setSelected(false);
            } else {
                viewHolder.tvDailyTaskCheckIn.setText("签到");
                viewHolder.tvDailyTaskCheckIn.setEnabled(true);
                viewHolder.tvDailyTaskCheckIn.setSelected(true);
            }
        } else {
            viewHolder.tvDailyTaskCheckIn.setText("签到");
            viewHolder.tvDailyTaskCheckIn.setEnabled(true);
            viewHolder.tvDailyTaskCheckIn.setSelected(true);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private CalendarAdapter adapter;
        private TextView tvTaskRecordMonth;
        private TextView tvDailyTaskCheckIn;
        private TextView tvIntegralRules;
        private Calendar ctime;
        private TaskRecordInterface taskRecordInterface;
        private ImageView ivTimeReplaceL;
        private ImageView ivTimeReplaceR;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskRecordMonth = itemView.findViewById(R.id.tv_task_record_month);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 7));
            /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
            adapter = new CalendarAdapter(R.layout.item_calendar);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    taskRecordInterface.setCalendarItemClickListener(adapter.getItem(i), ctime);
                }
            });
            ctime = Calendar.getInstance();
            int mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
            tvTaskRecordMonth.setText(mMonth + "月");
            tvDailyTaskCheckIn = itemView.findViewById(R.id.tv_daily_task_check_in);
            tvDailyTaskCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskRecordInterface.setDailyTaskCheckInOnClickListener();
                }
            });
            tvIntegralRules = itemView.findViewById(R.id.tv_integral_rules);
            String src1 = "签到说明：（1）橙色实心标记说明该天已完成签到（2）补字空心标记说明当天未签到（3）该月签满一个自然月可获得全勤奖励500积分（4）补签一次需耗费5积分";
            String src2 = "查看更多积分详情>";
            String src = src1 + src2;
            SpannableStringBuilder spanStr = new SpannableStringBuilder(src);
            spanStr.setSpan(new TextAppearanceSpan(itemView.getContext(), R.style.tv_integral_rules1), 0, src1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanStr.setSpan(new TextAppearanceSpan(itemView.getContext(), R.style.tv_integral_rules2), src1.length(), src.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvIntegralRules.setText(spanStr);
            tvIntegralRules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskRecordInterface.setIntegralRulesClickListener();
                }
            });
            ivTimeReplaceL = itemView.findViewById(R.id.iv_time_replace_l);
            ivTimeReplaceL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctime == null) {
                        ctime = Calendar.getInstance();
                    }
                    ctime.add(Calendar.MONTH, -1);
                    int mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
                    tvTaskRecordMonth.setText(mMonth + "月");
                    taskRecordInterface.setTimeReplaceLOnClickListener(ctime);
                }
            });
            ivTimeReplaceR = itemView.findViewById(R.id.iv_time_replace_r);
            ivTimeReplaceR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctime == null) {
                        ctime = Calendar.getInstance();
                    }
                    ctime.add(Calendar.MONTH, 1);
                    int mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
                    tvTaskRecordMonth.setText(mMonth + "月");
                    taskRecordInterface.setTimeReplaceROnClickListener(ctime);
                }
            });

        }

        private void setPosts(List<CalendarBean> posts, TaskRecordInterface taskRecordInterface, int type) {
            int mMonth;
            int mYear;
            if (type == 1) {
                ctime = Calendar.getInstance();
                mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
                mYear = ctime.get(Calendar.YEAR);
                tvTaskRecordMonth.setText(mMonth + "月");
            } else {
                mMonth = ctime.get(Calendar.MONTH) + 1;// 获取当前月份
                mYear = ctime.get(Calendar.YEAR);
            }
            adapter.setmMonth(mMonth);
            adapter.setYear(mYear);
            adapter.getData().clear();
            adapter.addData(posts);
            adapter.notifyDataSetChanged();
            this.taskRecordInterface = taskRecordInterface;
        }
    }


    /**
     * 点击事件
     */
    public interface TaskRecordInterface {
        void setDailyTaskCheckInOnClickListener();

        void setCalendarItemClickListener(CalendarBean calendarBean, Calendar calendar);

        void setTimeReplaceLOnClickListener(Calendar calendar);

        void setTimeReplaceROnClickListener(Calendar calendar);

        void setIntegralRulesClickListener();
    }

}
