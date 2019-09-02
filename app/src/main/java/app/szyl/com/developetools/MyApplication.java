package app.szyl.com.developetools;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import app.szyl.com.developetools.tools.crash.CrashHandler;

public class MyApplication extends Application {
	/** SDCard路径*/
	public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	/** 文件夹路径*/
	public static final String BASE_PATH = SD_PATH +"/"+Environment.DIRECTORY_PICTURES+"/DevelopeTools/";

	/** 缓存图片路径*/
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "images/";


	@Override
	public void onCreate() {

		createDir();
		//ImageLoaderConfig.initImageLoader(this, new File(BASE_IMAGE_CACHE));


		//开启自动获取异常日志
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);

	}

	/**
	 * 创建APP目录
	 */
	private void createDir() {
		File fileApplication = new File(BASE_PATH);
		File fileCacheImg = new File(BASE_IMAGE_CACHE);
		if (!fileApplication.exists()) {
			fileApplication.mkdirs();
		}
		
		if (!fileCacheImg.exists()) {
			fileCacheImg.mkdirs();
		}
	}

}
