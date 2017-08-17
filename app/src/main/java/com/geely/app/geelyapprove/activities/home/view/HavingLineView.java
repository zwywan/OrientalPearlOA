package com.geely.app.geelyapprove.activities.home.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Oliver on 2016/10/28.
 */

public class HavingLineView extends View {

    private int color;
    private Paint paint;
    private int gapLength;
    private int lineLength;

    public HavingLineView(Context context) {
        super(context);
        init();
    }

    public HavingLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HavingLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        color = Color.GRAY;
        paint = new Paint();
        paint.setColor(color);
        gapLength = 10;
        lineLength = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int temp = 0;
        do {
            canvas.drawRect(0, 0, lineLength, getHeight(), paint);
            canvas.translate(gapLength, 0);
            temp = temp + lineLength + gapLength;
        } while (temp <= getWidth() * 2);
        super.onDraw(canvas);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        invalidate();
    }

    public float getGapLength() {
        return gapLength;
    }

    public void setGapLength(int gapLength) {
        this.gapLength = gapLength;
        invalidate();
    }

    public float getLineLength() {
        return lineLength;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
        invalidate();
    }
}
