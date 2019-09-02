package com.szyl.szyllibrary.view.wheelPicker.picker;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.View;

import com.szyl.szyllibrary.view.wheelPicker.popup.ConfirmPopup;
import com.szyl.szyllibrary.view.wheelPicker.widget.WheelView;


/**
 * 滑轮选择器
 */
public abstract class WheelPicker extends ConfirmPopup<View> {
    protected int textSize = WheelView.TEXT_SIZE;
    protected int textOptionSize = WheelView.TEXTOPTION_SIZE;
    protected int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
    protected int textColorFocus = WheelView.TEXT_COLOR_FOCUS;
    protected int lineColor = WheelView.LINE_COLOR;
    protected boolean lineVisible = true;
    protected int offset = WheelView.OFF_SET;

    protected int paddingLeft = WheelView.PADDING_LEFT;
    protected int paddingTop = WheelView.PADDING_TOP;
    protected int paddingRight = WheelView.PADDING_RIGHT;
    protected int paddingBottom = WheelView.PADDING_BOTTOM;

    public WheelPicker(Activity activity) {
        super(activity);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * 设置选中文字大小
     */
    public void setTextOptionSize(int textSize) {
        this.textOptionSize = textSize;
    }

    /**
     * 设置文字颜色
     */
    public void setTextColor(@ColorInt int textColorFocus, @ColorInt int textColorNormal) {
        this.textColorFocus = textColorFocus;
        this.textColorNormal = textColorNormal;
    }

    /**
     * 设置文字颜色
     */
    public void setTextColor(@ColorInt int textColor) {
        this.textColorFocus = textColor;
    }

    /**
     * 设置分隔线是否可见
     */
    public void setLineVisible(boolean lineVisible) {
        this.lineVisible = lineVisible;
    }

    /**
     * 设置分隔线颜色
     */
    public void setLineColor(@ColorInt int lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * 设置选项偏移量，默认为1，范围为1-4。
     * 1显示三条、2显示5条、3显示7条、4显示9条
     */
    public void setOffset(@IntRange(from = 1, to = 4) int offset) {
        this.offset = offset;
    }


    /**
     * 设置中间选中内容的 padding
     */
    public void setMyPadding(int paddingLeft,int paddingTop,int paddingRight,int paddingBottom ) {
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
    }

}
