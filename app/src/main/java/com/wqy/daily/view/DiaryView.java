package com.wqy.daily.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.event.DataEvent;
import com.wqy.daily.widget.RecyclerView;
import com.wqy.daily.adapter.DiaryVH;
import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.event.DiaryEvent;
import com.wqy.daily.model.Diary;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.presenter.CreateDiaryActivity;
import com.wqy.daily.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wqy on 17-2-5.
 */

public class DiaryView extends ViewImpl {
    public static final String TAG = DiaryView.class.getSimpleName();

    @BindView(R.id.swipe_refresh_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private Unbinder mUnbinder;

    ListRecyclerViewAdapter<Diary> mAdapter;

    private boolean mHasMore = true;

    @Override
    public int getResId() {
        return R.layout.fragment_diary;
    }

    @Override
    public void created() {
        mUnbinder = ButterKnife.bind(this, mRootView);
        init();
        RxBus.get().register(this);
        RxBus.get().post(BusAction.HIDE_TAB_LAYOUT, "");
        RxBus.get().post(BusAction.LOAD_DIARY, new DataEvent<Diary>(DataEvent.REFRESH));
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
        mUnbinder.unbind();
    }

    private void init() {
        mAdapter = new ListRecyclerViewAdapter<Diary>(mRecyclerView) {
            @Override
            public ViewHolder<Diary> onCreateViewHolder(ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, null);
                item.findViewById(R.id.diary_item_bubble).setOnClickListener(v -> {
                    Log.d(TAG, "onClick: ");
                    // TODO: 17-2-24 on Diary item click
                });
                return new DiaryVH(item);
            }
        };
        initRefreshLayout(mRefreshLayout);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnLoadMoreListener(() -> {
            Log.d(TAG, "onLoadMore: ");
            RxBus.get().post(BusAction.LOAD_DIARY,
                    new DataEvent<Diary>(DataEvent.LOAD_MORE));
        });
        mRefreshLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            RxBus.get().post(BusAction.LOAD_DIARY,
                    new DataEvent<Diary>(DataEvent.REFRESH));
        });
    }

    private void initRefreshLayout(SwipeRefreshLayout layout) {
        layout.setColorSchemeColors(mResources.getColor(R.color.colorAccent),
                mResources.getColor(R.color.colorPrimary));
    }

    @Subscribe(tags = {@Tag(BusAction.SET_FAB)})
    public void setFab(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_create_white_24dp);
        fab.setOnClickListener(v -> RxBus.get().post(BusAction.CREATE_DIARY, ""));
    }

    @Produce(tags = {@Tag(BusAction.SET_MAIN_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_diary);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_DIARY)})
    public void setDiary(DataEvent<Diary> event) {
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                mHasMore = event.isHasMore();
                mAdapter.appendData(event.getDatas());
                break;
            case DataEvent.REFRESH:
                mHasMore = true;
                mAdapter.setDataList(event.getDatas());
                mRefreshLayout.setRefreshing(false);
        }
    }
}
