package com.wqy.daily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;
import com.wqy.daily.R;

import org.w3c.dom.Text;

/**
 * Created by wqy on 17-2-9.
 */

public class TagView extends TokenCompleteTextView<String> {

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(String object) {
        TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tag_view, null);
        view.setText(object);
        return view;
    }

    @Override
    protected String defaultObject(String completionText) {
        return completionText;
    }
}
