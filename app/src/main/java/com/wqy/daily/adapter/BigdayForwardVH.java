package com.wqy.daily.adapter;

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

public class BigdayForwardVH extends ViewHolder<Bigday> {

    @BindView(R.id.bigday_forward_title)
    TextView tvTitle;

    @BindView(R.id.bigday_forward_day)
    TextView tvDay;

    public BigdayForwardVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(Bigday data) {
        String format = tvTitle.getContext().getString(R.string.bigday_forward_title);
        tvTitle.setText(String.format(format, data.getTitle()));
        tvDay.setText(String.valueOf(CommonUtils.forwardDays(data.getDate())));
    }
}
