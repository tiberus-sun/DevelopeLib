package com.szyl.szyllibrary.view.poplist.ListWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected LayoutInflater inflater;
	public List<T> mDatas;
	public Context mContext;
	public ListViewInterface myinflater;
	public void  setUpdata(List<T> mDatas_){
		if(mDatas_!=null)
			this.mDatas = mDatas_;
		else
			this.mDatas=new ArrayList<>();
	}

	public void setInflater(ListViewInterface myinflater){
		this.myinflater=myinflater;
	}
	
	public MyBaseAdapter(Context mContext, List<T> mDatas) {
		super();
		this.mDatas = mDatas;
		this.mContext = mContext;
		inflater= LayoutInflater.from(mContext);
	}
	public void setData(List<T> mDatas){
		if(mDatas!=null)
		this.mDatas = mDatas;
		else
		this.mDatas=new ArrayList<>();
		notifyDataSetChanged();
	}


	public MyBaseAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
