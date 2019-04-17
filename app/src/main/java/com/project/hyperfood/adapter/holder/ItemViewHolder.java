package com.project.hyperfood.adapter.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder<I> extends RecyclerView.ViewHolder{

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    protected View findViewById(@IdRes int viewId) {
        return itemView.findViewById(viewId);
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    public void bindingData(I item, int position) {

    }

}
