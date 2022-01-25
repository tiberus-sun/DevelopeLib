package com.szyl.szyllibrary.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Method;

public class TsSpecialPermissionUtil {
    private String TAG = "RomUtils";

    private static boolean isXiaoMi() {
        return checkManufacturer("xiaomi");
    }

    private static boolean isOppo() {
        return checkManufacturer("oppo");
    }

    private static boolean isVivo() {
        return checkManufacturer("vivo");
    }

    private static boolean checkManufacturer(String manufacturer) {
        return manufacturer.equals(Build.MANUFACTURER);
    }

    public static boolean isBackgroundStartAllowed(Context context) {
        if (isXiaoMi()) {
            return isXiaomiBgStartPermissionAllowed(context);
        }

        if (isVivo()) {
            return isVivoBgStartPermissionAllowed(context);
        }

        if (isOppo() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    /**
     * 小米后台弹出界面权限检测
     * @param context
     * @return
     */
    public static boolean isXiaomiBgStartPermissionAllowed(Context context) {
        AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            int op = 10021;
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 小米后台锁屏检测方法
     * @param context
     * @return
     */
    public static boolean canShowLockView(Context context) {
        AppOpsManager ops = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
        try {
            int op = 10020; // >= 23
            // ops.checkOpNoThrow(op, uid, packageName)
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]
                    {int.class, int.class, String.class}
            );
            Integer result = (Integer) method.invoke(ops, op,  android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static boolean isVivoBgStartPermissionAllowed(Context context) {
        return getVivoBgStartPermissionStatus(context) == 0;
    }

    /**
     * 判断Vivo后台弹出界面状态， 1无权限，0有权限
     *
     * @param context context
     */
    private static int getVivoBgStartPermissionStatus(Context context) {
        Uri uri = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
        String selection = "pkgname = ?";
        String[] selectionArgs = {context.getPackageName()};
        int state = 1;
        try {
            Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                state = cursor.getInt(cursor.getColumnIndex("currentstate"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }


    /**
     * 判断vivo锁屏显示 1未开启 0开启
     * @param context
     * @return
     */
    public static int getVivoLockStatus(Context context) {
        String packageName = context.getPackageName();
        Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/control_locked_screen_action");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        try {
            Cursor cursor = context
                    .getContentResolver()
                    .query(uri2, null, selection, selectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int currentmode = cursor.getInt(cursor.getColumnIndex("currentstate"));
                    cursor.close();
                    return currentmode;
                } else {
                    cursor.close();
                    return 1;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 1;
    }


    /**
     * 跳转到MIUI应用权限设置页面
     *
     * @param context context
     */
    public static void jumpToPermissionsSettingActivity(Context context) {
        if (isXiaoMi()) {
            try {
                // MIUI 8
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivitycom.miui.permcenter.permissions.PermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e) {
                try {
                    // MIUI 5/6/7
                    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    localIntent.putExtra("extra_pkgname", context.getPackageName());
                    context.startActivity(localIntent);
                } catch (Exception e1) {
                    // 否则跳转到应用详情
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            }
        }else{
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }
}
