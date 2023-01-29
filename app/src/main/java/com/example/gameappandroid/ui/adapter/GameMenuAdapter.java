package com.example.gameappandroid.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.ItemMenuBinding;
import com.haiprj.base.BaseAdapter;
import com.haiprj.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GameMenuAdapter extends BaseAdapter<ItemMenuBinding, String> {

    public GameMenuAdapter(Context context, List<String> list) {
        super(context, list);
    }

    public GameMenuAdapter() {
        super();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public BaseViewHolder<ItemMenuBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void setOnViewItemClickListener(OnViewItemClickListener onViewItemClickListener) {
        super.setOnViewItemClickListener(onViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemMenuBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void update(List<String> list) {
        super.update(list);
    }

    @Override
    protected BaseViewHolder<ItemMenuBinding> getViewHolder() {
        return new ViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    public class ViewHolder extends BaseViewHolder<ItemMenuBinding> {

        public ViewHolder(@NonNull ViewDataBinding binding) {
            super((ItemMenuBinding) binding);
        }

        @Override
        public void load(Object object) {
            String item = (String) object;
            binding.itemTv.setText(item);
            binding.itemBtn.setOnClickListener(view -> {
                onViewItemClickListener.onClick(getPosition());
            });
        }
    }
}
