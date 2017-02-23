package com.wqy.daily.adapter;

import android.view.View;
import android.widget.TextView;

import com.wqy.daily.R;
import com.wqy.daily.StringUtils;
import com.wqy.daily.model.Memo;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wqy on 17-2-6.
 */

public class MemoVH extends ViewHolder<Memo> {

    public static SimpleDateFormat format;

    static {
        format = new SimpleDateFormat("MM月dd日");
    }

    @BindView(R.id.memo_title)
    TextView tvTitle;

    @BindView(R.id.memo_content)
    TextView tvContent;

    @BindView(R.id.memo_date)
    TextView tvDate;

    public MemoVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(Memo data) {
        String text = StringUtils.hideImages(data.getContent());
        String titleOfText = StringUtils.parseTitle(text);
        tvTitle.setText(titleOfText);
        tvContent.setText(StringUtils.parseBody(titleOfText, text));
        if (data.getCreatedAt() != null) {
            tvDate.setText(format.format(data.getCreatedAt()));
        }
    }
}
