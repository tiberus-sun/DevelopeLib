package com.szyl.szyllibrary.view.picdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.szyl.szyllibrary.BuildConfig;
import com.szyl.szyllibrary.R;
import com.szyl.szyllibrary.tools.pickimage.photoview.PhotoView;
import com.szyl.szyllibrary.tools.pickimage.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面点击发布，弹出半透明对话框
 * Created by xiaoke on 2016/4/28.
 */
public class PicShowDialog extends Dialog {
    private Context context;
    private List<String> imageInfos;
    private PicViewPager vp;
    private List<View> views = new ArrayList<View>();
    private LinearLayout ll_point;
    private ViewPagerAdapter pageAdapter;
    private int position;
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(10, 10);

    public PicShowDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public PicShowDialog(Context context, List<String> imageInfos, int position) {
        this(context, R.style.Pic_Dialog);
        this.imageInfos = imageInfos;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.layout_dialog_pic);
            getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            //getWindow().setLayout(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            vp = (PicViewPager) findViewById(R.id.vp);
            ll_point = (LinearLayout) findViewById(R.id.ll_point);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initMyPageAdapter();
        vp.setCurrentItem(position);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {

                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus2);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal2);
                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new ViewPagerAdapter();
            if (vp != null) {
                vp.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }

    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        if (imageInfos.size()==1){
            ll_point.setVisibility(View.GONE);
        }else {

            for (int i = 0; i < imageInfos.size(); i++) {
                View view = new View(context);
                paramsL.setMargins(dip2px(context, 5), dip2px(context, 2), 0, dip2px(context, 5));
                view.setLayoutParams(paramsL);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.point_focus2);
                } else {
                    view.setBackgroundResource(R.drawable.point_normal2);
                }

                views.add(view);
                ll_point.addView(view);
            }
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageInfos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view =View.inflate(context, R.layout.pick_item_pic_show, null);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.pic_pv);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.loading);

            String mImageUrl=imageInfos.get(position);
            //res mipmap中的文件
            if(mImageUrl.contains("mipmap")){
                int imgInt=context.getResources().getIdentifier(mImageUrl, "mipmap", BuildConfig.LIBRARY_PACKAGE_NAME);
                photoView.setImageResource(R.mipmap.mipmap_user);
            }else{

                if(mImageUrl.contains("http")){

                }else{
                    mImageUrl = "file://"+mImageUrl;
                }

                progressBar.setVisibility(View.VISIBLE);
                Glide.with(context).load(mImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        photoView.setImageDrawable(resource);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        //Toast.makeText(context, "图片加载失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    dismiss();
                }
            });

            ((ViewPager) container).addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}

