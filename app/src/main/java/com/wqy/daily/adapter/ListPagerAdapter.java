package com.wqy.daily.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wqy on 17-2-5.
 */

public class ListPagerAdapter extends PagerAdapter {

    private List<View> mViews;

    private List<String> mTitles;

    public ListPagerAdapter(@NonNull List<View> views, @NonNull List<String> titles) {
        mViews = views;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 :
                mViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null || mTitles.size() <= position ?
                "" : mTitles.get(position);
    }
}
