package com.example.along.scmusic.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by long on 02/05/2018
 */

public class UnSwipeViewPager extends ViewPager {
    private boolean mIsEnabled;

    public UnSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIsEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mIsEnabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }
}
