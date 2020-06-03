package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeFunctionDivisionTwo;
import com.system.technologyinformation.model.HomeMessage;
import com.system.technologyinformation.model.UserMessage;
import com.system.technologyinformation.model.UserServiceFunctionListBean;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class HomeFunctionDivisionTwoItemViewProvider extends ItemViewBinder<HomeFunctionDivisionTwo,HomeFunctionDivisionTwoItemViewProvider.ViewHolder> {

    private HomeFunctionDivisionTwoInterface homeFunctionDivisionTwoInterface;

    public HomeFunctionDivisionTwoInterface getHomeFunctionDivisionTwoInterface() {
        return homeFunctionDivisionTwoInterface;
    }

    public void setHomeFunctionDivisionTwoInterface(HomeFunctionDivisionTwoInterface homeFunctionDivisionTwoInterface) {
        this.homeFunctionDivisionTwoInterface = homeFunctionDivisionTwoInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_home_message_function, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull HomeFunctionDivisionTwo homeFunctionDivisionTwo) {
        viewHolder.setPosts(homeFunctionDivisionTwo.getHomeMessageList(),getHomeFunctionDivisionTwoInterface());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private LinearLayout linHomeMessage;
        private HomeMessageAdapter adapter;
        private HomeFunctionDivisionTwoInterface homeFunctionDivisionTwoInterface;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
            adapter = new HomeMessageAdapter(R.layout.item_home_message);
            recyclerView.setAdapter(adapter);
            linHomeMessage=itemView.findViewById(R.id.lin_home_message);
            linHomeMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeFunctionDivisionTwoInterface.setHomeMessageOnClickListener();
                }
            });
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    homeFunctionDivisionTwoInterface.setHomeMessageOnClickListener();
                }
            });
        }

        private void setPosts(List<String> posts,HomeFunctionDivisionTwoInterface homeFunctionDivisionTwoInterface) {
            adapter.getData().clear();
            adapter.addData(posts);
            adapter.notifyDataSetChanged();
            this.homeFunctionDivisionTwoInterface=homeFunctionDivisionTwoInterface;
        }
    }

    /**
     * 点击事件
     */
    public interface HomeFunctionDivisionTwoInterface {
        void setHomeMessageOnClickListener();
    }
}
