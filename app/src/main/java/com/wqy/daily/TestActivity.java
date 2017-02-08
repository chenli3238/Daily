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
import com.wqy.daily.event.DayTimePickerEvent;
import com.wqy.daily.event.TimePickerEvent;
import com.wqy.daily.widget.DayTimePickerFragment;
import com.wqy.daily.widget.TimePickerFragment;

import java.util.Locale;

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
            DialogFragment fragment = new DayTimePickerFragment();
            fragment.show(getSupportFragmentManager(), TimePickerFragment.TAG);
        });

        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.DAY_TIME_PICKER_RESULT)})
    public void onTimePickerResult(DayTimePickerEvent event) {
        tv.setText(CommonUtils.getDayTimeString(getResources(), event));
    }
}
