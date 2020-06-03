package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.UserFunctionListBean;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class UserFunctionItemViewProvider extends ItemViewBinder<UserFunctionListBean,UserFunctionItemViewProvider.ViewHolder> {

    private UserFunctionInterface userFunctionInterface;

    public UserFunctionInterface getUserFunctionInterface() {
        return userFunctionInterface;
    }

    public void setUserFunctionInterface(UserFunctionInterface userFunctionInterface) {
        this.userFunctionInterface = userFunctionInterface;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_user_function, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull UserFunctionListBean userFunctionBean) {
        viewHolder.setPosts(userFunctionBean.getUserFunctionBeanList(),getUserFunctionInterface());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private UserFunctionAdapter adapter;
        private UserFunctionInterface userFunctionInterface;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
            /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
            adapter = new UserFunctionAdapter(R.layout.item_home_function);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    userFunctionInterface.setOnClickListener(i);
                }
            });

        }

        private void setPosts(List<UserFunctionListBean.UserFunctionBean> posts,UserFunctionInterface userFunctionInterface) {
            adapter.getData().clear();
            adapter.addData(posts);
            adapter.notifyDataSetChanged();
            this.userFunctionInterface=userFunctionInterface;
        }
    }

    /**
     * 点击事件
     */
    public interface UserFunctionInterface {
        void setOnClickListener(int index);
    }
}
