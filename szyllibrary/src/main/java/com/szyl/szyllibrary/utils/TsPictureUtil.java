package com.szyl.szyllibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class TsPictureUtil {


	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		
		return Base64.encodeToString(b, Base64.DEFAULT);
		
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Bitmap getSmallBitmap(String filePath) {

		//可以改变像素 但不是按设置的480 和800 输出,而是计算出比例一个比例 宽高进行同比例缩放
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(filePath, options);
	}


	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

//	删除文件下所有文件
	public  static void deleteFile(String fileUrl){
		File file = new File(fileUrl);
		File[] files = file.listFiles();
		if(files!=null) {
			for (int i = 0; i < files.length; i++) {
				File file1 = files[i];
				file1.delete();
			}

		}
	}


	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/** 保存方法 */
	public static File saveBitmap(String path, Bitmap bm) {
//		Log.e(TAG, "保存图片");
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	public  static void detelePicture(List<String> urls){
		for(int i=0;i<urls.size();i++){

			TsPictureUtil.deleteTempFile(urls.get(i));
		}

	}


	public static Bitmap decodeSampledBitmapFromFile(String filename,
													 int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filename, options);
	}


	/**
	 * 把bitmap
	 * 质量压缩法 转换成String
	 * @param filePath
	 * @return
	 */
	public static String fileToString(String filePath) {

		Bitmap bm = BitmapFactory.decodeFile(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}



	/**
	 * 添加文字到图片，类似水印文字。自动换行
	 * @param src
	 * @param watermark
	 * @return
	 */
	public static Bitmap drawMoreTextToBitmap(Context mContext, Bitmap src, String watermark,float newWidth,float newHeight) {
		Resources resources = mContext.getResources();
		float density = resources.getDisplayMetrics().density;

		int w = src.getWidth();
		int h = src.getHeight();
        /*
        //如果传screenWidth值 说明需要按屏幕宽度缩放
        if(screenWidth>0){
            // 计算缩放比例 保持原图比例 所以缩放宽高缩放值相同
            float scale = ( screenWidth) / w;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        }*/

		if(newWidth>0 && newHeight>0){
			// 计算缩放比例
			float scaleWidth = newWidth / w;
			float scaleHeight = newHeight / h;
			// 取得想要缩放的matrix参数
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
		}

		src = src.copy(Bitmap.Config.ARGB_8888, true); // 创建一个新的和SRC长度宽度一样的位图

		Canvas canvas = new Canvas(src);

		TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor("#DC143C"));
		paint.setTextSize((int) (3 * density*5));
		paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

		//位置 自动换行
		StaticLayout layout = new StaticLayout(watermark,paint,src.getWidth()-20, Layout.Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
		if(layout.getLineCount()>1){
			canvas.translate(10,src.getHeight()*3/4);
		}else{
			canvas.translate(10,src.getHeight()*4/5);
		}
		layout.draw(canvas);

		//当前时间
		String time= TsTimeUtil.getTime();
		Rect bounds = new Rect();
		paint.getTextBounds(time, 0, time.length(), bounds);
		canvas.drawText(time, 0,layout.getHeight()+bounds.height(), paint);

		return src;
	}


	/**
	 * 给图片加水印，原图调整为按屏幕宽度 高度同比例修改
	 * 右下角
	 * @param src         原图
	 * @param watermark   水印图
	 * @param screenWidth 手机屏幕宽度
	 * @return 加水印的结果图
	 */
	public static Bitmap drawBitmapToBitmap(Bitmap src, Bitmap watermark,float screenWidth) {
		int w = src.getWidth();
		int h = src.getHeight();

		//如果传screenWidth值 说明需要按屏幕宽度缩放
		if(screenWidth>0){

			// 计算缩放比例 保持原图比例 所以缩放宽高缩放值相同
			float scale = ( screenWidth) / w;

			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
		}

		src = src.copy(Bitmap.Config.ARGB_8888, true);;// 创建一个新的和SRC长度宽度一样的位图// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(src);
		//在canvas上绘制原图和新的水印图
		cv.drawBitmap(src, 0, 0, null);

		watermark =watermark.copy(Bitmap.Config.ARGB_8888, true);

		//水印图绘制在画布的右下角，距离右边和底部都为20
		cv.drawBitmap(watermark, src.getWidth() - watermark.getWidth()-20, src.getHeight() - watermark.getHeight()-20, null);
		//cv.save(Canvas.ALL_SAVE_FLAG);
		//cv.restore();

		return src;
	}

}
