package com.wqy.daily.adapter;

import android.view.View;
import android.widget.TextView;

import com.wqy.daily.CommonUtils;
import com.wqy.daily.R;
import com.wqy.daily.model.Diary;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-7.
 */

public class DiaryVH extends ViewHolder<Diary> {

    public static SimpleDateFormat format;


    static {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @BindView(R.id.diary_item_date)
    TextView tvDate;

    @BindView(R.id.diary_item_day_of_week)
    TextView tvWeek;

    @BindView(R.id.diary_item_text)
    TextView tvText;

    @BindView(R.id.diary_item_timestamp)
    TextView tvTimestamp;

    public DiaryVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(Diary data) {
        tvText.setText(data.getText());
        Calendar c = Calendar.getInstance();
        c.setTime(data.getDate());
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int w = c.get(Calendar.DAY_OF_WEEK);
        tvDate.setText(String.format("%d月%d日", m, d));
        tvTimestamp.setText(format.format(data.getDate()));
        tvWeek.setText(CommonUtils.getDayOfWeekString(tvWeek.getContext().getResources(), w));
    }
}
