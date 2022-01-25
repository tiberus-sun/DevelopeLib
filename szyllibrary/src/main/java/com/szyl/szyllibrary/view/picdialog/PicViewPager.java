package com.szyl.szyllibrary.view.picdialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * picshowdialog，重写viewpager，避免pointerIndex out of range异常
 * Created by xiaoke on 2016/5/20.
 */
public class PicViewPager extends ViewPager {
    public PicViewPager(Context context) {
        super(context);
    }

    public PicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
