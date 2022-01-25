package com.szyl.szyllibrary.tools.pickimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.szyl.szyllibrary.R;

import java.util.List;

public class PubShowImgsAdapter extends BaseAdapter {

	Context context;
	List<String> list;

	public PubShowImgsAdapter(Context context) {
		this.context=context;
	}

	public PubShowImgsAdapter(Context context, List<String> data) {
		this.context=context;
		this.list=data;
	}


	public void setData(Context context,List<String> data){
		this.context=context;
		this.list=data;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		Holder holder;
		if (view==null) {
			view=LayoutInflater.from(context).inflate(R.layout.pick_item_selected_imgs, null);
			holder=new Holder();
			holder.imageView=(ImageView) view.findViewById(R.id.imageView);
			holder.delete_img=(ImageView) view.findViewById(R.id.delete_img);
			view.setTag(holder);
		}else {
			holder= (Holder) view.getTag();
		}

		Glide.with(context)
				.load(list.get(position))
				//  .placeholder(R.drawable.loading)
				.error(R.mipmap.icon_error)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.centerCrop()
				.into(holder.imageView);
		holder.delete_img.setVisibility(View.GONE);

		return view;
	}
	
	class Holder{
		ImageView imageView,delete_img;
	}

}
