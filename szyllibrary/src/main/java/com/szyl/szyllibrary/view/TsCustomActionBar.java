package com.szyl.szyllibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szyl.szyllibrary.R;


/**
 * Created by JCY on 2017/6/16.
 * 自定义title
 */
public class TsCustomActionBar extends RelativeLayout implements View.OnClickListener{

    private TextView tv_title_ty;
    private ImageView ll_back;
    private ImageView ib_more;
    private TextView tv_bar_right;
    public TsCustomActionBar(Context context) {
        super(context);
    }

    public TsCustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigationbar, this, true);
        tv_title_ty = (TextView) findViewById(R.id.tv_title_ty);
        ll_back = (ImageView) findViewById(R.id.ib_back_qd);
        ib_more = (ImageView) findViewById(R.id.ib_more);
        tv_bar_right = (TextView) findViewById(R.id.tv_bar_right);

        tv_bar_right.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        ib_more.setOnClickListener(this);
        initView(context,attrs);
    }

    public TsCustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
     * 设置标题栏的右侧图标
     */
    public void setRightImage(int icon){
        ib_more.setBackgroundResource(icon);
    }

    /*
     * 设置标题栏的标题
     */
    public void setNavigationTitle(String navigationTitle) {
        tv_title_ty.setText(navigationTitle);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ib_back_qd) {
            if (callBackListener != null)
                callBackListener.OnClickButton(view);
            //                返回按钮
            ((Activity) getContext()).finish();

        } else if (i == R.id.ib_more || i == R.id.tv_bar_right ) {
            if (mcallRitht != null)
                mcallRitht.oncallRighText();

        }
    }

    public void setRightText(boolean isshow,String content){
        if(isshow){
            tv_bar_right.setVisibility(VISIBLE);
            tv_bar_right.setText(content);
        }else{
            tv_bar_right.setVisibility(INVISIBLE);
            tv_bar_right.setText(content);
        }
    }


    /*
    *创建回调接口,点击了返回以后回调
    */
    public interface OnClickCallBackListener {
        public void OnClickButton(View v);
    }

//  点击右侧按钮的回到
    public interface CallRight {
        public void oncallRighText();

    }


    private OnClickCallBackListener callBackListener;
    private CallRight mcallRitht;
    public void setCallBackListener(OnClickCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }
    public void setCallRight(CallRight call){
        this.mcallRitht=call;
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
//			float scale = context.getResources().getDisplayMetrics().density;
            float scale=2;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TsCustomActionBar);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.TsCustomActionBar_cus_right) {
                    //drawableBottom = a.getDrawable(attr);
                    ib_more.setBackground(a.getDrawable(attr));

                } else if (attr == R.styleable.TsCustomActionBar_cus_title) {
                    tv_title_ty.setText(a.getText(attr));

                } else if (attr == R.styleable.TsCustomActionBar_isnot_right_pic) {
                    if (a.getBoolean(attr, false))
                        ib_more.setVisibility(View.GONE);

                } else if (attr == R.styleable.TsCustomActionBar_isnot_left_pic) {
                    if (a.getBoolean(attr, false))
                        ll_back.setVisibility(View.GONE);

                }
            }
            a.recycle();

        }
    }



}
