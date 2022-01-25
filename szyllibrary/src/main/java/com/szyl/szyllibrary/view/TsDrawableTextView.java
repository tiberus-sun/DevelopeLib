package com.szyl.szyllibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by tiberus on 2019/3/3.
 * 作用：图片和文字 集体居中
 */

public class TsDrawableTextView extends AppCompatTextView {

    public TsDrawableTextView(Context context) {

        this(context, null);

    }

    public TsDrawableTextView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public TsDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }

    @Override

    protected void onDraw(Canvas canvas) {

// getCompoundDrawables() : Returns drawables for the left, top, right, and bottom borders.

        Drawable[] drawables = getCompoundDrawables();

// 得到drawableLeft设置的drawable对象

        Drawable leftDrawable = drawables[0];

        if (leftDrawable != null) {

// 得到leftDrawable的宽度

            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();

// 得到drawable与text之间的间距

            int drawablePadding = getCompoundDrawablePadding();

// 得到文本的宽度

            int textWidth = (int) getPaint().measureText(getText().toString().trim());

            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;

            canvas.save();

            canvas.translate((getWidth() - bodyWidth) / 2, 0);

        }

        super.onDraw(canvas);

    }

}

