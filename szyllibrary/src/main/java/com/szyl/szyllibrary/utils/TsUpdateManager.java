package com.szyl.szyllibrary.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.szyl.szyllibrary.view.TsToastView;
import com.szyl.szyllibrary.view.dialog.ios.TsAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 版本更新
 */
public class TsUpdateManager {

    private Boolean hasNewVersion;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;

    /* 下载保存路径 */ // 获得存储卡的路径
    private final String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private final boolean cancelUpdate = false;

    private final Context mContext;
    private String mVersionNo = "0";
    private final String mUpdateUrl;

    /* 更新进度条修改的 */
    private ProgressDialog progressDialog;

    private final MyHandler mHandler = new MyHandler(this);

    /**
     * 检查软件是否有更新版本
     * 本系统是在登录的时候后台返回版本号
     * 不用调用方法去读取服务器版本配置文件
     *
     * @return
     */
    public boolean isUpdate() {
        // 获取当前软件版本
        int versionCode = TsDeviceUtil.getAppVersionNo(mContext);
        //服务器版本
        Integer serviceCode = Integer.valueOf(mVersionNo);
        // 版本判断
        return serviceCode > versionCode;
    }

    /*public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    progressDialog.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };*/

//    public TsUpdateManager(Context context) {
//        this.mContext = context;
//    }
//
//    public TsUpdateManager(Context context, String updateUrl) {
//        this.mContext = context;
//        this.mUpdateUrl = updateUrl;
//    }

    public TsUpdateManager(Context context, String versionNo, String updateUrl) {
        this.mContext = context;
        this.mVersionNo = versionNo;
        this.mUpdateUrl = updateUrl;
        this.mSavePath = context.getExternalCacheDir().getPath();

    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        if (isUpdate()) {
            // 显示提示对话框
            showNoticeDialog();
        } else {
            TsToastView.normal(mContext, TsDeviceUtil.getAppVersionName(mContext) + "已经是最新版本").show();
        }
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {

        //弹出要给ProgressDialog
        progressDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
        //progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载中，请稍后......");
        //  设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为水平的样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //取消下载方法
		/*progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
					}
				});*/

        progressDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog() {

        new TsAlertDialog(mContext).builder().setTitle("软件更新").setMsg("检测到新版本，为了不影响您的使用，请立即更新。")
                .setPositiveButton("取消更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setNegativeButton("立刻更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果有相应的版本直接安装 否则下载
                if (!installApk()) {
                    if (!TextUtils.isEmpty(mUpdateUrl)) {
                        // 显示下载对话框
                        showDownloadDialog();
                    }
                }

            }
        }).show();

        //setCancelable(false)
    }

    private static class MyHandler extends Handler {

        private final WeakReference<TsUpdateManager> mTsUpdateManager;

        public MyHandler(TsUpdateManager tsUpdateManager) {
            mTsUpdateManager = new WeakReference<TsUpdateManager>(tsUpdateManager);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (mTsUpdateManager != null && mTsUpdateManager.get() != null) {
                TsUpdateManager tsUpdateManager = mTsUpdateManager.get();
                switch (msg.what) {
                    // 正在下载
                    case DOWNLOAD:
                        // 设置进度条位置
                        tsUpdateManager.progressDialog.setProgress(tsUpdateManager.progress);
                        break;
                    case DOWNLOAD_FINISH:
                        // 安装文件
                        tsUpdateManager.installApk();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
//                File file1=new File(mSavePath);
//                if(!file1.exists()){
//                    file1.mkdir();
//                }
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    URL url = new URL(mUpdateUrl);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);

                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File apkFile = new File(mSavePath, mVersionNo + "app.apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte[] buf = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            progressDialog.dismiss();

        }
    }

    /**
     * 安装APK文件
     */
    private boolean installApk() {
        File apkfile = new File(mSavePath, mVersionNo + "app.apk");
        if (!apkfile.exists()) {
            return false;
        }
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri apkUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
            intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
//            Uri apkUri = Uri.fromFile(toInstall);
            intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
       /* //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getApplicationInfo().packageName + ".Fileprovider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }*/
        // 仅需改变这一行
        TsFileProvider7.setIntentDataAndType(mContext,
                intent, "application/vnd.android.package-archive", apkfile, true);
//                     intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        return true;
    }

}
