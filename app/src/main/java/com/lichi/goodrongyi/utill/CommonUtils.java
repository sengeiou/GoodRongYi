package com.lichi.goodrongyi.utill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.widget.Toast;


import com.lichi.goodrongyi.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:Jenny
 * Date:16/4/29 11:52
 * E-mail:fishloveqin@gmail.com
 * <p/>
 * 公共资源工具类
 **/
public final class CommonUtils {

    private CommonUtils() {

    }

    /**
     * 启动新的Activity页面
     *
     * @param context
     * @param cls
     */
    public static void startNewActivity(Context context, Class cls) {

        Intent intent = new Intent(context, cls);
        context.startActivity(intent);

    }

    /**
     * 启动新的有返回值Activity页面
     *
     * @param activity
     * @param args
     * @param cls
     * @param requestCode
     */
    public static void startResultNewActivity(Activity activity, Map<String, Serializable> args,
                                              Class cls, int requestCode) {

        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (null != args) {

            Iterator iterator = args.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();

                String key = entry.getKey().toString();
                Serializable value = (Serializable) entry.getValue();
                intent.putExtra(key, value);

            }

        }

        activity.startActivityForResult(intent, requestCode);

    }

    /**
     * 启动新的Activity页面
     *
     * @param context
     * @param args
     * @param cls
     */
    public static void startNewActivity(Context context, Map<String, Serializable> args,
                                        Class cls) {

        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (null != args) {

            Iterator iterator = args.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();

                String key = entry.getKey().toString();
                Serializable value = (Serializable) entry.getValue();
                intent.putExtra(key, value);

            }

        }

        context.startActivity(intent);

    }

    /**
     * Toast 提示框
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {

        Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast 提示框
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId) {

        Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检查是否有指定的权限 主要是6.0版本
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static boolean checkSpPermission(Activity activity, String[] permissions,
                                            int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {

            for (String s : permissions) {
                if (!(activity.checkSelfPermission(s)//第一个元素做为需要的权限
                == PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(activity, permissions, requestCode);
                    return false;
                }
            }
            return true;
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }

    }

    private static final String TAG = CommonUtils.class.getSimpleName();

    /**
     * 将位图文件写入到存储设备
     *
     * @param bitmap
     */
    public static String saveImageFileToSD(Bitmap bitmap) {

        FileOutputStream b = null;
        File file = new File(APP_CACHE_DIR);
        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))
                      + ".jpg";
        file.mkdirs();// 创建文件夹
        String fileName = file.getAbsolutePath() + "/" + name;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            b.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return fileName;
    }

    public static final String ROOT          = Environment.getExternalStorageDirectory()
        .getAbsolutePath();
    public static final String APP_CACHE_DIR = ROOT + "/Exhibition_data";

    /**
     * 从asset中获取文件并读取数据,并返回BitmapDrawable
     *
     * @param context
     * @param fileName
     */
    public static BitmapDrawable getFromAsset(Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName); // 从Assets中的文件获取输入流

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            return bitmapDrawable;
        } catch (IOException e) {
            e.printStackTrace(); // 捕获异常并打印
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据字符串数组id获取数组对象
     *
     * @param res
     * @param resId
     * @return
     */
    public static String[] getStringArray(Resources res, int resId) {
        String[] arrays = res.getStringArray(resId);

        return arrays;
    }

    public static void customDisplayText(Resources res, TextView tv, String content, int colorId) {
        SpannableString sp = new SpannableString(content);
        sp.setSpan(new ForegroundColorSpan(res.getColor(colorId)), content.lastIndexOf(" "),
            content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(sp);
    }

    public static void customDisplayText4(Resources res, TextView tv, String content, int colorId) {
        SpannableString sp = new SpannableString(content);
        sp.setSpan(new ForegroundColorSpan(res.getColor(colorId)), content.lastIndexOf(" "),
            content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(sp);
    }

    /**
     *  TextView分块定制化显示
     * @param content
     * @param tv
     * @param res
     */
    public static void customDisplayText(String content, TextView tv, Resources res) {

        Pattern pattern = Pattern.compile("\\d{1,15}");
        Matcher matcher = pattern.matcher(content);
        SpannableString sp = new SpannableString(content);
        while (matcher.find()) {

            int start = matcher.start(0);
            int end = matcher.end(0);
            sp.setSpan(new ForegroundColorSpan(res.getColor(R.color.colorAccent)), start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        tv.setText(sp);
    }

    /**
     *  TextView分块定制化显示
     * @param content
     * @param tv
     * @param res
     */
    public static void customDisplayText(Resources res, String content, TextView tv, int colorId) {

        Pattern pattern = Pattern.compile("\\d{1,15}");
        Matcher matcher = pattern.matcher(content);
        SpannableString sp = new SpannableString(content);
        while (matcher.find()) {

            int start = matcher.start(0);
            int end = matcher.end(0);
            sp.setSpan(new ForegroundColorSpan(res.getColor(colorId)), start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        tv.setText(sp);
    }

    /**
     *  TextView分块定制化显示
     * @param content
     * @param tv
     * @param res
     */
    public static void customDisplayReplyText(String content, TextView tv, Resources res) {

        SpannableString sp = new SpannableString(content);

        int index = content.indexOf("回复");
        int index2 = content.indexOf(":");
        if (index >= 0) {
            sp.setSpan(new ForegroundColorSpan(res.getColor(R.color.colorAccent)), 0, index,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            if (index2 >= 0) {
                sp.setSpan(new ForegroundColorSpan(res.getColor(R.color.colorAccent)), index + 2,
                    index2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }

        }

        tv.setText(sp);
    }

    public static void customDisplayText2(Resources res, TextView tv, String content, int colorId) {
        SpannableString sp = new SpannableString(content);
        int index = content.indexOf("用");
        int index2 = content.indexOf("/");
        sp.setSpan(new ForegroundColorSpan(res.getColor(colorId)), index + 1, index2,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(sp);
    }

    public static void customDisplayText3(Resources res, TextView tv, String content, int colorId) {
        SpannableString sp = new SpannableString(content);
        int index = content.indexOf(":");
        sp.setSpan(new ForegroundColorSpan(res.getColor(colorId)), 0, index + 1,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(sp);
    }

    public static String getMoneyType(String string) {
        // 把string类型的货币转换为double类型。
        Double numDouble = Double.parseDouble(string);
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回
        String numString = format.format(numDouble);
        numString = numString.substring(1, numString.length());
        numString=numString.substring(0,numString.indexOf("."));
        return numString;
    }

}
