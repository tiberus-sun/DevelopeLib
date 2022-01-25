package com.szyl.szyllibrary.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szyl.szyllibrary.R;

/**
 * @author vondear
 * Mainly used for confirmation and cancel.
 */
public class TsDialogEdit extends TsDialog {

    private ImageView mIvLogo;
    private TextView mTvSure;
    private TextView mTvCancel;
    private EditText editText;
    private TextView mTvTitle;

    public TsDialogEdit(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public TsDialogEdit(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public TsDialogEdit(Context context) {
        super(context);
        initView();
    }

    public TsDialogEdit(Activity context) {
        super(context);
        initView();
    }

    public TsDialogEdit(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public ImageView getLogoView() {
        return mIvLogo;
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public TextView getTitleView() {
        return mTvTitle;
    }

    public EditText getEditText() {
        return editText;
    }

    public TextView getSureView() {
        return mTvSure;
    }

    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }

    public TextView getCancelView() {
        return mTvCancel;
    }

    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edittext, null);
        mIvLogo = (ImageView) dialogView.findViewById(R.id.iv_logo);
        mTvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        mTvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);
        editText = (EditText) dialogView.findViewById(R.id.editText);
        setContentView(dialogView);
    }

    //取消弹框
    public void cancel() {

        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0); // 强制隐藏键盘
        super.cancel();
    }

    //展示弹框
    /*public void show() {

        super.show();
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(editText, 0);
    }*/
}
