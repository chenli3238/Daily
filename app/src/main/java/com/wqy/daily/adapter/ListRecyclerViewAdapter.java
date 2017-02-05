package com.wqy.daily.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wqy on 17-2-5.
 */

public abstract class ListRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder<T>> {

    private List<T> mDataList;

    public void setDataList(@NonNull List<T> dataList) {
        mDataList = dataList;
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
