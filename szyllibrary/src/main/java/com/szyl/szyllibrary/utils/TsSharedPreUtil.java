package com.szyl.szyllibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class TsSharedPreUtil {
    // sp的名字
    public final static String SP_NAME = "szyllibrary_config";
    private static SharedPreferences sp;

    public static void saveBoolean(Context context, String key, boolean value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        sp.edit().putBoolean(key, value).commit();
    }

    public static void saveInt(Context context, String key, int value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        sp.edit().putInt(key, value).commit();
    }

    public static void saveString(Context context, String key, String value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getInt(key, defValue);

    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getString(key, defValue);

    }

    // SharedPreferences sp = getSharedPreferences("config", 0);
    //
    // boolean result = sp.getBoolean("isUpdate", false);

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getBoolean(key, defValue);
    }


    // String 类型
    public static void removeShared(Context context, String name) {

        if (sp == null) {
            // 实例化SharedPreferences对象（第一步）
            sp = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
            // 实例化SharedPreferences.Editor对象（第二步）
        }
        // 用remove方法清除
        sp.edit().remove(name).commit();
    }

    // String 类型全部
    public static void removeAllShared(Context context) {

        if (sp == null) {
            // 实例化SharedPreferences对象（第一步）
            sp = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
            // 实例化SharedPreferences.Editor对象（第二步）
        }
        sp.edit().clear().commit();
    }

}
