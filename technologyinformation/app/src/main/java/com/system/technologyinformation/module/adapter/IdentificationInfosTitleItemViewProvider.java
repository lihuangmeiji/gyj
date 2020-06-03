package com.system.technologyinformation.module.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.technologyinformation.R;
import com.system.technologyinformation.model.LoginBean;

import me.drakeet.multitype.ItemViewBinder;

public class IdentificationInfosTitleItemViewProvider extends ItemViewBinder<String, IdentificationInfosTitleItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_identification_title, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull String s) {

        viewHolder.tvIdenTitle.setText(s);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final TextView tvIdenTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvIdenTitle = (TextView) itemView.findViewById(R.id.tv_iden_title);
        }
    }

}
