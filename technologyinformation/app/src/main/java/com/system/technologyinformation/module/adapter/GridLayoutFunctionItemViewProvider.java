package com.system.technologyinformation.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeFunctionListBean;
import com.system.technologyinformation.module.ui.home.EnrollmentActivity;
import com.wenld.multitypeadapter.base.MultiItemView;
import com.wenld.multitypeadapter.base.ViewHolder;

import java.util.List;



public class GridLayoutFunctionItemViewProvider extends MultiItemView<HomeFunctionListBean> {

    Context context;

    public FunctionItemInterface getFunctionItemInterface() {
        return functionItemInterface;
    }

    public void setFunctionItemInterface(FunctionItemInterface functionItemInterface) {
        this.functionItemInterface = functionItemInterface;
    }

    private  FunctionItemInterface functionItemInterface;


    public GridLayoutFunctionItemViewProvider(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_function_horizontal;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull HomeFunctionListBean homeFunctionListBean, int i) {
        RecyclerView recyclerView = (RecyclerView) viewHolder.getView(R.id.post_list);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
        HomeFunctionAdapter homeFunctionAdapter = new HomeFunctionAdapter(R.layout.item_home_function);
        homeFunctionAdapter.addData(homeFunctionListBean.getHomeFunctionBeanList());
        recyclerView.setAdapter(homeFunctionAdapter);
        homeFunctionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                functionItemInterface.onItemClick(position);
            }
        });
        ImageView iv_title=(ImageView) viewHolder.getView(R.id.iv_title);
        iv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionItemInterface.onItemClick(0);
            }
        });
    }

    /**
     * 首页中部功能点击事件
     */
    public interface FunctionItemInterface {
        void onItemClick(int position);
    }
}
