package com.wqy.daily.view;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.widget.DateTimePickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-8.
 */

public class CreateDiaryView extends ViewImpl {
    public static final String TAG = CreateDiaryView.class.getSimpleName();

    @BindView(R.id.cdiary_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cdiary_text)
    EditText etText;

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
                // TODO: 17-2-8 nav to parent activity and fragment
                NavUtils.navigateUpFromSameTask((Activity) getContext());
                return true;
            case R.id.cdiary_confirm:
                // TODO: 17-2-8 create a diary
                confirm();
                return true;
            case R.id.cdiary_edit:
                enableEdit();
                // TODO: 17-2-8 enable edit
                return true;
            default:
                return false;
        }
    }

    private void confirm() {
        Log.d(TAG, "confirm: ");
        RxBus.get().post(BusAction.CREATE_DIARY, etText.getText().toString());
    }

    @Produce(tags = {@Tag(BusAction.SET_CDIARY_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.cdiary_title);
    }

    @Subscribe(tags = {@Tag(BusAction.CDIARY_EDITABLE)})
    public void setEditable(Boolean b) {
        if (b) {
            enableEdit();
        } else {
            disableEdit();
        }
    }


    public void enableEdit() {
        etText.setEnabled(true);
        getMenu().findItem(R.id.cdiary_confirm).setVisible(true);
        getMenu().findItem(R.id.cdiary_edit).setVisible(false);
    }

    public void disableEdit() {
        etText.setEnabled(false);
        getMenu().findItem(R.id.cdiary_confirm).setVisible(false);
        getMenu().findItem(R.id.cdiary_edit).setVisible(true);
    }
}
