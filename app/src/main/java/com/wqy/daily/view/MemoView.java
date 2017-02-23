package com.wqy.daily.view;

import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.event.DataEvent;
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.presenter.CreateMemoActivity;
import com.wqy.daily.widget.RecyclerView;
import com.wqy.daily.adapter.GridItemMarginDecoration;
import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.MemoVH;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.event.MemoEvents;
import com.wqy.daily.event.MemoInitEvent;
import com.wqy.daily.model.Memo;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.widget.SwipeRefreshLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wqy on 17-2-5.
 */

public class MemoView extends ViewImpl {

    public static final String TAG = "MemoView";

    @BindView(R.id.memo_vp)
    ViewPager mViewPager;

    private Unbinder mUnbinder;

    private boolean mUnderwayHasMore = true;
    private boolean mFinishedHasMore = true;
    private boolean mDeletedHasMore = true;

    SwipeRefreshLayout mUnderwayLayout;
    SwipeRefreshLayout mFinishedLayout;
    SwipeRefreshLayout mDeletedLayout;

    RecyclerView mUnderwayRV;
    RecyclerView mFinishedRV;
    RecyclerView mDeletedRV;

    ListRecyclerViewAdapter<Memo> mUnderwayAdapter = null;
    ListRecyclerViewAdapter<Memo> mFinishedAdapter = null;
    ListRecyclerViewAdapter<Memo> mDeletedAdapter = null;

    @Override
    public int getResId() {
        return R.layout.fragment_memo;
    }

    @Override
    public void created() {
        mUnbinder = ButterKnife.bind(this, mRootView);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mUnderwayLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.swipe_recyclerview, null);
        mUnderwayRV = (RecyclerView) mUnderwayLayout.findViewById(R.id.swipe_refresh_rv);
        mFinishedLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.swipe_recyclerview, null);
        mFinishedRV = (RecyclerView) mFinishedLayout.findViewById(R.id.swipe_refresh_rv);
        mDeletedLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.swipe_recyclerview, null);
        mDeletedRV = (RecyclerView) mDeletedLayout.findViewById(R.id.swipe_refresh_rv);
        init();
        RxBus.get().register(this);
        refreshData(BusAction.LOAD_MEMO_UNDERWAY);
        refreshData(BusAction.LOAD_MEMO_FINISHED);
        refreshData(BusAction.LOAD_MEMO_DELETED);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
        mUnbinder.unbind();
    }

    public void init() {
        initUnderway();
        initFinished();
        initDeleted();
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(
                mUnderwayLayout, mFinishedLayout, mDeletedLayout
        );
        List<String> titles = Arrays.asList(
                getContext().getString(R.string.tab_underway),
                getContext().getString(R.string.tab_finished),
                getContext().getString(R.string.tab_deleted)
        );
        ListPagerAdapter adapter = new ListPagerAdapter(views, titles);
        mViewPager.setAdapter(adapter);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(BusAction.SET_TAB_LAYOUT)}
    )
    public void setTabLayout(TabLayout tabLayout) {
        Log.d(TAG, "setTabLayout: ");
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        setViewPager();
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_FAB)})
    public void setFab(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_note_add_white_24dp);
        fab.setOnClickListener(v -> RxBus.get().post(BusAction.START_ACTIVITY, CreateMemoActivity.class));
    }

    @Produce(tags = {@Tag(BusAction.SET_MAIN_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_memo);
    }

    private void initSwipeRefreshLayout(SwipeRefreshLayout layout) {
        layout.setColorSchemeColors(mResources.getColor(R.color.colorAccent),
                mResources.getColor(R.color.colorPrimary));
    }

    public void initUnderway() {
        mUnderwayAdapter = new ListRecyclerViewAdapter<Memo>(mUnderwayRV) {
            @Override
            public ViewHolder<Memo> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memo_item, null);
                itemView.setOnClickListener(v -> {
                    Log.d(TAG, "onClick: ");
                    int position = mUnderwayRV.getChildAdapterPosition(v);
                    Memo memo = mUnderwayAdapter.getDataList().get(position);
                    RxBus.get().post(BusAction.VIEW_MEMO, memo);
                });
                return new MemoVH(itemView);
            }
        };
        mUnderwayRV.setAdapter(mUnderwayAdapter);
        mUnderwayRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mUnderwayRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        mUnderwayRV.setOnLoadMoreListener(() -> {
            Log.d(TAG, "onLoadMore: ");
            if (!mUnderwayHasMore) return;
            loadMoreData(BusAction.LOAD_MEMO_UNDERWAY);
        });
        initSwipeRefreshLayout(mUnderwayLayout);
        mUnderwayLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            refreshData(BusAction.LOAD_MEMO_UNDERWAY);
        });
    }

    public void initFinished() {
        mFinishedAdapter = new ListRecyclerViewAdapter<Memo>(mFinishedRV) {
            @Override
            public ViewHolder<Memo> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memo_item, null);
                return new MemoVH(itemView);
            }
        };
        mFinishedRV.setAdapter(mFinishedAdapter);
        mFinishedRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mFinishedRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        mFinishedRV.setOnLoadMoreListener(() -> {
            Log.d(TAG, "onLoadMore: ");
            if (!mFinishedHasMore) return;
            loadMoreData(BusAction.LOAD_MEMO_FINISHED);
        });
        mFinishedLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            refreshData(BusAction.LOAD_MEMO_FINISHED);
        });
    }

    public void initDeleted() {
        mDeletedAdapter = new ListRecyclerViewAdapter<Memo>(mDeletedRV) {
            @Override
            public ViewHolder<Memo> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memo_item, null);
                return new MemoVH(itemView);
            }
        };
        mDeletedRV.setAdapter(mDeletedAdapter);
        mDeletedRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mDeletedRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        mDeletedRV.setOnLoadMoreListener(() -> {
            Log.d(TAG, "onLoadMore: ");
            if (!mDeletedHasMore) return;
            loadMoreData(BusAction.LOAD_MEMO_DELETED);
        });
        mDeletedLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            refreshData(BusAction.LOAD_MEMO_DELETED);
        });
    }

    @Subscribe(tags = {@Tag(BusAction.SET_MEMO_UNDERWAY)})
    public void setMemoUnderway(DataEvent<Memo> event) {
        Log.d(TAG, "setMemoUnderway: ");
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                mUnderwayHasMore = event.isHasMore();
                mUnderwayAdapter.appendData(event.getDatas());
                break;
            case DataEvent.REFRESH:
                mUnderwayHasMore = true;
                mUnderwayAdapter.setDataList(event.getDatas());
                mUnderwayLayout.setRefreshing(false);
                break;
        }
    }

    @Subscribe(tags = {@Tag(BusAction.SET_MEMO_FINISHED)})
    public void setMemoFinished(DataEvent<Memo> event) {
        Log.d(TAG, "setMemoFinished: ");
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                mFinishedHasMore = event.isHasMore();
                mFinishedAdapter.appendData(event.getDatas());
                break;
            case DataEvent.REFRESH:
                mFinishedHasMore = true;
                mFinishedAdapter.setDataList(event.getDatas());
                mFinishedLayout.setRefreshing(false);
                break;
        }
    }

    @Subscribe(tags = {@Tag(BusAction.SET_MEMO_DELETED)})
    public void setMemoDeleted(DataEvent<Memo> event) {
        Log.d(TAG, "setMemoDeleted: ");
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                mDeletedHasMore = event.isHasMore();
                mDeletedAdapter.appendData(event.getDatas());
                break;
            case DataEvent.REFRESH:
                mDeletedHasMore = true;
                mFinishedAdapter.setDataList(event.getDatas());
                mDeletedLayout.setRefreshing(false);
                break;
        }
    }

    private void loadMoreData(String action) {
        RxBus.get().post(action,
                new DataEvent(DataEvent.LOAD_MORE));
    }

    private void refreshData(String action) {
        RxBus.get().post(action,
                new DataEvent(DataEvent.REFRESH));
    }

    @Subscribe(thread = EventThread.IO,
            tags = {@Tag(BusAction.MEMO_DATASET_CHANGED)})
    public void onDatasetChanged(DatasetChangedEvent event) {
        Log.d(TAG, "onDatasetChanged: ");
        refreshData(BusAction.LOAD_MEMO_UNDERWAY);
        refreshData(BusAction.LOAD_MEMO_FINISHED);
        refreshData(BusAction.LOAD_MEMO_DELETED);
    }
}
