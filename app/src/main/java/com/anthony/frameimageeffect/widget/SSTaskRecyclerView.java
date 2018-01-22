package com.anthony.frameimageeffect.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Harry on 6/1/16.
 */
public class SSTaskRecyclerView extends RecyclerView implements View.OnTouchListener {
    private static final int MIN_DISTANCE = 300;
    private float x1, x2;
    private boolean isTap;

    public SSTaskRecyclerView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public SSTaskRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public SSTaskRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    isTap = true;
                } else {
                    isTap = false;
                }
                break;

        }
        return isTap;
    }
}
