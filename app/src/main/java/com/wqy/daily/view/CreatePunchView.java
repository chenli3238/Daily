package com.wqy.daily.view;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.CommonUtils;
import com.wqy.daily.R;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.event.NumberPickerEvent;
import com.wqy.daily.event.ShowDialogEvent;
import com.wqy.daily.model.DayTime;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.widget.BooleanPickerFragment;
import com.wqy.daily.widget.DateTimePickerFragment;
import com.wqy.daily.widget.DayTimePickerFragment;
import com.wqy.daily.widget.ListPickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wqy on 17-2-7.
 */

public class CreatePunchView extends ViewImpl {
    public static final String TAG = CreatePunchView.class.getSimpleName();

    @BindView(R.id.cpunch_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cpunch_title_layout)
    TextInputLayout tilTitle;

    @BindView(R.id.cpunch_title)
    TextInputEditText tvTitle;

    @BindView(R.id.cpunch_desc_layout)
    TextInputLayout tilDesc;

    @BindView(R.id.cpunch_desc)
    TextInputEditText tvDesc;

    @BindView(R.id.cpunch_aim_value)
    TextView tvAim;

    @BindView(R.id.cpunch_time_value)
    TextView tvTime;

    @BindView(R.id.cpunch_duration_value)
    TextView tvDuration;

    @BindView(R.id.cpunch_remind_value)
    TextView tvRemind;

    @BindView(R.id.cpunch_priority_value)
    TextView tvPriority;

    private Resources mResources;

    private String[] mAims;
    private String[] mPriorities;

    @Override
    public int getResId() {
        return R.layout.activity_create_punch;
    }

    @Override
    public int getMenuId() {
        return R.menu.activity_cpunch;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mResources = getContext().getResources();
        mAims = mResources.getStringArray(R.array.cpunch_aims);
        mPriorities = mResources.getStringArray(R.array.priority);
        RxBus.get().register(this);
    }

    @Override
    public void bindEvent() {
        tvTitle.addTextChangedListener(new AfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                verifyTitle();
            }
        });
        tvDesc.addTextChangedListener(new AfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                verifyDesc();
            }
        });
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask((Activity) getContext());
                return true;
            case R.id.cpunch_confirm:
                // create a new punch event
                confirm();
                return true;
            default:
                return false;
        }
    }

    @Produce(tags = {@Tag(BusAction.SET_CPUNCH_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.title_cpunch);
    }

    private void confirm() {
        Log.d(TAG, "confirm: ");
        if (verifyTitle() && verifyDesc()) {
            RxBus.get().post(BusAction.PUNCH_TITLE_DESC, new String[] {
                    tvTitle.getText().toString(),
                    tvDesc.getText().toString()
            });
            RxBus.get().post(BusAction.CREATE_PUNCH, "");
        }
    }

    private boolean verifyTitle() {
        String title = tvTitle.getText().toString();
        boolean lengthNotNull = title != null && title.length() > 0;
        if (!lengthNotNull) {
            tilTitle.setError(mResources.getString(R.string.cpunch_title_required));

            return false;
        }
        boolean lengthLessThanMax = title.length() < tilTitle.getCounterMaxLength();
        if (!lengthLessThanMax) {
            tilTitle.setError(mResources.getString(R.string.cpunch_out_of_maxlen));
            return false;
        }
        tilTitle.setError(null);
        return true;
    }

    private boolean verifyDesc() {
        boolean ok = tvDesc.getText().toString().length() < tilDesc.getCounterMaxLength();
        if (!ok) {
            tilDesc.setError(mResources.getString(R.string.cpunch_out_of_maxlen));
            return false;
        }
        tilDesc.setError(null);
        return true;
    }

    private void showDialog(String tag, DialogFragment fragment) {
        mIPresenter.showDialog(tag, fragment);
    }

    private void showListPickerDialog(int titleId, int itemArrayId, int defaultValue, String eventTag) {
        DialogFragment fragment = new ListPickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ListPickerFragment.ARG_TITLE, mResources.getString(titleId));
        bundle.putStringArray(ListPickerFragment.ARG_ITEM_TITLES, mResources.getStringArray(itemArrayId));
        bundle.putString(ListPickerFragment.ARG_EVENT_TAG, eventTag);
        bundle.putInt(ListPickerFragment.ARG_DEFAULT_VALUE, defaultValue);
        fragment.setArguments(bundle);
        showDialog(ListPickerFragment.TAG, fragment);
    }

    @OnClick(R.id.cpunch_aim)
    void showAimDialog() {
        showListPickerDialog(R.string.cpunch_aim_title,
                R.array.cpunch_aims,
                3, BusAction.NUMBER_PICKER_RESULT);
    }

    @OnClick(R.id.cpunch_time)
    void showDayTimeDialog() {
        DialogFragment fragment = new DayTimePickerFragment();
        showDialog(DateTimePickerFragment.TAG, fragment);
    }

    private void showBooleanDialog(String message, String eventTag) {
        DialogFragment fragment = new BooleanPickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BooleanPickerFragment.ARG_MESSAGE, message);
        bundle.putString(BooleanPickerFragment.ARG_EVENT_TAG, eventTag);
        fragment.setArguments(bundle);
        showDialog(BooleanPickerFragment.TAG, fragment);
    }

    @OnClick(R.id.cpunch_duration)
    void showDurationDialog() {
        showBooleanDialog(mResources.getString(R.string.cpunch_time_title),
                BusAction.PUNCH_DURATION);
    }

    @OnClick(R.id.cpunch_remind)
    void showRemindDialog() {
        showBooleanDialog(mResources.getString(R.string.cpunch_remind_title),
                BusAction.PUNCH_REMIND);
    }

    @OnClick(R.id.cpunch_priority)
    void showPriorityDialog() {
        showListPickerDialog(R.string.cpunch_priority,
                R.array.priority,
                1, BusAction.PUNCH_PRIORITY);
    }

    @Subscribe(tags = {@Tag(BusAction.NUMBER_PICKER_RESULT)})
    public void setPunchAim(Integer selected) {
        tvAim.setText(mAims[selected]);
        int number;
        if (selected == mAims.length - 1) {
            number = NumberPickerEvent.MAX_VALUE;
        } else {
            number = Integer.valueOf(mAims[selected]);
        }
        RxBus.get().post(BusAction.PUNCH_AIM, new NumberPickerEvent(number));
    }

    @Subscribe(tags = {@Tag(BusAction.DAY_TIME_PICKER_RESULT)})
    public void setPunchTime(DayTime event) {
        tvTime.setText(CommonUtils.getDayTimeString(getContext().getResources(), event));
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_DURATION)})
    public void setPunchKeepTime(Boolean b) {
        tvDuration.setText(CommonUtils.getBooleanString(getContext().getResources(), b));
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_REMIND)})
    public void setPunchRemind(Boolean remind) {
        tvRemind.setText(CommonUtils.getBooleanString(getContext().getResources(), remind));
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_PRIORITY)})
    public void setPunchPriority(Integer priority) {
        if (priority < mPriorities.length) {
            tvPriority.setText(mPriorities[priority]);
        }
    }

    private abstract class AfterTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    }
}
