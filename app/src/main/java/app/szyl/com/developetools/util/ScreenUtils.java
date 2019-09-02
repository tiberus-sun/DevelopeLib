package app.szyl.com.developetools.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 *
 * Created by tiberus on 2016/3/2.
 * 获得屏幕相关的辅助类
 */

public class ScreenUtils {


	/**
	 * 获取当前屏幕 DispatchMetrics信息
	 *
	 * @param context the context
	 * @return the display metrics
	 */
	public static DisplayMetrics displayMetrics(Context context) {
		//法一：
		// 获取当前屏幕
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		// 返回当前资源对象的DispatchMetrics信息。
		windowManager.getDefaultDisplay().getMetrics(dm);

		//法二：
		/*
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();*/

		return dm;
	}


	/**
	 * 获得屏幕宽度像素 pix
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {

		return displayMetrics(context).widthPixels;
	}

	/**
	 * 获得屏幕高度像素  pix
	 * @param context
	 * @return
	 */

	public static int getScreenHeight(Context context) {

		return displayMetrics(context).heightPixels;
	}



	/**
	 * 获得设备最小宽度 返回宽度单位dp
	 * 5.5寸手机宽度为360dp 14寸平板 800dp
	 * @param context
	 * @return
	 */
	public static int getDeviceSmallWidth(Context context)
	{
		Configuration deviceconfig = context.getResources().getConfiguration();
		int smallestScreenWidth = deviceconfig.smallestScreenWidthDp;
		return smallestScreenWidth;
	}

	/**
	 * dp转换为px dp等同于dip
	 * @param context
	 * @param dpValue
	 * @return int int
	 */
	public static int dp2px(Context context, float dpValue) {

		// 密度因子 scale=dm.density 等于 dm.densityDpi/160
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	/**
	 * px转换为dp dp等同于dip
	 * @param context
	 * @param pxValue
	 * @return int int
	 */
	public static int px2dp(Context context, float pxValue) {

		// 密度因子 scale=dm.density 等于 dm.densityDpi/160
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}



	/**
	 * 获取context宽高
	 */
	public static Point getDeviceSize(Context ctx) {
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		Point size = new Point();
		size.x = dm.widthPixels;
		size.y = dm.heightPixels;
		return size;
	}

	/**
	 * 获得状态栏的高度
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取状态栏高度 方法二
	 *
	 * @param context context
	 * @return 状态栏高度
	 */
	private int getStatusBarHeight(Context context) {
		// 获得状态栏高度
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}


	/**
	 * 获取当前屏幕截图，包含状态栏
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 保持屏幕常亮
	 * @param activity the activity
	 */
	public static void keepBright(Activity activity) {
		//需在setContentView前调用
		int keepScreenOn = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		activity.getWindow().setFlags(keepScreenOn, keepScreenOn);
	}

	/**
	 * 获取手机唯一标识
	 */
	public static String getDeviceId(Context mContext) {
		TelephonyManager TelephonyMgr = (TelephonyManager)mContext.getSystemService(TELEPHONY_SERVICE);
		return TelephonyMgr.getDeviceId();
	}

}
