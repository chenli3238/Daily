package com.wqy.daily.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.wqy.daily.RecyclerView;
import com.wqy.daily.adapter.BigdayBackwardVH;
import com.wqy.daily.adapter.BigdayForwardVH;
import com.wqy.daily.adapter.BigdayInitEvent;
import com.wqy.daily.adapter.GridItemMarginDecoration;
import com.wqy.daily.adapter.ListRecyclerViewAdapter;
import com.wqy.daily.adapter.ViewHolder;
import com.wqy.daily.event.BigdayEvent;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.R;
import com.wqy.daily.adapter.ListPagerAdapter;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.mvp.ViewImpl;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-5.
 */

public class BigdayView extends ViewImpl {

    public static final String TAG = "BigdayView";

    @BindView(R.id.bigday_vp)
    ViewPager mViewPager;

    RecyclerView mBackwardRV;
    RecyclerView mForwardRV;

    ListRecyclerViewAdapter<Bigday> mBackwardAdapter = null;
    ListRecyclerViewAdapter<Bigday> mForwardAdapter = null;

    @Override
    public int getResId() {
        return R.layout.fragment_bigday;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        RxBus.get().register(this);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        mBackwardRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);
        mForwardRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);

        RxBus.get().post(BusAction.INIT_BIGDAY_BACKWARD, new BigdayInitEvent());
        RxBus.get().post(BusAction.INIT_BIGDAY_FORWARD, new BigdayInitEvent());
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(
                mBackwardRV, mForwardRV
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
    }

    @Produce(tags = {@Tag(BusAction.SET_ACTIVITY_TITLE)})
    public String getTitle() {
        return getContext().getString(R.string.title_bigday);
    }

    @Subscribe(tags = {@Tag(BusAction.SET_BIGDAY_BACKWARD)})
    public void setUnderway(BigdayEvent event) {
        mBackwardAdapter = new ListRecyclerViewAdapter<Bigday>() {
            @Override
            public ViewHolder<Bigday> onCreateViewHolder(ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bigday_backward_item, null);
                return new BigdayBackwardVH(item);
            }
        };
        mBackwardAdapter.setDataList(event.getBigdays());
        mBackwardRV.setAdapter(mBackwardAdapter);
        mBackwardRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mBackwardRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
    }

    @Subscribe(tags = {@Tag(BusAction.SET_BIGDAY_FORWARD)})
    public void setBigdayForward(BigdayEvent event) {
        mForwardAdapter = new ListRecyclerViewAdapter<Bigday>() {
            @Override
            public ViewHolder<Bigday> onCreateViewHolder(ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bigday_forward_item, null);
                return new BigdayForwardVH(item);
            }
        };
        mForwardAdapter.setDataList(event.getBigdays());
        mForwardRV.setAdapter(mForwardAdapter);
        mForwardRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mForwardRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
    }
}
