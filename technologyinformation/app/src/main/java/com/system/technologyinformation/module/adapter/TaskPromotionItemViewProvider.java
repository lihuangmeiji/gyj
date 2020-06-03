package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.CalendarBean;
import com.system.technologyinformation.model.DailyMissionBean;
import com.system.technologyinformation.model.TaskPromotionBean;
import com.system.technologyinformation.model.TaskRecordBean;

import java.util.Calendar;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class TaskPromotionItemViewProvider extends ItemViewBinder<TaskPromotionBean, TaskPromotionItemViewProvider.ViewHolder> {


    private boolean islock;

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }


    private TaskPromotionInterface taskPromotionInterface;

    public TaskPromotionInterface getTaskPromotionInterface() {
        return taskPromotionInterface;
    }

    public void setTaskPromotionInterface(TaskPromotionInterface taskPromotionInterface) {
        this.taskPromotionInterface = taskPromotionInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_task_basis, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull TaskPromotionBean taskRecordBean) {
        viewHolder.setPosts(taskRecordBean.getDailyMissionBeanList(), isIslock(), getTaskPromotionInterface());
        if (isIslock() == false) {
            viewHolder.ivTaskUnlock.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivTaskUnlock.setVisibility(View.GONE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private DailyMissionAdapter adapter;
        private ImageView ivTaskUnlock;
        private TextView tvTaskBasis;
        private TextView tvTaskBasisTs;
        private TaskPromotionInterface taskPromotionInterface;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            ivTaskUnlock = itemView.findViewById(R.id.iv_task_unlock);
            ivTaskUnlock.setVisibility(View.VISIBLE);
            tvTaskBasis = itemView.findViewById(R.id.tv_task_basis);
            tvTaskBasis.setText("进阶任务");
            tvTaskBasisTs = itemView.findViewById(R.id.tv_task_basis_ts);
            tvTaskBasisTs.setVisibility(View.GONE);
            /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
            adapter = new DailyMissionAdapter(R.layout.item_daily_mission);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    taskPromotionInterface.setTaskPromotionOnClickListener(i, adapter.isIslock(), adapter.getItem(i).isAccomplish(), adapter.getItem(i));
                }
            });
        }

        private void setPosts(List<DailyMissionBean> posts, boolean islock, TaskPromotionInterface taskPromotionInterface) {
            adapter.setIslock(islock);
            adapter.getData().clear();
            adapter.addData(posts);
            adapter.notifyDataSetChanged();
            this.taskPromotionInterface = taskPromotionInterface;
        }
    }

    /**
     * 点击事件
     */
    public interface TaskPromotionInterface {
        void setTaskPromotionOnClickListener(int index, boolean islock, boolean accomplish, DailyMissionBean dailyMissionBean);
    }

}
