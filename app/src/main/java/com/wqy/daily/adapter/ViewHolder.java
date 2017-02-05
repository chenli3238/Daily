package com.wqy.daily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wqy on 17-2-5.
 */

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T data);
}
