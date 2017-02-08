package com.wqy.daily.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.mvp.ViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-8.
 */

public class CreateDiaryView extends ViewImpl {

    @BindView(R.id.cdiary_toolbar)
    Toolbar mToolbar;

    @Override
    public int getResId() {
        return R.layout.activity_create_diary;
    }

    @Override
    public int getMenuId() {
        return R.menu.activity_cdiary;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // nav to parent
                return true;
            case R.id.cdiary_confirm:
                // create a diary
                return true;
            case R.id.cdiary_edit:
                item.setVisible(false);
                getMenu().findItem(R.id.cdiary_confirm).setVisible(true);
                return true;
            default:
                return false;
        }
    }

    @Produce(tags = {@Tag(BusAction.SET_CDIARY_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.cdiary_title);
    }
}
