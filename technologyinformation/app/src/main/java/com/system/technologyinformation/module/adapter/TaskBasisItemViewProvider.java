package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.CalendarBean;
import com.system.technologyinformation.model.DailyMissionBean;
import com.system.technologyinformation.model.TaskBasisBean;
import com.system.technologyinformation.model.TaskRecordBean;

import java.util.Calendar;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class TaskBasisItemViewProvider extends ItemViewBinder<TaskBasisBean,TaskBasisItemViewProvider.ViewHolder> {

    private TaskRecordInterface taskRecordInterface;

    public TaskRecordInterface getTaskRecordInterface() {
        return taskRecordInterface;
    }

    public void setTaskRecordInterface(TaskRecordInterface taskRecordInterface) {
        this.taskRecordInterface = taskRecordInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_task_basis, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull TaskBasisBean taskBasisBean) {
        viewHolder.setPosts(taskBasisBean.getDailyMissionBeanList(),getTaskRecordInterface());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private DailyMissionAdapter adapter;
        private TaskRecordInterface taskRecordInterface;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
            adapter = new DailyMissionAdapter(R.layout.item_daily_mission);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                    taskRecordInterface.setTaskBasisOnClickListener(i,adapter.getItem(i).isAccomplish(),adapter.getItem(i));
                }
            });
        }

        private void setPosts(List<DailyMissionBean> posts,TaskRecordInterface taskRecordInterface) {
            adapter.getData().clear();
            adapter.addData(posts);
            adapter.notifyDataSetChanged();
            this.taskRecordInterface=taskRecordInterface;
        }

    }


    /**
     * 点击事件
     */
    public interface TaskRecordInterface {
        void setTaskBasisOnClickListener(int index, boolean accomplish, DailyMissionBean dailyMissionBean);
    }

}
