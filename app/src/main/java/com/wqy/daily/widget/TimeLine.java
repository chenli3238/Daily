package com.wqy.daily.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wqy.daily.R;

/**
 * Created by wqy on 17-2-7.
 */

public class TimeLine extends View {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int orientation;
    private float dotPosition;
    private float dotSize;
    private float lineWidth;
    private int color;

    private float linePadding;
    private float radius;

    private Paint linePaint;
    private Paint circlePaint;

    public TimeLine(Context context) {
        this(context, null);
    }

    public TimeLine(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimeLine, 0, R.style.TimeLine);
        dotPosition = a.getDimension(R.styleable.TimeLine_dotPosition, 0f);
        dotSize = a.getDimension(R.styleable.TimeLine_dotSize, 0f);
        orientation = a.getInt(R.styleable.TimeLine_orientation, VERTICAL);
        lineWidth = a.getDimension(R.styleable.TimeLine_lineWidth, 0);
        color = a.getColor(R.styleable.TimeLine_color, Color.TRANSPARENT);
        a.recycle();
        linePaint = new Paint();
        linePaint.setColor(color);
        linePaint.setStrokeWidth(lineWidth);
        circlePaint = new Paint();
        circlePaint.setColor(color);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (orientation == HORIZONTAL) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);
            if (hMode == MeasureSpec.EXACTLY) {
                dotSize = MeasureSpec.getSize(heightMeasureSpec);
            }
            setMeasuredDimension(width, (int) dotSize);

            linePadding = (dotSize - lineWidth) / 2;
            radius = dotSize / 2;
        } else {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            if (wMode == MeasureSpec.EXACTLY) {
                dotSize = MeasureSpec.getSize(widthMeasureSpec);
            }
            setMeasuredDimension((int)dotSize, height);

            linePadding = (dotSize - lineWidth) / 2;
            radius = dotSize / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (orientation == HORIZONTAL) {
            canvas.drawLine(0, radius, getWidth(), radius, linePaint);
            canvas.drawCircle(dotPosition, radius, radius, circlePaint);
        } else {
            canvas.drawLine(radius, 0, radius, getHeight(), linePaint);
            canvas.drawCircle(radius, dotPosition, radius, circlePaint);
        }
    }
}
