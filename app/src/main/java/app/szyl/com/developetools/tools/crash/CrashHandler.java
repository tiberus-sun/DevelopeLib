package app.szyl.com.developetools.tools.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.util.ToolUtils;

import static app.szyl.com.developetools.config.Api.CrashURL;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/ryg_test/log/";

    public static final String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    //用来储存上传服务器的数据
    private Crash crash=new Crash();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss SSS");

    private String filePath="";
    private String result;

    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;

        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {

//            Intent intent=new Intent(mContext, UploadCashService.class);
//            intent.putExtra("path",filePath); //文件路径
//            intent.putExtra("crash",crash); //异常信息
//            mContext.startService(intent);
            upLoadCash(mContext,"",crash);
            //跳转到首页
//            Intent in = new Intent(mContext, MainActivity.class);
            //in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 如果设置了此标志，这个activity将成为一个新task的历史堆栈中的第一个activity
//            mContext.startActivity(in);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }

//             退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常3S退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();

            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);


                //版本号 手机型号
                crash.setVersionCode(versionName);
                crash.setDeviceModel(Build.MODEL);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        result = writer.toString();
        sb.append(result);

        //异常信息
        crash.setCrashInfo(result);

        try {
            String time = formatter.format(new Date());
            String fileName = time + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = PATH;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes("UTF-8"));
                fos.close();

                filePath=path + fileName;
            }

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
    //上报异常
    private void upLoadCash(final Context context, String path, Crash crash) {

        crash.setCrashRank("Systemcrash");
        crash.setCrashDate(ToolUtils.getTime());
        crash.setAppName(context.getString(R.string.app_name));
        crash.setCrashInfo(result);
        String json=new Gson().toJson(crash);

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("json",json);
        RequestParams requestParams = new RequestParams(CrashURL);
        requestParams.addBodyParameter("json", json);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(context,"异常上传成功,程序退出",Toast.LENGTH_SHORT).show();
                // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(context,"异常上传失败,程序退出",Toast.LENGTH_SHORT).show();
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String s) {
                return false;
            }
        });
//        OkhttpUtil.okHttpPost(CrashURL, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//                Log.e("Crash_",e.toString());
//                // 退出程序
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
//                // 退出程序
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//            }
//        });


       /* final File file = new File(path);
        final Map<String, String> headers = new HashMap<String, String>();
        final Map<String, String> params = new HashMap<String, String>();

        headers.put("ContentType", "multipart/form-data");
        headers.put("dcode", CommonUtil.getDeviceInfo(context));//手机唯一标识
        headers.put("brand", android.os.Build.BRAND);   //手机品牌
        headers.put("model", Build.MODEL);//手机型号
        headers.put("ver", android.os.Build.VERSION.RELEASE);//系统版本号
        headers.put("appver", String.valueOf(CommonUtil.getVersion(context)));//app版本号
        headers.put("verName", String.valueOf(CommonUtil.getVersionName(context)));//app版本名
        headers.put("appname", "CLIENT");*/

        /*PostFormBuilder post = OkHttpUtils.post()
                .url(new Constant(context).CASHUPLOAD)
                .params(params)
                .headers(headers);

        post.addFile("mFile", System.currentTimeMillis() + Math.random() * 10 + ".txt",
                file);
        post.build().execute(new Callback<Object>() {

            @Override
            public Object parseNetworkResponse(
                    Response response)
                    throws Exception {
                // TODO Auto-generated method stub
                if (file.exists()) {
                    file.delete();
                }

                return null;
            }

            @Override
            public void onError(Call call,
                                Exception e) {
                // TODO Auto-generated method stub
                MyToast.showToast(context, "错误日志上传失败");
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);

            }

            @Override
            public void onResponse(Object response) {
                // TODO Auto-generated method stub

            }
        });*/

    }
}