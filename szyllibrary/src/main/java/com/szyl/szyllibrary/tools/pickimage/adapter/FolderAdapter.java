package com.szyl.szyllibrary.tools.pickimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.szyl.szyllibrary.R;
import com.szyl.szyllibrary.tools.pickimage.entity.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件夹Adapter
 */
public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Folder> mFolders = new ArrayList<Folder>();

    int mImageSize;

    int lastSelected = 0;

    public FolderAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
    }

    /**
     * 设置数据集
     * @param folders
     */
    public void setData(List<Folder> folders) {
        if(folders != null && folders.size()>0){
            mFolders = folders;
        }else{
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolders.size()+1;
    }

    @Override
    public Folder getItem(int i) {
        if(i == 0) return null;
        return mFolders.get(i-1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.pick_item_folder, viewGroup, false);
            holder = new ViewHolder(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null) {
            if(i == 0){
                holder.name.setText("所有图片");
                holder.size.setText(getTotalImageSize()+"张");
                if(mFolders.size()>0){
                    Folder f = mFolders.get(0);
                    Glide.with(mContext)
                            .load(new File(f.cover.path))
                            //.asBitmap()//只加载静态图片，如果是git图片则只加载第一帧。
                            .error(R.mipmap.icon_error)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerCrop()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .into(holder.cover);
                }
            }else {
                holder.bindData(getItem(i));
            }
            if(lastSelected == i){
                holder.indicator.setVisibility(View.VISIBLE);
            }else{
                holder.indicator.setVisibility(View.GONE);
            }
        }
        return view;
    }

    private int getTotalImageSize(){
        int result = 0;
        if(mFolders != null && mFolders.size()>0){
            for (Folder f: mFolders){
                result += f.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if(lastSelected == i) return;

        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex(){
        return lastSelected;
    }

    class ViewHolder{
        ImageView cover;
        TextView name;
        TextView size;
        ImageView indicator;
        ViewHolder(View view){
            cover = (ImageView)view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.name);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            view.setTag(this);
        }

        void bindData(Folder data) {
            name.setText(data.name);
            size.setText(data.images.size()+"张");
            // 显示图片
            Glide.with(mContext)
                    .load(new File(data.cover.path))
                    //.asBitmap()//只加载静态图片，如果是git图片则只加载第一帧。
                    //  .placeholder(R.drawable.loading)
                    .error(R.mipmap.icon_error)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(cover);
            // TODO 选择标识

        }
    }

}
