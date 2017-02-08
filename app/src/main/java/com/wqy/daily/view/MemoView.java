package com.wqy.daily.view;

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
        mUnderwayRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);
        mFinishedRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);
        mDeletedRV = (RecyclerView) inflater.inflate(R.layout.recyclerview, null);

        RxBus.get().register(this);
        RxBus.get().post(BusAction.INIT_MEMO_UNDERWAY, new MemoInitEvent());
        RxBus.get().post(BusAction.INIT_MEMO_FINISHED, new MemoInitEvent());
        RxBus.get().post(BusAction.INIT_MEMO_DELETED, new MemoInitEvent());
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
        mUnbinder.unbind();
    }

    public void setViewPager() {
        List<View> views = Arrays.asList(
                mUnderwayRV, mFinishedRV, mDeletedRV
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

    @Subscribe(tags = {@Tag(BusAction.SET_MEMO_UNDERWAY)})
    public void initUnderway(MemoEvents events) {
        mUnderwayAdapter = new ListRecyclerViewAdapter<Memo>() {
            @Override
            public ViewHolder<Memo> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memo_item, null);
                return new MemoVH(itemView);
            }
        };
        mUnderwayAdapter.setDataList(events.getMemos());
        mUnderwayRV.setAdapter(mUnderwayAdapter);
        mUnderwayRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mUnderwayRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
    }

    @Subscribe(tags = {@Tag(BusAction.SET_MEMO_FINISHED)})
    public void initFinished(MemoEvents events) {
        mFinishedAdapter = new ListRecyclerViewAdapter<Memo>() {
            @Override
            public ViewHolder<Memo> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memo_item, null);
                return new MemoVH(itemView);
            }
        };
        mFinishedAdapter.setDataList(events.getMemos());
        mFinishedRV.setAdapter(mFinishedAdapter);
        mFinishedRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mFinishedRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
    }

    @Subscribe(tags = {@Tag(BusAction.SET_MEMO_DELETED)})
    public void initDeleted(MemoEvents events) {
        mDeletedAdapter = new ListRecyclerViewAdapter<Memo>() {
            @Override
            public ViewHolder<Memo> onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.memo_item, null);
                return new MemoVH(itemView);
            }
        };
        mDeletedAdapter.setDataList(events.getMemos());
        mDeletedRV.setAdapter(mDeletedAdapter);
        mDeletedRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int margin = (int) getContext().getResources().getDimension(R.dimen.card_margin);
        mDeletedRV.addItemDecoration(new GridItemMarginDecoration(2, margin));
    }
}
