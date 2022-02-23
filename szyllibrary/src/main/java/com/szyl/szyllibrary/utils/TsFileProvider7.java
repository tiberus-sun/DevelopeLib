package com.szyl.szyllibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Android7.0 api 24  FileProvider适配
 *
 * 安装apk 或是图片选择功能 需要路径 需要使用FileProvider
 */
public class TsFileProvider7 {

    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }


    public static Uri getUriForFile24(Context context, File file) {
        //context.getPackageName() 或是 context.getApplicationInfo().packageName 得到都是应用的包名
        Uri fileUri = FileProvider.getUriForFile(context.getApplicationContext(),
                "com.szyl.szyllibrary.FileProvider",
                file);
        return fileUri;
    }

    /**
     *
     * @param context
     * @param intent
     * @param type  类型
     * @param file
     * @param writeAble 是否需要写的权限
     */
    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            /**
             *   Uri apkUri = FileProvider.getUriForFile(context, "com.ylzh.app.forestfire3", file);
             *                     intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
             *                     intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
             */
            intent.setType("*/*");
//            intent.putExtra(Intent.EXTRA_STREAM, getUriForFile(context, file));
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.grantUriPermission(context.getPackageManager().toString(), getUriForFile(context, file), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

    public static void setIntentData(Context context,
                                     Intent intent,
                                     File file,
                                     boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setData(getUriForFile(context, file));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setData(Uri.fromFile(file));
        }
    }


    public static void grantPermissions(Context context, Intent intent, Uri uri, boolean writeAble) {

        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, flag);
        }
    }
}
