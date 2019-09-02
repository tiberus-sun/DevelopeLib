package app.szyl.com.developetools.tools.crash;

import java.io.Serializable;

/**
 * Created by tiberus on 2018/9/25.
 */

public class Crash implements Serializable{
    private String appName;     //app名称
    private String versionCode; //app版本号
    private String deviceModel; //设备型号
    private String crashInfo;   //异常信息
    private String crashDate;   //异常时间
    private String crashRank;   //异常等级

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getCrashInfo() {
        return crashInfo;
    }

    public void setCrashInfo(String crashInfo) {
        this.crashInfo = crashInfo;
    }

    public String getCrashDate() {
        return crashDate;
    }

    public void setCrashDate(String crashDate) {
        this.crashDate = crashDate;
    }

    public String getCrashRank() {
        return crashRank;
    }

    public void setCrashRank(String crashRank) {
        this.crashRank = crashRank;
    }
}
