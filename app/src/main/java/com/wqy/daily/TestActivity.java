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

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_fab)
    FloatingActionButton mFab;

    @BindView(R.id.test_tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mFab.setOnClickListener(v -> {
            DialogFragment fragment = new BooleanPickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(BooleanPickerFragment.ARG_EVENT_TAG, BusAction.PUNCH_KEEP_TIME);
            bundle.putString(BooleanPickerFragment.ARG_MESSAGE, getString(R.string.cpunch_duration));
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(), BooleanPickerFragment.TAG);
        });

        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.PUNCH_KEEP_TIME)})
    public void onTimePickerResult(Boolean event) {
        tv.setText(CommonUtils.getBooleanString(getResources(), event));
    }
}
