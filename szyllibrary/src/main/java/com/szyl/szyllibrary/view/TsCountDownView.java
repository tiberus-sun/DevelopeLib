package com.szyl.szyllibrary.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.szyl.szyllibrary.R;

/**
 * 倒计时TextView
 */

public class TsCountDownView extends AppCompatTextView {

    private boolean isclick=false;

    private int smsCode=0;

    /**
     * 提示文字
     */
    private String mHintText = " 重新获取 ";

    /**
     * 倒计时时间
     */
    private long mCountDownMillis = 120_000;

    /**
     * 剩余倒计时时间
     */
    private long mLastMillis;
    /**
     * 间隔时间差(两次发送handler)
     */
    private long mIntervalMillis = 1_000;
    /**
     * 开始倒计时code
     */
    private final int MSG_WHAT_START = 10_010;
    /**
     * 可用状态下字体颜色Id
     */
    private int usableColorId = R.color.colorPrimary;
    /**
     * 不可用状态下字体颜色Id
     */
    private int unusableColorId = R.color.countdown_grey;

    public TsCountDownView(Context context) {
        super(context);
    }

    public TsCountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TsCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isclick() {
        return isclick;
    }

    public void setIsclick(boolean isclick) {
        this.isclick = isclick;
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_WHAT_START:
//                    Log.e("l", mLastMillis + "");
                    if (mLastMillis > 0) {
                        setUsable(false);
                        mLastMillis -= mIntervalMillis;
                        mHandler.sendEmptyMessageDelayed(MSG_WHAT_START, mIntervalMillis);
                    } else {
                        setUsable(true);
                    }
                    break;
            }
        }
    };


    /**
     * 设置是否可用
     *
     * @param usable
     */
    public void setUsable(boolean usable) {


        if (usable) {
            //可用
            if (!isClickable()) {
                setClickable(usable);
                setTextColor(getResources().getColor(usableColorId));
                setBackgroundResource(R.drawable.countdown_normal);
                setText(mHintText);

                smsCode=0;
            }
        } else {
            //不可用
            if (isClickable()) {
                setClickable(usable);
                setTextColor(getResources().getColor(unusableColorId));
                setBackgroundResource(R.drawable.countdown_press);
            }
            setText(mLastMillis / 1000 + "s"+"后重新获取");
        }

    }


    /**
     * 设置倒计时颜色
     *
     * @param usableColorId   可用状态下的颜色
     * @param unusableColorId 不可用状态下的颜色
     */
    public void setCountDownColor(@ColorRes int usableColorId, @ColorRes int unusableColorId) {
        this.usableColorId = usableColorId;
        this.unusableColorId = unusableColorId;
    }

    /**
     * 设置倒计时时间
     *
     * @param millis 毫秒值
     */
    public void setCountDownMillis(long millis) {
        mCountDownMillis = millis;
    }

    /**
     * 开始倒计时
     */
    public void start() {
        mLastMillis = mCountDownMillis;
        mHandler.sendEmptyMessage(MSG_WHAT_START);
    }

    /**
     * 重置倒计时
     */
    public void reset() {
        mLastMillis = 0;
        mHandler.sendEmptyMessage(MSG_WHAT_START);
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener onClickListener) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                if(isclick) {
                    mHandler.removeMessages(MSG_WHAT_START);
                    start();
                }
            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(MSG_WHAT_START);
    }

    public int getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(int smsCode) {
        this.smsCode = smsCode;
    }
}