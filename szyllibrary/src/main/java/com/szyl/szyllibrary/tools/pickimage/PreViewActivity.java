package com.szyl.szyllibrary.tools.pickimage;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.szyl.szyllibrary.R;
import com.szyl.szyllibrary.tools.pickimage.photoview.PhotoView;
import com.szyl.szyllibrary.utils.TsSharedPreUtil;

import java.util.ArrayList;

/**
 * 
 */
public class PreViewActivity extends AppCompatActivity implements OnClickListener{
	
	private static final String STATE_POSITION = "STATE_POSITION";
	ViewPager pager;
	TextView pager_selected,delimg;
	Button commit;
	CheckBox cb;
	ImageView btn_back;
	private ArrayList<String> resultList =null;
	private ArrayList<String> resultListDel =null;
	
	private ArrayList<Boolean> resultBooleanList =null;
	int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_layout_image);

		//改变状态栏颜色 根据情况可注释掉
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().setStatusBarColor(Color.parseColor("#21282C"));
		}

		btn_back=(ImageView) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);

		pager_selected=(TextView) findViewById(R.id.pager_selected);

		commit=(Button) findViewById(R.id.commit);
		commit.setOnClickListener(this);
		delimg=(TextView) findViewById(R.id.delimg);
		delimg.setOnClickListener(this);

		cb=(CheckBox) findViewById(R.id.cb);
		Bundle b=getIntent().getBundleExtra("b");
		resultList=b.getStringArrayList("imglist");
		resultListDel=new ArrayList<String>();
		if (resultList!=null&&resultList.size()>0) {
			resultBooleanList=new ArrayList<Boolean>();
			for(int i=0;i<resultList.size();i++){
				resultBooleanList.add(true);
			}
			commit.setText("完成"+resultList.size()+"/"+resultList.size());
			pager = (ViewPager) findViewById(R.id.pager);
			pager.setAdapter(new ImagePagerAdapter(resultList));
			pager.setCurrentItem(position);
			String posi=(position+1)+"/"+resultList.size();
			pager_selected.setText(posi);
			pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageSelected(int arg0) {
					position=arg0;
					String posi=(arg0+1)+"/"+resultList.size();
					pager_selected.setText(posi);
					cb.setChecked(resultBooleanList.get(position));
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
			});
			
			cb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(resultBooleanList.get(position)){
						resultBooleanList.remove(position);
						resultBooleanList.add(position, false);
						resultListDel.add(resultList.get(position));
					}else{
						resultBooleanList.remove(position);
						resultBooleanList.add(position, true);
						resultListDel.remove(resultList.get(position));
					}
					cb.setChecked(resultBooleanList.get(position));
					commit.setText("完成"+(resultList.size()-resultListDel.size())+"/"+resultList.size());
				}
			});
			
			
			
		}

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	
	private class ImagePagerAdapter extends PagerAdapter {

		private  ArrayList<String> images;
		private LayoutInflater inflater;

		ImagePagerAdapter( ArrayList<String> images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, final int position) {
			View imageLayout = inflater.inflate(R.layout.pick_item_pager_image, view, false);
			assert imageLayout != null;
			PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);
			Glide.with(PreViewActivity.this)
					.load("file://"+images.get(position))
					//  .placeholder(R.drawable.loading)
					.error(R.mipmap.icon_error)
					.diskCacheStrategy(DiskCacheStrategy.NONE)
					.centerCrop()
					.into(imageView);
			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			if(resultListDel!=null&&resultListDel.size()>0){
				String imgpaths="";
				for(String imgpath:resultListDel){
					imgpaths+=imgpath+",";
				}
				imgpaths.subSequence(0, imgpaths.length()-1);//去掉最后一个逗号
				TsSharedPreUtil.saveString(this, "imgsdel", imgpaths);
			}
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int i = v.getId();
		if (i == R.id.commit) {
			if (resultListDel != null && resultListDel.size() > 0) {
				String imgpaths = "";
				for (String imgpath : resultListDel) {
					imgpaths += imgpath + ",";
				}
				imgpaths.subSequence(0, imgpaths.length() - 1);//去掉最后一个逗号
				TsSharedPreUtil.saveString(this, "imgsdel", imgpaths);
			}
			finish();

		} else if (i == R.id.btn_back) {
			finish();

		} else {
		}
	}
	

}
