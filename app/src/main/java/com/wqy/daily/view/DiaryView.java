package com.wqy.daily.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.RecyclerView;
import com.wqy.daily.adapter.DiaryVH;
import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.event.DiaryEvent;
import com.wqy.daily.model.Diary;
import com.wqy.daily.mvp.ViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wqy on 17-2-5.
 */

public class DiaryView extends ViewImpl {

    @BindView(R.id.diary_rv)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;

    ListRecyclerViewAdapter<Diary> mAdapter;

    @Override
    public int getResId() {
        return R.layout.fragment_diary;
    }

    @Override
    public void created() {
        mUnbinder = ButterKnife.bind(this, mRootView);

        RxBus.get().register(this);
        RxBus.get().post(BusAction.HIDE_TAB_LAYOUT, "");
        RxBus.get().post(BusAction.INIT_DIARY, "");
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
        mUnbinder.unbind();
    }

    @Subscribe(tags = {@Tag(BusAction.SET_FAB)})
    public void setFab(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_create_white_24dp);
    }

    @Produce(tags = {@Tag(BusAction.SET_MAIN_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_diary);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_DIARY)})
    public void setDiary(DiaryEvent event) {
        mAdapter = new ListRecyclerViewAdapter<Diary>() {
            @Override
            public ViewHolder<Diary> onCreateViewHolder(ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.diary_item, null);
                return new DiaryVH(item);
            }
        };
        mAdapter.setDataList(event.getDiaries());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
