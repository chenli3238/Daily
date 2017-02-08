package com.wqy.daily.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.adapter.GridItemMarginDecoration;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.widget.RecyclerView;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.PunchDeletedVH;
import com.wqy.daily.adapter.PunchFinishedVH;
import com.wqy.daily.adapter.PunchUnderwayVH;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.event.PunchEvents;
import com.wqy.daily.event.PunchInitEvent;
import com.wqy.daily.model.Event;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.presenter.CreatePunchActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wqy on 17-2-4.
 */

public class PunchView extends ViewImpl {

    public static final String TAG = "PunchView";

    @BindView(R.id.punch_vp)
    ViewPager mViewPager;

    private Unbinder mUnbinder;

    RecyclerView mUnderwayRV;
    RecyclerView mFinishedRV;
    RecyclerView mDeletedRV;

    ListRecyclerViewAdapter<Event> mUnderwayAdapter = null;
    ListRecyclerViewAdapter<Event> mFinishedAdapter = null;
    ListRecyclerViewAdapter<Event> mDeletedAdapter = null;


    @Override
    public int getResId() {
        return R.layout.fragment_punch;
    }

    @Override
    public void created() {
        Log.d(TAG, "created: ");
        mUnbinder = ButterKnife.bind(this, mRootView);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mUnderwayRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);
        mFinishedRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);
        mDeletedRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);

        RxBus.get().register(this);
        RxBus.get().post(BusAction.INIT_PUNCH_UNDERWAY, new PunchInitEvent());
        RxBus.get().post(BusAction.INIT_PUNCH_FINISHED, new PunchInitEvent());
        RxBus.get().post(BusAction.INIT_PUNCH_DELETED, new PunchInitEvent());
    }

    @Override
    public void destroy() {
        Log.d(TAG, "destroy: ");
        RxBus.get().unregister(this);
        mUnbinder.unbind();
    }

    @Override
    public void start() {
        Log.d(TAG, "start: ");
//        RxBus.get().register(this);
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop: ");
//        RxBus.get().unregister(this);
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(mUnderwayRV, mFinishedRV, mDeletedRV);
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
        fab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(1000)
                .start();
        fab.setImageResource(R.drawable.ic_turned_in_white_24dp);
        fab.setOnClickListener(v -> {
            RxBus.get().post(BusAction.START_ACTIVITY, CreatePunchActivity.class);
        });
    }

    @Produce(tags = {@Tag(BusAction.SET_MAIN_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_punch);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_PUNCH_UNDERWAY)})
    public void initUnderway(PunchEvents events) {
        mUnderwayAdapter = new ListRecyclerViewAdapter<Event>() {
            @Override
            public ViewHolder<Event> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.punch_underway_item, null);
                return new PunchUnderwayVH(itemView);
            }
        };
        mUnderwayAdapter.setDataList(events.getEvents());
        mUnderwayRV.setAdapter(mUnderwayAdapter);
        mUnderwayRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mUnderwayRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        Log.d(TAG, "initUnderway: " + mUnderwayRV.getWidth());
    }

    @Subscribe(tags = {@Tag(BusAction.SET_PUNCH_FINISHED)})
    public void initFinished(PunchEvents events) {
        mFinishedAdapter = new ListRecyclerViewAdapter<Event>() {
            @Override
            public ViewHolder<Event> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.punch_finished_item, null);
                return new PunchFinishedVH(itemView);
            }
        };
        mFinishedAdapter.setDataList(events.getEvents());
        mFinishedRV.setAdapter(mFinishedAdapter);
        mFinishedRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mFinishedRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        Log.d(TAG, "initFinished: " + mFinishedRV.getWidth());
    }

    @Subscribe(tags = {@Tag(BusAction.SET_PUNCH_DELETED)})
    public void initDeleted(PunchEvents events) {
        mDeletedAdapter = new ListRecyclerViewAdapter<Event>() {
            @Override
            public ViewHolder<Event> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.punch_deleted_item, null);
                return new PunchDeletedVH(itemView);
            }
        };
        mDeletedAdapter.setDataList(events.getEvents());
        mDeletedRV.setAdapter(mDeletedAdapter);
        mDeletedRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mDeletedRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
        Log.d(TAG, "initDeleted: " + mDeletedRV.getWidth());
    }
}
