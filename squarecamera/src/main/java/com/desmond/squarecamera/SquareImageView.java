package com.desmond.squarecamera;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by desmond on 9/8/15.
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();

        //设置高度为屏幕的3/4
        setMeasuredDimension(width, width*3/4);
    }
}
