package com.szyl.szyllibrary.view.poplist.ListWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.szyl.szyllibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//	这个是list列表选择的弹出
public class ListWindow extends PopupWindow implements OnItemClickListener {

	private Context mContext;
	private ListView mListView;
	private ListWindowAdapter mAdapter;

	private IOnItemCBSelectListener mItemSelectListener;
	private List<HashMap<String,Object>> mData;

	public ListWindow(Context context, IOnItemCBSelectListener mItemSelectListener, ArrayList<HashMap<String,Object>> list)
	{
		super(context);
		
		mContext = context;
		this.mItemSelectListener=mItemSelectListener;
		this.mData=list;
		init(list);
	}
	

	private void init(ArrayList<HashMap<String, Object>> list)
	{
		View view = LayoutInflater.from(mContext).inflate(R.layout.control_list_window, null);
		setContentView(view);		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

//		加上了这个以后点击外面就会消失
//		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		mListView = (ListView) view.findViewById(R.id.lv_list_window);
		mAdapter = new ListWindowAdapter(mContext,list);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {

		mItemSelectListener.onCBItemClick(pos);
		this.dismiss();
	}
}
