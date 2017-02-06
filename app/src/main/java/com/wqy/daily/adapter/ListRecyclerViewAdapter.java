package com.wqy.daily.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by wqy on 17-2-5.
 */

public abstract class ListRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder<T>> {

    private List<T> mDataList;

    public void setDataList(@NonNull List<T> dataList) {
        if (mDataList != dataList) {
            mDataList = dataList;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder<T> holder, int position) {
        holder.bindView(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 :
                mDataList.size();
    }
}
