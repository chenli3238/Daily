package com.wqy.daily;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wqy.daily.event.BusAction;
import com.wqy.daily.widget.BooleanPickerFragment;
import com.wqy.daily.widget.ListPickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_fab)
    FloatingActionButton mFab;

    @BindView(R.id.test_tv)
    TextView tv;

    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        items = getResources().getStringArray(R.array.priority);
        mFab.setOnClickListener(v -> {
            DialogFragment fragment = new ListPickerFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray(ListPickerFragment.ARG_ITEM_TITLES, items);
            bundle.putString(ListPickerFragment.ARG_EVENT_TAG, BusAction.PUNCH_PRIORITY);
            bundle.putInt(ListPickerFragment.ARG_DEFAULT_VALUE, 1);
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(), ListPickerFragment.TAG);
        });

        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_PRIORITY)})
    public void onTimePickerResult(Integer event) {
        tv.setText(items[event]);
    }
}
