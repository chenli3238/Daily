package com.wqy.daily.view;

import android.app.Activity;
import android.content.res.Resources;
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
import com.wqy.daily.event.ShowDialogEvent;
import com.wqy.daily.mvp.ViewImpl;
import com.wqy.daily.widget.DateTimePickerFragment;
import com.wqy.daily.widget.TagPickerFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wqy on 17-2-8.
 */

public class CreateBigdayView extends ViewImpl {
    public static final String TAG = CreatePunchView.class.getSimpleName();

    @BindView(R.id.cbigday_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cbigday_title_layout)
    TextInputLayout tilTitle;

    @BindView(R.id.cbigday_title)
    TextInputEditText etTitle;

    @BindView(R.id.cbigday_desc_layout)
    TextInputLayout tilDesc;

    @BindView(R.id.cbigday_desc)
    TextInputEditText etDesc;

    @BindView(R.id.cbigday_time_value)
    TextView tvTime;

    @BindView(R.id.cbigday_tags_value)
    TextView tvTags;

    private Resources mResources;


    @Override
    public int getResId() {
        return R.layout.activity_create_bigday;
    }

    @Override
    public int getMenuId() {
        return R.menu.activity_cbigday;
    }

    @Override
    public void created() {
        ButterKnife.bind(this, mRootView);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mResources = getContext().getResources();
        RxBus.get().register(this);
    }

    @Override
    public void destroy() {
        RxBus.get().unregister(this);
    }

    @Override
    public void bindEvent() {
        etTitle.addTextChangedListener(new AfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                verifyTitle();
            }
        });

        etDesc.addTextChangedListener(new AfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                verifyDesc();
            }
        });
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask((Activity) getContext());
                return true;
            case R.id.cbigday_confirm:
                // create a new bigday
                confirm();
                return true;
            default:
                return false;
        }
    }

    private void confirm() {
        Log.d(TAG, "confirm: ");
        if (verifyTitle() && verifyDesc()) {
            RxBus.get().post(BusAction.BIGDAY_TITLE_DESC, new String[] {
                    etTitle.getText().toString(),
                    etDesc.getText().toString()
            });
            RxBus.get().post(BusAction.CREATE_BIGDAY, "");
        }
    }

    @Produce(tags = {@Tag(BusAction.SET_CBIGDAY_TITLE)})
    public String setActivityTitle() {
        return getContext().getString(R.string.title_cbigday);
    }

    private boolean verifyTitle() {
        String title = etTitle.getText().toString();
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
        boolean ok = etDesc.getText().toString().length() < tilDesc.getCounterMaxLength();
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

    @OnClick(R.id.cbigday_time)
    public void showDateTimeDialog() {
        Log.d(TAG, "showDateTimeDialog: ");
        DialogFragment fragment = new DateTimePickerFragment();
        showDialog(DateTimePickerFragment.TAG, fragment);

    }

    @OnClick(R.id.cbigday_tags)
    public void showTagDialog() {
        Log.d(TAG, "showTagDialog: ");
        DialogFragment fragment = new TagPickerFragment();
        showDialog(TagPickerFragment.TAG, fragment);
    }

    @Subscribe(tags = {@Tag(BusAction.DATE_TIME_PICKER_RESULLT)})
    public void setTime(Calendar event) {
        tvTime.setText(CommonUtils.getDateTimeString(mResources, event));
    }

    @Subscribe(tags = {@Tag(BusAction.TAG_PICKER_RESULT)})
    public void setTags(String[] tags) {
        tvTags.setText(CommonUtils.getTagString(mResources, tags));
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
