package com.baiiu.filter.typeview.DoubleGridView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiiu.filter.R;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;

import java.util.List;

/**
 * Created by baiiu on 15/12/22.
 * 筛选器GridView
 *
 * extends ScrollView
 */
public class DoubleGridView extends LinearLayout implements View.OnClickListener {
    private SimpleTextAdapter<String> mTopAdapter;
    private SimpleTextAdapter<String> mBottomAdapter;

    private OnFilterDoneListener onFilterDoneListener;

    private GridViewInScrollView mTopGrid;
    private GridViewInScrollView mBottomGrid;

    private FilterCheckedTextView mTopSelectedTextView;
    private FilterCheckedTextView mBottomSelectedTextView;

    public DoubleGridView(Context context) {
        this(context, null);
    }

    public DoubleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view= inflate(context, R.layout.merge_filter_double_grid, this);
        setBackgroundResource(android.R.color.white);

        mTopGrid=(GridViewInScrollView)view.findViewById(R.id.grid_top);
        mBottomGrid=(GridViewInScrollView)view.findViewById(R.id.grid_bottom);
        TextView tvrest=(TextView)view.findViewById(R.id.double_tv_reset);
        TextView tvconfirm=(TextView)view.findViewById(R.id.double_tv_confirm);

        tvrest.setOnClickListener(this);
        tvconfirm.setOnClickListener(this);

        initAdapter(context);

        mTopGrid.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mBottomGrid.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        mTopGrid.setAdapter(mTopAdapter);
        mBottomGrid.setAdapter(mBottomAdapter);
    }

    private void initAdapter(Context context) {

        mTopAdapter = new SimpleTextAdapter<String>(null, context) {
            @Override
            public String provideText(String phase) {
                return phase;
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                checkedTextView.setGravity(Gravity.CENTER);
                checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
            }
        };

        mBottomAdapter = new SimpleTextAdapter<String>(null, context) {
            @Override
            public String provideText(String area) {
                return area;
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {

                checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                checkedTextView.setGravity(Gravity.CENTER);

            }
        };


        mTopGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FilterCheckedTextView textView = (FilterCheckedTextView) view;

                if (textView == mTopSelectedTextView) {
                    mTopSelectedTextView = null;

                    //由于设置的背景颜色 checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid) xml中
                    //有check 和 select 两种，所以两者都设置false 否则 选中之后再点击变不了非选中;
                    textView.setSelected(false);
                    textView.setChecked(false);

                } else  {
                    if (mTopSelectedTextView != null) {
                        mTopSelectedTextView.setSelected(false);
                        mTopSelectedTextView.setChecked(false);
                    }
                    mTopSelectedTextView = textView;
                    mTopSelectedTextView.setSelected(true);
                    mTopSelectedTextView.setChecked(true);

                }
            }
        });

        mBottomGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FilterCheckedTextView textView = (FilterCheckedTextView) view;

                if (textView == mBottomSelectedTextView) {
                    mBottomSelectedTextView = null;
                    textView.setSelected(false);
                    textView.setChecked(false);
                } else {
                    if (mBottomSelectedTextView != null) {
                        mBottomSelectedTextView.setSelected(false);
                        mBottomSelectedTextView.setChecked(false);
                    }
                    mBottomSelectedTextView = textView;
                    mBottomSelectedTextView.setSelected(true);
                    mBottomSelectedTextView.setChecked(true);
                }
            }
        });

    }

    public void setTopGridData(List<String> list) {

        if (CommonUtil.isEmpty(list)) {
            return;
        }

        mTopAdapter.setList(list);
    }

    public void setBottomGridList(List<String> list) {
        if (CommonUtil.isEmpty(list)) {
            return;
        }

        mBottomAdapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.double_tv_reset) {

            if (mBottomSelectedTextView != null) {
                mBottomSelectedTextView.setSelected(false);
                mBottomSelectedTextView.setChecked(false);
                mBottomSelectedTextView=null;
            }
            if (mTopSelectedTextView != null) {
                mTopSelectedTextView.setSelected(false);
                mTopSelectedTextView.setChecked(false);
                mTopSelectedTextView=null;
            }

        } else if (i == R.id.double_tv_confirm) {
            /* //原取值方式
            int topPosition = mTopGrid.getCheckedItemPosition();
            topPosition = topPosition == -1 ? 0 : topPosition;

            int bottomPosition = mBottomGrid.getCheckedItemPosition();
            bottomPosition = bottomPosition == -1 ? 0 : bottomPosition;

            String topValue = mTopAdapter.getItem(topPosition);
            String bottomValue = mBottomAdapter.getItem(bottomPosition);*/

            if (onFilterDoneListener != null) {
                onFilterDoneListener.onFilterDone(5, "", "");
            }

        }

    }

    public void setOnFilterDoneListener(OnFilterDoneListener listener) {
        onFilterDoneListener = listener;
    }

    public String getDoubleData(){

        String top= mTopSelectedTextView == null ? "" : (String) mTopSelectedTextView.getText();
        String bottom=mBottomSelectedTextView == null ? "" : (String) mBottomSelectedTextView.getText();

        return top+bottom;
    }
}
