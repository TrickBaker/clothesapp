package com.anthony.frameimageeffect.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Harry on 5/31/16.
 */
public class SSViewPager extends ViewPager {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;


    public enum SwipeDirection {
        all, left, right, none;
    }

    public interface OnSwipeListener {
        void onSwipe(int pos);
    }

    private OnSwipeListener onSwipeListener;
    private float initialXValue;
    private SwipeDirection direction;

    public SSViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection.all;
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (this.IsSwipeAllowed(e)) {
            return super.onTouchEvent(e);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (onSwipeListener != null) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    float deltaX = x2 - x1;
                    if (Math.abs(deltaX) > MIN_DISTANCE)
                    {
                        onSwipeListener.onSwipe(getCurrentItem());
                    }
                    break;
            }

        }

        if (this.IsSwipeAllowed(event)) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    private boolean IsSwipeAllowed(MotionEvent event) {
        if (this.direction == SwipeDirection.all) return true;

        if (direction == SwipeDirection.none)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection.right) {
                    // swipe from left to right detected
                    return false;
                } else if (diffX < 0 && direction == SwipeDirection.left) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }


    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }
}
