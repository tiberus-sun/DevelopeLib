package com.szyl.szyllibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.szyl.szyllibrary.R;

/**
 * Created by tiberus on 2018/12/11.
 * 加载loading
 */

public class TsLoadView extends Dialog {
    private TextView textView;
    private String content;
    public TsLoadView(Context context) {
        super(context);
    }
    public TsLoadView(Context context, int theme) {
        super(context, theme);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }
    private void init(Context context) {

        setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉白色边角 我的小米手机在xml里设置 android:background="@android:color/transparent"居然不生效
        //所以在代码里设置，不知道是不是小米手机的原因
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.view_loading);

        textView = (TextView) this.findViewById(R.id.tv_load_dialog);
        if(null!=content){
            textView.setText(content);
        }
    }
    @Override
    public void show() {//开启
        super.show();
    }
    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }
    //设置展示内容
    public void setContent(String content) {
        this.content = content;
    }
}