package com.wqy.daily.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.wqy.daily.widget.RecyclerView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by wqy on 17-2-5.
 */

public abstract class ListRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder<T>> {

    private List<T> mDataList;
    private com.wqy.daily.widget.RecyclerView mRecyclerView;

    public ListRecyclerViewAdapter(RecyclerView rv) {
        mRecyclerView = rv;
    }

    public void setDataList(List<T> dataList) {
        if (dataList == null) return;
        mRecyclerView.setRefreshing(true);
        if (mDataList != null) {
            int preSize = mDataList.size();
            mDataList.clear();
            notifyItemRangeRemoved(0, preSize);
        }
        mDataList = dataList;
        notifyDataSetChanged();
        mRecyclerView.setRefreshing(false);
    }

    public void appendData(List<T> dataList) {
        if (dataList != null) return;
        if (mDataList == null) {
            setDataList(dataList);
        } else {
            mRecyclerView.setRefreshing(true);
            int start = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(start, dataList.size());
            mRecyclerView.setRefreshing(false);
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public List<T> getDataList() {
        return mDataList;
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
