package app.szyl.com.developetools.tools.crash;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.tools.okhttp.CallBackUtil;
import app.szyl.com.developetools.tools.okhttp.OkhttpUtil;
import app.szyl.com.developetools.util.ToolUtils;
import okhttp3.Call;

import static app.szyl.com.developetools.config.Api.CrashURL;

/**
 * Created by tiberus on 2018/9/25.
 */

public class UploadCashService extends IntentService {

    public UploadCashService(String name) {
        super(name);
    }

    public UploadCashService() {
        super("UploadCashService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Crash crash= (Crash)intent.getSerializableExtra("crash");
        upLoadCash(getApplicationContext(),"",crash);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //开启上传
        String path= intent.getStringExtra("path");
        Crash crash= (Crash)intent.getSerializableExtra("crash");
        upLoadCash(getApplicationContext(),path,crash);
        return super.onStartCommand(intent, flags, startId);

    }
    //上报异常
    private void upLoadCash(final Context context, String path, Crash crash) {

        crash.setCrashRank("Systemcrash");
        crash.setCrashDate(ToolUtils.getTime());
        crash.setAppName(context.getString(R.string.app_name));
        String json=new Gson().toJson(crash);

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("json",json);
        OkhttpUtil.okHttpPost(CrashURL, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
            }
        });


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