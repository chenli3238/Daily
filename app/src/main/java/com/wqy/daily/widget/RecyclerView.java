package com.wqy.daily.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.cjj.loadmore.RecyclerViewWithFooter;

/**
 * Created by wqy on 17-2-6.
 */

public class RecyclerView extends RecyclerViewWithFooter {
    public static final String TAG = RecyclerView.class.getSimpleName();

    private GestureDetectorCompat mGestureDetector;
    private OnItemClickListener mOnItemClickListener = null;

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: " + mOnItemClickListener);
                if (mOnItemClickListener != null) {
                    View view = RecyclerView.this.findChildViewUnder(e.getX(), e.getY());
                    mOnItemClickListener.onItemClick(view);
                    return true;
                }
                return false;
            }
        });
        addOnItemTouchListener(new SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(android.support.v7.widget.RecyclerView rv, MotionEvent e) {
                Log.d(TAG, "onInterceptTouchEvent: ");
                return mGestureDetector.onTouchEvent(e);
            }
        });
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView);
    }
}
