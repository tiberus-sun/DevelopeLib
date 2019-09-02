package com.szyl.szyllibrary.view.poplist.ListWindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szyl.szyllibrary.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ListWindowAdapter<T> extends MyBaseAdapter {

    public static final String NAME = "name";



    public ListWindowAdapter(Context mContext, ArrayList<HashMap<String, Object>> list) {
        super(mContext,list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.control_list_item, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap<String,Object> data = (HashMap<String, Object>) mDatas.get(position);
        viewHolder.tvItemCw.setText((String) data.get(NAME));
        return convertView;
    }



    static class ViewHolder {

        TextView tvItemCw;

    }



}
