package com.baiiu.filter.typeview.MultiGridView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiiu.filter.R;
import com.baiiu.filter.interfaces.OnFilterDoneListener;

import java.util.List;

/**
 * auther: baiiu
 * time: 16/6/5 05 23:03
 * description:
 */
public class MultiGridView extends LinearLayout implements View.OnClickListener {

    private RecyclerView recyclerView;

    private List<String> mTopGridData;
    private List<String> mBottomGridList;
    private OnFilterDoneListener mOnFilterDoneListener;

    private TextView tv_reset;
    private TextView tv_confirm;

    public MultiGridView(Context context) {
        this(context, null);
    }

    public MultiGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultiGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        View view= inflate(context, R.layout.merge_filter_multi_grid, this);
        recyclerView = (RecyclerView)findViewById(R.id.filetr_recyclerView);
        tv_reset= (TextView)findViewById(R.id.tv_reset);
        tv_confirm= (TextView)findViewById(R.id.tv_confirm);

        //重置
        tv_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSelectedTextView != null) {
                    mBottomSelectedTextView.setSelected(false);
                    mBottomSelectedTextView=null;
                }
                if (mTopSelectedTextView != null) {
                    mTopSelectedTextView.setSelected(false);
                    mTopSelectedTextView=null;
                }
            }
        });

        tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnFilterDoneListener != null) {
                    mOnFilterDoneListener.onFilterDone(4, "", "");
                }
            }
        });

    }


    public MultiGridView setmTopGridData(List<String> mTopGridData) {
        this.mTopGridData = mTopGridData;
        return this;
    }

    public MultiGridView setmBottomGridList(List<String> mBottomGridList) {
        this.mBottomGridList = mBottomGridList;
        return this;
    }

    public MultiGridView build() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (position == 0 || position == mTopGridData.size() + 1) {
                    return 3;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new MultiGridAdapter(getContext(), mTopGridData, mBottomGridList, this));

        return this;
    }

    private TextView mTopSelectedTextView;
    private TextView mBottomSelectedTextView;

    @Override
    public void onClick(View v) {

        TextView textView = (TextView) v;
        String text = (String) textView.getTag();

        if (textView == mTopSelectedTextView) {
            mTopSelectedTextView = null;
            textView.setSelected(false);
        } else if (textView == mBottomSelectedTextView) {
            mBottomSelectedTextView = null;
            textView.setSelected(false);
        } else if (mTopGridData.contains(text)) {
            if (mTopSelectedTextView != null) {
                mTopSelectedTextView.setSelected(false);
            }
            mTopSelectedTextView = textView;
            textView.setSelected(true);
        } else {
            if (mBottomSelectedTextView != null) {
                mBottomSelectedTextView.setSelected(false);
            }
            mBottomSelectedTextView = textView;
            textView.setSelected(true);
        }

    }


    public MultiGridView setOnFilterDoneListener(OnFilterDoneListener listener) {
        mOnFilterDoneListener = listener;
        return this;
    }


    public String getMoreData(){

        String top= mTopSelectedTextView == null ? "" : (String) mTopSelectedTextView.getTag();
        String bottom=mBottomSelectedTextView == null ? "" : (String) mBottomSelectedTextView.getTag();

        return top+bottom;
    }


}
