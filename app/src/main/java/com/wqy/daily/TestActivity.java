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
import com.wqy.daily.event.NumberPickerEvent;
import com.wqy.daily.event.TimePickerEvent;
import com.wqy.daily.widget.DayTimePickerFragment;
import com.wqy.daily.widget.NoticeDialogFragment;
import com.wqy.daily.widget.NumberPickerFragment;

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
            DialogFragment fragment = new NumberPickerFragment();
            fragment.show(getSupportFragmentManager(), DayTimePickerFragment.TAG);
        });

        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(BusAction.NUMBER_PICKER_RESULT)})
    public void onTimePickerResult(NumberPickerEvent event) {
        tv.setText(CommonUtils.getNumberString(getResources(), event));
    }
}
