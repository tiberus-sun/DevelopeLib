package com.baiiu.filter.typeview.MultiGridView.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.baiiu.filter.R;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;



/**
 * auther: baiiu
 * time: 16/6/5 05 23:35
 * description:
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final FilterCheckedTextView textView;
    private View.OnClickListener mListener;

    public ItemViewHolder(Context mContext, ViewGroup parent, View.OnClickListener mListener) {
        super(UIUtil.infalte(mContext, R.layout.holder_item, parent));
        textView = itemView.findViewById(R.id.tv_item);
        //textView = ButterKnife.findById(itemView, R.id.tv_item);
        this.mListener = mListener;
    }

    public void bind(String s) {
        textView.setText(s);
        textView.setTag(s);
        textView.setOnClickListener(mListener);
    }
}
