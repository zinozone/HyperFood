package com.project.hyperfood.adapter;


import com.project.hyperfood.adapter.holder.ItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractArrayAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    protected List<Object> items = new ArrayList<>();

    public AbstractArrayAdapter() {

    }

    public void add(Object item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<?> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void insert(int position, Object item) {
        items.add(position, item);
        notifyDataSetChanged();
    }


    public void remove(Object post) {
        items.remove(post);
        notifyDataSetChanged();
    }

    public void remove(int position){
        items.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll(List<Object> posts) {
        items.removeAll(posts);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public List<?> getItems() {
        return items;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindingData(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
