package com.szyl.szyllibrary.view.wheelPicker.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * 获取屏幕宽高等信息、全屏切换、保持屏幕常亮、截屏等
 *
 */
public final class ScreenUtils {
    private static boolean isFullScreen = false;

    /**
     * Display metrics display metrics.
     *
     * @param context the context
     * @return the display metrics
     */
    public static DisplayMetrics displayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * Width pixels int.
     *
     * @param context the context
     * @return the int
     */
    public static int widthPixels(Context context) {
        return displayMetrics(context).widthPixels;
    }

    /**
     * Height pixels int.
     *
     * @param context the context
     * @return the int
     */
    public static int heightPixels(Context context) {
        return displayMetrics(context).heightPixels;
    }

    /**
     * Density float.
     *
     * @param context the context
     * @return the float
     */
    public static float density(Context context) {
        return displayMetrics(context).density;
    }

    /**
     * Density dpi int.
     *
     * @param context the context
     * @return the int
     */
    public static int densityDpi(Context context) {
        return displayMetrics(context).densityDpi;
    }

    /**
     * Is full screen boolean.
     *
     * @return the boolean
     */
    public static boolean isFullScreen() {
        return isFullScreen;
    }

    /**
     * Toggle full displayMetrics.
     *
     * @param activity the activity
     */
    public static void toggleFullScreen(Activity activity) {
        Window window = activity.getWindow();
        int flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (isFullScreen) {
            window.clearFlags(flagFullscreen);
            isFullScreen = false;
        } else {
            window.setFlags(flagFullscreen, flagFullscreen);
            isFullScreen = true;
        }
    }

    /**
     * 保持屏幕常亮
     *
     * @param activity the activity
     */
    public static void keepBright(Activity activity) {
        //需在setContentView前调用
        int keepScreenOn = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        activity.getWindow().setFlags(keepScreenOn, keepScreenOn);
    }

    /**
     * dp转换为px
     *
     * @param context the context
     * @param dpValue the dp value
     * @return int int
     */
    public static int toPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pxValue = (int) (dpValue * scale + 0.5f);
        return pxValue;
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
}
