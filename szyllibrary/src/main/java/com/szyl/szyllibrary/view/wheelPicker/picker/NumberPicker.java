package com.szyl.szyllibrary.view.wheelPicker.picker;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 数字选择器
 */
public class NumberPicker extends OptionPicker {

    public NumberPicker(Activity activity) {
        super(activity, new String[]{});
    }

    /**
     * 设置数字范围，递增量为1
     */
    public void setRange(int startNumber, int endNumber) {
        setRange(startNumber, endNumber, 1);
    }

    /**
     * 设置数字范围及递增量
     */
    public void setRange(int startNumber, int endNumber, int step) {
        for (int i = startNumber; i <= endNumber; i = i + step) {
            options.add(String.valueOf(i));
        }
    }

    /**
     * 设置数字范围
     */
    public void setRange(ArrayList<String> options) {
        this.options.addAll(options);
    }

    /**
     * 设置默认选中的数字
     */
    public void setSelectedItem(int number) {
        setSelectedItem(String.valueOf(number));
    }

}

