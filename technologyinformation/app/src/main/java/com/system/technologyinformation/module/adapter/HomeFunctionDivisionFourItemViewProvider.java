package com.system.technologyinformation.module.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.HomeFunctionDivisionFour;
import com.system.technologyinformation.model.HomeFunctionDivisionTwo;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.module.ui.home.CreditsExchangeDetailedActivity;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class HomeFunctionDivisionFourItemViewProvider extends ItemViewBinder<HomeFunctionDivisionFour,HomeFunctionDivisionFourItemViewProvider.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_home_function_division_four, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull HomeFunctionDivisionFour homeFunctionDivisionFour) {
        viewHolder.setPosts(homeFunctionDivisionFour.getHomeShoppingSpreeBeanList());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private HomeShoppingSpreeAdapter adapter;
        private ViewHolder(@NonNull final View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.post_list);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
            /* adapter 只负责灌输、适配数据，布局交给 LayoutManager，可复用 */
            adapter = new HomeShoppingSpreeAdapter(R.layout.item_home_shopping_spree);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    Intent intent = new Intent(itemView.getContext(), CreditsExchangeDetailedActivity.class);
                    intent.putExtra("shoppingId", adapter.getItem(i).getId());
                    if(adapter.getItem(i).getModel()==1){
                        intent.putExtra("shoppingType", 1);
                    }else{
                        intent.putExtra("shoppingType", 0);
                    }
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private void setPosts(List<HomeShoppingSpreeBean> posts) {
            adapter.getData().clear();
            adapter.addData(posts);
            adapter.notifyDataSetChanged();
        }
    }

}
