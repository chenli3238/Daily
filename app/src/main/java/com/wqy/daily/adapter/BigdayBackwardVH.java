package com.wqy.daily.adapter;

import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import com.wqy.daily.CommonUtils;
import com.wqy.daily.R;
import com.wqy.daily.model.Bigday;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-7.
 */

public class BigdayBackwardVH extends ViewHolder<Bigday> {

    @BindView(R.id.bigday_backward_title)
    TextView tvTitle;

    @BindView(R.id.bigday_backward_day)
    TextView tvDay;


    public BigdayBackwardVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(Bigday data) {
        String format = tvDay.getContext().getString(R.string.bigday_backward_title);
        tvTitle.setText(String.format(format, data.getTitle()));
        tvDay.setText(String.valueOf(CommonUtils.backwardDays(data.getDate())));
    }
}
