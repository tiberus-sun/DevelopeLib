package com.szyl.szyllibrary.view.poplist.ExpandableListWindow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szyl.szyllibrary.R;

import java.util.ArrayList;

import static com.szyl.szyllibrary.view.poplist.ExpandableListWindow.ExpandableListWindow.childPositionCurrent;
import static com.szyl.szyllibrary.view.poplist.ExpandableListWindow.ExpandableListWindow.groupPositionCurrent;

/**
 *
 */
public class ExpandableListWindowAdapter extends BaseExpandableListAdapter {

    private ArrayList<Group> gData;
    private ArrayList<ArrayList<Item>> iData;
    private Context mContext;

    public ExpandableListWindowAdapter(Context mContext , ArrayList<Group> gData, ArrayList<ArrayList<Item>> iData) {
        this.mContext = mContext;
        this.gData = gData;
        this.iData = iData;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Item getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    //private int i=0;
    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //i= groupPosition;
        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.control_exlist_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
            groupHolder.tv_group_icon = (ImageView) convertView.findViewById(R.id.tv_group_icon);

            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_group_name.setText(gData.get(groupPosition).getgName());

        if(isExpanded){
            groupHolder.tv_group_icon.setImageResource(R.mipmap.arrow_up);
        }else{
            groupHolder.tv_group_icon.setImageResource(R.mipmap.arrow_down);
        }

        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ViewHolderItem itemHolder;

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.control_exlist_item, parent, false);
            itemHolder = new ViewHolderItem();

            itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(itemHolder);

            itemHolder.tv_name.setText(iData.get(groupPosition).get(childPosition).getiName());


            if (childPositionCurrent == childPosition && groupPositionCurrent == groupPosition) {
                itemHolder.tv_name.setTextColor(Color.parseColor("#263BA6"));
            } else {
                itemHolder.tv_name.setTextColor(Color.parseColor("#666666"));
            }

            return convertView;

    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class ViewHolderGroup{
        private TextView tv_group_name;
        private ImageView tv_group_icon;
    }

    private static class ViewHolderItem{
        private TextView tv_name;
    }

}
