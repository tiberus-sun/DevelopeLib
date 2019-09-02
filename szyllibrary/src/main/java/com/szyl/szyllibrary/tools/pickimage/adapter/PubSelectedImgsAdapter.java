package com.szyl.szyllibrary.tools.pickimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.szyl.szyllibrary.R;

import java.util.List;

public class PubSelectedImgsAdapter extends BaseAdapter {

	Context context;
	private List<String> mAllPath;
	OnItemClickClass onItemClickClass;

	//默认是模式1：1 add 只是增加模式 2 showadd 显示增加 3 show 只显示
	String module="add";

	public PubSelectedImgsAdapter(Context context) {
		this.context=context;
	}

	//默认模式只增 不需要传module
	public PubSelectedImgsAdapter(Context context,List<String> allPath,OnItemClickClass onItemClickClass) {
		this.context=context;
		this.mAllPath=allPath;
		this.onItemClickClass=onItemClickClass;
	}
	//只显示模式
	public PubSelectedImgsAdapter(String module,Context context,List<String> allPath) {
		this.module=module;
		this.context=context;
		this.mAllPath=allPath;
	}
	//增加显示模式（或传module="add"为增加模式）
	public PubSelectedImgsAdapter(String module,Context context,List<String> allPath,OnItemClickClass onItemClickClass) {
		this.module=module;
		this.context=context;
		this.mAllPath=allPath;
		this.onItemClickClass=onItemClickClass;
	}

	@Override
	public int getCount() {

		if(module.equals("show")){
			if (mAllPath == null) {
				return 0;
			}  else {
				return mAllPath.size();
			}
		}else {
			if (mAllPath == null) {
				return 1;
			}  else {
				return mAllPath.size() + 1;
			}
		}
	}

	@Override
	public Object getItem(int position) {

		if (mAllPath != null) {
			return mAllPath.get(position);
		} else if (mAllPath == null || position - 1 < 0
				|| position > mAllPath.size()) {
			return null;
		} else {
			return mAllPath.get(position - 1);
		}
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

		if(module.equals("show")){
			holder.delete_img.setVisibility(View.GONE);
			Glide.with(context).load(mAllPath.get(position)).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).into(holder.imageView);
		}else {
			if (isShowAddItem(position)) {
				Glide.with(context).load(R.mipmap.image_add).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).into(holder.imageView);
				holder.delete_img.setVisibility(View.GONE);
			}else{
				//表示网络请求来的数据 同时删除按钮要隐藏
				if(mAllPath.get(position).contains("http")){

					Glide.with(context).load(mAllPath.get(position)).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).into(holder.imageView);
					holder.delete_img.setVisibility(View.GONE);
				}else{

					Glide.with(context).load("file://"+mAllPath.get(position)).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).into(holder.imageView);
					holder.delete_img.setVisibility(View.VISIBLE);
				}
				holder.delete_img.setOnClickListener(new OnPhotoClick(mAllPath.get(position)));
			}
		}

		return view;
	}

	class Holder{
		ImageView imageView,delete_img;
	}


	public interface OnItemClickClass{
		public void OnItemClick(View v, String filepath);
	}

	class OnPhotoClick implements OnClickListener{
		String filepath;

		public OnPhotoClick(String  filepath) {
			this.filepath=filepath;
		}
		@Override
		public void onClick(View v) {
			if (mAllPath!=null && onItemClickClass!=null ) {
				onItemClickClass.OnItemClick(v, filepath);
			}
		}
	}
	private boolean isShowAddItem(int position) {

		int size = mAllPath == null ? 0 : mAllPath.size();
		return position == size;
	}

	public void setOnItemClickClass(OnItemClickClass onItemClickClass) {
		this.onItemClickClass = onItemClickClass;
	}
}
