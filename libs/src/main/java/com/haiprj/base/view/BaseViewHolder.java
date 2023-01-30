package com.haiprj.base.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T binding;

    public BaseViewHolder(@NonNull T binding) {
        super(((ViewDataBinding)binding).getRoot());
        this.binding = binding;
        //noinspection unchecked
    }

    public abstract void load(Object object);
}
