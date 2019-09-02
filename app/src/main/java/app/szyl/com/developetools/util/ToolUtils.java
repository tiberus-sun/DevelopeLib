package app.szyl.com.developetools.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.szyl.szyllibrary.view.wheelPicker.picker.DatePicker;
import com.szyl.szyllibrary.view.wheelPicker.picker.DateTimePicker;
import com.szyl.szyllibrary.view.wheelPicker.picker.NumberPicker;
import com.szyl.szyllibrary.view.wheelPicker.picker.OptionPicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static app.szyl.com.developetools.MyApplication.BASE_PATH;

public final class ToolUtils {

     private static Calendar calendar = Calendar.getInstance();

     //获取年
     public static int getYear(){

         return  calendar.get(Calendar.YEAR);
     }
     //获取月
     public static int getMonth(){

        return  calendar.get(Calendar.MONTH) + 1;
     }
     //获取日
     public static int getDay(){

        return  calendar.get(Calendar.DAY_OF_MONTH);
     }

     /**
      * 获取当前日期是星期几
      *
      * @param dt
      * @return 当前日期是星期几
      */
    public static String getWeekByCalendar(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    //获取当前日期是星期几
    public static String getWeekByDate() {
        Date date=new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    //获取随机数
    public static int getRandom(int num ) {
        java.util.Random random=new java.util.Random();// 定义随机类
        int result=random.nextInt(num);// 返回[0,num)集合中的整数，注意不包括num
        return result+1;
    }


    /**
     * 弹出输入框
     *
     * @param context
     * @param view
     * @param show_type
     *            true:显示输入框；false：隐藏
     */
    public static void showInputMethod(Context context, View view, boolean show_type) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show_type) {
            im.showSoftInput(view, 0);
        } else {
            im.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
        }
    }

    public static String getCurrentTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return mDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date(System.currentTimeMillis()));
    }
    public static String getDate() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return mDateFormat.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 判断NULL或空字符串
     */
    public static boolean isNullOrEmpty(String param) {
        return null == param || param.trim().length() == 0;
    }

    /**
     * 判断对象为空
     */
    public static boolean isEmpty(Object param) {

        if (null==param) {
            return true;
        }
        if (param instanceof CharSequence && param.toString().length() == 0) {
            return true;
        }

        if (param.getClass().isArray() && Array.getLength(param) == 0) {
            return true;
        }
        if (param instanceof Collection && ((Collection) param).isEmpty()) {
            return true;
        }

        return false;
    }

    //帐号 （大小写英文字母、汉字、数字、下划线 . 组成的长度4-12个字节）
    public static boolean isAccount(String account) {

        //半角中文：[\u4e00-\u9fa5] 全角：[ufe30-uffa0]
        return account.matches("^([\u4e00-\u9fa5]|[ufe30-uffa0]|[A-Za-z0-9_.]){4,12}$");
    }

    //手机号验证 （大小写英文字母、汉字、数字、下划线 . 组成的长度4-12个字节）
    public static boolean isPhoneNo(String account) {

        //半角中文：[\u4e00-\u9fa5] 全角：[ufe30-uffa0]
        return account.matches("^([\u4e00-\u9fa5]|[ufe30-uffa0]|[0-9]){11}$");
    }

    /**
     * 月日时分秒，0-9前补0
     *
     * @param number the number
     * @return the string
     */
    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    //解析时间 去掉
    public static String parseDate(String dateStr){

        if(!isNullOrEmpty(dateStr)){
            return  dateStr.replaceAll("T"," ");
        }else {
            return dateStr;
        }

    }

    //解析时间 日期去掉时分秒
    public static String dateToStr(String dateStr){
        if(!isNullOrEmpty(dateStr)) {
            dateStr = dateStr.replaceAll("T", " ");
            return  dateStr.substring(0,10);
        }else{
            return dateStr;
        }
    }


    /**
     * 获取软件版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)
    {
        int versionCode = 0;
        try
        {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取软件版本号名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context)
    {
        String versionName = "V1.0";
        try
        {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getImgName() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_"+mDateFormat.format(new Date(System.currentTimeMillis())) + ".png";
    }

    //生成查验图片的名字
    public static String getPicTypeName() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat("ddHHmmss");
        return "CY"+mDateFormat.format(new Date(System.currentTimeMillis()))+ ".png";

    }

    //根据传过来的路径 进行删除文件
    public static void deleteFile(String filepath)
    {

        File file = new File(filepath);
        //不存在创建
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] fl = file.listFiles();

        if(fl!=null){

            for (int i=0; i<fl.length; i++)
            {
                fl[i].delete();
            }
        }
    }

    public static String getDateTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date(System.currentTimeMillis()));
    }



    //保留4位小数
    public static Double Double4(Double count) {
        DecimalFormat df = new DecimalFormat("#.0000");
        return   Double.valueOf(df.format(count));

    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }


    /**
     * 遍历布局，并禁用所有子控件
     * @param viewGroup 布局对象
     */
    public static void setDisable(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    spinner.setClickable(false);
                    spinner.setEnabled(false);

                } else if (v instanceof ListView) {
                    ((ListView) v).setClickable(false);
                    ((ListView) v).setEnabled(false);

                } else {
                    setDisable((ViewGroup) v);
                }
            } else if (v instanceof EditText) {
                ((EditText) v).setEnabled(false);
                ((EditText) v).setClickable(false);

            } else if (v instanceof Button) {
                ((Button) v).setEnabled(false);

            } else if (v instanceof CheckBox) {
                ((CheckBox) v).setEnabled(false);

            } else if (v instanceof TextView) {
                ((TextView) v).setEnabled(false);
            }
        }
    }

    /**
     * 遍历布局，并启用所有子控件
     * @param viewGroup 布局对象
     */
    public static void setEnabled(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    spinner.setClickable(true);
                    spinner.setEnabled(true);

                } else if (v instanceof ListView) {
                    ((ListView) v).setClickable(true);
                    ((ListView) v).setEnabled(true);

                } else {
                    setEnabled((ViewGroup) v);
                }
            } else if (v instanceof EditText) {
                ((EditText) v).setEnabled(true);
                ((EditText) v).setClickable(true);

            } else if (v instanceof Button) {
                ((Button) v).setEnabled(true);

            } else if (v instanceof CheckBox) {
                ((CheckBox) v).setEnabled(true);

            } else if (v instanceof TextView) {
                ((TextView) v).setEnabled(true);
            }
        }
    }



    /**
     * 保存图片至/sdcard/Pictures文件夹下
     * @param bmp
     */
    public static  String saveCroppedImage(Context mcontext,Bitmap bmp) {

        File file = new File(BASE_PATH+"dzqd/");
        if (!file.exists())
            file.mkdir();

        String timeStamp = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());

        String path=BASE_PATH+"dzqd/"+"QD_"+timeStamp+".jpg".trim();
        file = new File(path);

        /*String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));
        String newFilePath = BASE_PATH + mName + sName;
        file = new File(newFilePath);*/

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通知Mediascanner 可以扫描到
        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri fileContentUri = Uri.fromFile(file);
        mediaScannerIntent.setData(fileContentUri);
        mcontext.sendBroadcast(mediaScannerIntent);

        return path;
    }



    /**
     * 图片按比例大小压缩方法
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float ww = 800f;
        float hh = 600f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }


    /**
     * 添加文字到图片，类似水印文字。
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap drawTextToBitmap(Context mContext, Bitmap src, String watermark, float screenWidth) {
        Resources resources = mContext.getResources();
        float density = resources.getDisplayMetrics().density;

        int w = src.getWidth();
        int h = src.getHeight();

        //如果传screenWidth值 说明需要按屏幕宽度缩放
        if(screenWidth>0){

            // 计算缩放比例 保持原图比例 所以缩放宽高缩放值相同
            float scale = ( screenWidth) / w;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        }
        src = src.copy(Bitmap.Config.ARGB_8888, true); // 创建一个新的和SRC长度宽度一样的位图

        Canvas canvas = new Canvas(src);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#458EFD"));
        paint.setTextSize((int) (3 * density*4));
        //阴影
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        Rect bounds = new Rect();
        paint.getTextBounds(watermark, 0, watermark.length(), bounds);
        int x = (src.getWidth() - bounds.width())/10*6 ;
        int y = (src.getHeight() + bounds.height())/10*8;
        int y1 = (src.getHeight() + bounds.height())/10*9;
        canvas.drawText(watermark,  x , y , paint);
        canvas.drawText(getTime(),  x , y1 , paint);

        //旋转角度
        //canvas.rotate(10, x + bounds.exactCenterX(),y - bounds.exactCenterY());
        return src;
    }


    /**
     * 添加文字到图片，类似水印文字。自动换行
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap drawMoreTextToBitmap(Context mContext, Bitmap src, String watermark,float newWidth,float newHeight) {
        Resources resources = mContext.getResources();
        float density = resources.getDisplayMetrics().density;

        int w = src.getWidth();
        int h = src.getHeight();
        /*
        //如果传screenWidth值 说明需要按屏幕宽度缩放
        if(screenWidth>0){
            // 计算缩放比例 保持原图比例 所以缩放宽高缩放值相同
            float scale = ( screenWidth) / w;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        }*/

        if(newWidth>0 && newHeight>0){
            // 计算缩放比例
            float scaleWidth = newWidth / w;
            float scaleHeight = newHeight / h;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        }

        src = src.copy(Bitmap.Config.ARGB_8888, true); // 创建一个新的和SRC长度宽度一样的位图

        Canvas canvas = new Canvas(src);

        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#DC143C"));
        paint.setTextSize((int) (3 * density*4));
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        //位置 自动换行
        StaticLayout layout = new StaticLayout(watermark,paint,src.getWidth()-20, Layout.Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
        if(layout.getLineCount()>1){
            canvas.translate(10,src.getHeight()*3/4);
        }else{
            canvas.translate(10,src.getHeight()*4/5);
        }
        layout.draw(canvas);

        //当前时间
        String time=getTime();
        Rect bounds = new Rect();
        paint.getTextBounds(time, 0, time.length(), bounds);
        canvas.drawText(time, 0,layout.getHeight()+bounds.height(), paint);

        return src;
    }


    /**
     * 给图片加水印，原图调整为按屏幕宽度 高度同比例修改
     * 右下角
     * @param src         原图
     * @param watermark   水印图
     * @param screenWidth 手机屏幕宽度
     * @return 加水印的结果图
     */
    public static Bitmap drawBitmapToBitmap(Bitmap src, Bitmap watermark,float screenWidth) {
        int w = src.getWidth();
        int h = src.getHeight();

        //如果传screenWidth值 说明需要按屏幕宽度缩放
        if(screenWidth>0){

            // 计算缩放比例 保持原图比例 所以缩放宽高缩放值相同
            float scale = ( screenWidth) / w;

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            src = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);
        }

        src = src.copy(Bitmap.Config.ARGB_8888, true);;// 创建一个新的和SRC长度宽度一样的位图// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(src);
        //在canvas上绘制原图和新的水印图
        cv.drawBitmap(src, 0, 0, null);

        watermark =watermark.copy(Bitmap.Config.ARGB_8888, true);

        //水印图绘制在画布的右下角，距离右边和底部都为20
        cv.drawBitmap(watermark, src.getWidth() - watermark.getWidth()-20, src.getHeight() - watermark.getHeight()-20, null);
        //cv.save(Canvas.ALL_SAVE_FLAG);
        //cv.restore();

        return src;
    }


    /**
     * 读取asset文件
     * 使用context.getAssets().open() 方法得到inputstream
     *
     * @param context   the context
     * @param assetPath the asset path
     * @return String string
     */
    public static String readText(Context context, String assetPath) {

        try {
            return ToolUtils.toString(context.getAssets().open(assetPath));
        } catch (Exception e) {
            return "";
        }
    }

    public static String toString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch (IOException e) {

        }
        return sb.toString();
    }



    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr    时间字符串
     * @param dataFormat 当前时间字符串的格式。
     * @return Date 日期 ,转换异常时返回null。
     */
    public static long parseTime(String dateStr, String dataFormat) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
            Date date = dateFormat.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            //LogUtils.warn(e);
            return 0;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return Date 日期 ,转换异常时返回null。
     */
    public static long parseTime(String dateStr) {
        return parseTime(dateStr, "yyyy-MM-dd HH:mm:ss");
    }


    public  static void popTimePicker(final TextView textView, Context context){

        int width= ScreenUtils.getDeviceSmallWidth(context);

        String currentdate= textView.getText().toString();
        if(ToolUtils.isNullOrEmpty(currentdate)){
            currentdate=getTime();
        }

        //2016-09-08
        int  year = Integer.parseInt(currentdate.split("-")[0].toString());
        int  month = Integer.parseInt(currentdate.split("-")[1].toString());
        int  day = Integer.parseInt(currentdate.split("-")[2].split(" ")[0]);
        int  hour = Integer.parseInt(currentdate.split("-")[2].split(" ")[1].split(":")[0]);
        int  minute = Integer.parseInt(currentdate.split("-")[2].split(" ")[1].split(":")[1]);

        DateTimePicker timepicker = new DateTimePicker((Activity)context, DateTimePicker.HOUR_OF_DAY);
        timepicker.setRange(year-30, year+30);
        //n5最小宽度是360dp  5.5寸手机是720pix
        if(width<=600){
            timepicker.setMyPadding(13,8,13,8);
        }else {
            timepicker.setTextSize(17);
            timepicker.setTextOptionSize(19);
        }

        timepicker.setSelectedItem(year, month, day,hour,minute);
        timepicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {

            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {

                textView.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute+":"+"00");
            }

        });

        timepicker.show();
    }

    //单选
    public  static void popOptionPicker(final TextView textView, ArrayList str, Context context,String title){

        int width= ScreenUtils.getDeviceSmallWidth(context);

        String optionString= textView.getText().toString();

        OptionPicker optionPicker = new OptionPicker((Activity)context, str);
        if(width>=600){
            optionPicker.setMyPadding(50,8,50,8);
            optionPicker.setTextSize(17);
            optionPicker.setTextOptionSize(19);
        }
        if(!ToolUtils.isNullOrEmpty(optionString)){
            optionPicker.setSelectedItem(optionString);
        }else{
            optionPicker.setSelectedIndex(1);
        }

        //一些个性化的设置 只需要设置picker.set方法进行设置
        //1-展示3条，2-展示5条，3-展示7条，4-展示9条
        optionPicker.setOffset(2);

        optionPicker.setTitleText(title);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {

                textView.setText(option);
            }
        });
        optionPicker.show();

        textView.getCompoundDrawables()[2].setLevel(1);

        optionPicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                textView.getCompoundDrawables()[2].setLevel(0);
            }
        });
    }


}
