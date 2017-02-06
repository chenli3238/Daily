package com.wqy.daily.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wqy.daily.R;
import com.wqy.daily.model.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-6.
 */

public class PunchUnderwayVH extends ViewHolder<Event> {

    @BindView(R.id.punch_underway_title)
    TextView tvTitle;

    @BindView(R.id.punch_underway_score)
    TextView tvScore;

    @BindView(R.id.punch_underway_btn)
    Button btn;

    public PunchUnderwayVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(Event data) {
        tvTitle.setText(data.getTitle());
        tvScore.setText(String.valueOf(data.getScore()));
    }
}
