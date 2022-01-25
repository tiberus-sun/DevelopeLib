package com.szyl.szyllibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
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
public class TsCustomActionBar extends RelativeLayout implements View.OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;
    private ImageView iv_more;
    private TextView tv_right;

    public TsCustomActionBar(Context context) {
        super(context);
    }

    public TsCustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigationbar, this, true);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        tv_right = (TextView) findViewById(R.id.tv_right);

        tv_right.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_more.setOnClickListener(this);
        initView(context, attrs);
    }

    public TsCustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
     * 设置标题栏的右侧图标
     */
    public void setRightImage(int icon) {
        iv_more.setBackgroundResource(icon);
        iv_more.setVisibility(View.VISIBLE);
    }

    /*
     * 设置标题栏的右侧图标 显示隐藏
     */
    public void setRightImageVisible(int visible) {
        iv_more.setVisibility(visible);
    }

    /*
     * 设置标题栏的标题
     */
    public void setNavigationTitle(String navigationTitle) {
        tv_title.setText(Html.fromHtml(navigationTitle));
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            if (callBackListener != null) {
                callBackListener.OnClickButton(view);
            } else {
                ((Activity) getContext()).onBackPressed();
            }

        } else if (i == R.id.iv_more || i == R.id.tv_right) {
            if (mcallRitht != null)
                mcallRitht.oncallRighText();
        }
    }

    public void setRightText(boolean isshow, String content) {
        if (isshow) {
            tv_right.setVisibility(VISIBLE);
            tv_right.setText(content);
        } else {
            tv_right.setVisibility(INVISIBLE);
            tv_right.setText(content);
        }
    }


    /*
     *创建回调接口,点击了返回以后回调
     */
    public interface OnClickCallBackListener {
        public void OnClickButton(View v);
    }

    //  点击右侧按钮的回调
    public interface CallRight {
        public void oncallRighText();
    }


    private OnClickCallBackListener callBackListener;
    private CallRight mcallRitht;

    public void setCallBackListener(OnClickCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void setCallRight(CallRight call) {
        this.mcallRitht = call;
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
//			float scale = context.getResources().getDisplayMetrics().density;
            float scale = 2;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TsCustomActionBar);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.TsCustomActionBar_cus_left) {
                    iv_back.setImageDrawable(a.getDrawable(attr));
                } else if (attr == R.styleable.TsCustomActionBar_cus_right) {
                    iv_more.setBackground(a.getDrawable(attr));

                } else if (attr == R.styleable.TsCustomActionBar_cus_title) {
                    tv_title.setText(a.getText(attr));

                } else if (attr == R.styleable.TsCustomActionBar_cus_color) {
                    tv_title.setTextColor(a.getColor(attr, Color.WHITE));
                    tv_right.setTextColor(a.getColor(attr, Color.WHITE));

                } else if (attr == R.styleable.TsCustomActionBar_isnot_right_pic) {
                    if (a.getBoolean(attr, false))
                        iv_more.setVisibility(View.GONE);

                } else if (attr == R.styleable.TsCustomActionBar_isnot_left_pic) {
                    if (a.getBoolean(attr, false))
                        iv_back.setVisibility(View.GONE);
                }
            }
            a.recycle();

        }
    }


}
