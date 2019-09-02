package com.szyl.szyllibrary.view.poplist.ExpandableListWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.szyl.szyllibrary.R;

import java.util.ArrayList;

//	这个是list列表选择的弹出
public class ExpandableListWindow extends PopupWindow implements ExpandableListView.OnChildClickListener {

	private Context mContext;
	private ExpandableListView mExListView;
	private ExpandableListWindowAdapter mAdapter;

	private ExSelectListener exSelectListener;
	private ArrayList<Group> gmData;
	private ArrayList<ArrayList<Item>> imData;


	public static int childPositionCurrent =-1;
	public static int groupPositionCurrent=-1;

	public ExpandableListWindow(Context context, ExSelectListener exSelectListener, ArrayList<Group> gData, ArrayList<ArrayList<Item>> iData)
	{
		super(context);
		
		mContext = context;
		this.exSelectListener =exSelectListener;
		this.gmData=gData;
		this.imData=iData;
		init();
	}
	

	private void init()
	{
		View view = LayoutInflater.from(mContext).inflate(R.layout.contorl_exlist_window, null);
		setContentView(view);		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

//		加上了这个以后点击外面就会消失
		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		mExListView = (ExpandableListView) view.findViewById(R.id.lv_exlist_window);

		mAdapter = new ExpandableListWindowAdapter(mContext,gmData,imData);
		mExListView.setAdapter(mAdapter);
		mExListView.setOnChildClickListener(this);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		//Toast.makeText(mContext, "你点击了：" + imData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();

		childPositionCurrent = childPosition;
		groupPositionCurrent = groupPosition;
		mAdapter.notifyDataSetChanged();

		exSelectListener.onClick(imData.get(groupPosition).get(childPosition).getiName());
		this.dismiss();

		return true;
	}
}
