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
import com.wqy.daily.event.DatasetChangedEvent;
import com.wqy.daily.widget.RecyclerView;
import com.wqy.daily.adapter.BigdayBackwardVH;
import com.wqy.daily.adapter.BigdayForwardVH;
import com.wqy.daily.adapter.GridItemMarginDecoration;
import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.event.DataEvent;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.model.Bigday;
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

public class BigdayView extends ViewImpl {

    public static final String TAG = "BigdayView";

    @BindView(R.id.bigday_vp)
    ViewPager mViewPager;

    private Unbinder mUnbinder;

    SwipeRefreshLayout mBackwardLayout;
    SwipeRefreshLayout mForwardLayout;

    RecyclerView mBackwardRV;
    RecyclerView mForwardRV;

    ListRecyclerViewAdapter<Bigday> mBackwardAdapter = null;
    ListRecyclerViewAdapter<Bigday> mForwardAdapter = null;

    private boolean mBackwardHasMore = true;
    private boolean mForwardHasMore = true;

    private Resources mResources;

    @Override
    public int getResId() {
        return R.layout.fragment_bigday;
    }

    @Override
    public void created() {
        mUnbinder = ButterKnife.bind(this, mRootView);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        mBackwardLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.swipe_recyclerview, null);
        mBackwardRV = (RecyclerView) mBackwardLayout.findViewById(R.id.swipe_refresh_rv);
        mForwardLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.swipe_recyclerview, null);
        mForwardRV = (RecyclerView) mForwardLayout.findViewById(R.id.swipe_refresh_rv);
        mResources = getContext().getResources();
        init();

        RxBus.get().register(this);
        RxBus.get().post(BusAction.LOAD_BIGDAY_BACKWARD, new DataEvent(DataEvent.REFRESH));
        RxBus.get().post(BusAction.LOAD_BIGDAY_FORWARD, new DataEvent(DataEvent.REFRESH));
    }

    private void init() {
        initBackward();
        initForward();
    }

    private void initSwipeRefreshLayout(SwipeRefreshLayout layout) {
        layout.setColorSchemeColors(mResources.getColor(R.color.colorAccent),
                mResources.getColor(R.color.colorPrimary));
    }

    public void initBackward() {
        mBackwardAdapter = new ListRecyclerViewAdapter<Bigday>(mBackwardRV) {
            @Override
            public ViewHolder<Bigday> onCreateViewHolder(ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bigday_backward_item, null);
                item.setOnClickListener(v -> {
                    Log.d(TAG, "onClick: ");
                    int position = mBackwardRV.getChildAdapterPosition(v);
                    Bigday bigday = mBackwardAdapter.getDataList().get(position);
                    RxBus.get().post(BusAction.VIEW_BIGDAY, bigday);
                });
                return new BigdayBackwardVH(item);
            }
        };

        mBackwardRV.setAdapter(mBackwardAdapter);
        mBackwardRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mBackwardRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        mBackwardRV.setOnLoadMoreListener(() -> {
            Log.d(TAG, "onLoadMore: ");
            if (!mBackwardHasMore) return;
            RxBus.get().post(BusAction.LOAD_BIGDAY_BACKWARD,
                    new DataEvent(DataEvent.LOAD_MORE));
        });
        initSwipeRefreshLayout(mBackwardLayout);
        mBackwardLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            refreshData(BusAction.LOAD_BIGDAY_BACKWARD);
        });
    }

    public void initForward() {
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mForwardAdapter = new ListRecyclerViewAdapter<Bigday>(mForwardRV) {
            @Override
            public ViewHolder<Bigday> onCreateViewHolder(ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bigday_forward_item, null);
                item.setOnClickListener(v -> {
                    Log.d(TAG, "onClick: ");
                    int position = mForwardRV.getChildAdapterPosition(v);
                    Bigday bigday = mForwardAdapter.getDataList().get(position);
                    RxBus.get().post(BusAction.VIEW_BIGDAY, bigday);
                });
                return new BigdayForwardVH(item);
            }
        };

        mForwardRV.setAdapter(mForwardAdapter);
        mForwardRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mForwardRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        mForwardRV.setOnLoadMoreListener(() -> {
            Log.d(TAG, "onLoadMore: ");
            if (!mForwardHasMore) return;
            RxBus.get().post(BusAction.LOAD_BIGDAY_FORWARD,
                    new DataEvent(DataEvent.LOAD_MORE));
        });
        initSwipeRefreshLayout(mForwardLayout);
        mForwardLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            refreshData(BusAction.LOAD_BIGDAY_FORWARD);
        });
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
        mUnbinder.unbind();
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(
                mBackwardLayout, mForwardLayout
        );
        List<String> titles = Arrays.asList(
                getContext().getString(R.string.tab_backward),
                getContext().getString(R.string.tab_forward)
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
        fab.setImageResource(R.drawable.ic_insert_invitation_white_24dp);
        fab.setOnClickListener(v -> RxBus.get().post(BusAction.CREATE_BIGDAY, ""));
    }

    @Produce(tags = {@Tag(BusAction.SET_MAIN_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_bigday);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_BIGDAY_BACKWARD)})
    public void setBigdayBackward(DataEvent<Bigday> event) {
        Log.d(TAG, "setBigdayBackward: " + event.getAction());
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                mBackwardHasMore = event.isHasMore();
                mBackwardAdapter.appendData(event.getDatas());
                break;
            case DataEvent.REFRESH:
                mBackwardHasMore = true;
                mBackwardAdapter.setDataList(event.getDatas());
                mBackwardLayout.setRefreshing(false);
                break;
        }
    }

    @Subscribe(tags = {@Tag(BusAction.SET_BIGDAY_FORWARD)})
    public void setBigdayForward(DataEvent<Bigday> event) {
        Log.d(TAG, "setBigdayForward: " + event.getAction());
        switch (event.getAction()) {
            case DataEvent.LOAD_MORE:
                mForwardHasMore = event.isHasMore();
                mForwardAdapter.appendData(event.getDatas());
                break;
            case DataEvent.REFRESH:
                mForwardHasMore = true;
                mForwardAdapter.setDataList(event.getDatas());
                mForwardLayout.setRefreshing(false);
                break;
        }
    }

    private void refreshData(String action) {
        RxBus.get().post(action,
                new DataEvent(DataEvent.REFRESH));
    }

    @Subscribe(tags = {@Tag(BusAction.BIGDAY_DATASET_CHANGED)})
    public void onDatasetChanged(DatasetChangedEvent event) {
        Log.d(TAG, "onDatasetChanged: ");
        refreshData(BusAction.LOAD_BIGDAY_BACKWARD);
        refreshData(BusAction.LOAD_BIGDAY_FORWARD);
    }
}
