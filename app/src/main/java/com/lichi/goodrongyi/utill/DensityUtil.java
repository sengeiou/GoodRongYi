package com.lichi.goodrongyi.utill;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArDu on 2017/12/5.
 * px 和 dp 之间转化的工具类
 */

public class DensityUtil {

    /*
       根据手机分辨率从dp的单位 转为px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*
        根据手机的分辩率从px（像素）的单位转化为dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *    * 查找字符串pattern在str中第一次出现的位置
     *    * @param str
     *    * @param pattern
     *    * @return
     */
    public int firstIndexOf(String str, String pattern) {
        for (int i = 0; i < (str.length() - pattern.length()); i++) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) return i;
        }
        return -1;
    }

    /**
     *    * 查找字符串pattern在str中最后一次出现的位置
     *    * @param str
     *    * @param pattern
     *    * @return
     *    
     */


    public int lastIndexOf(String str, String pattern) {
        for (int i = str.length() - pattern.length(); i >= 0; i--) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) return i;
        }
        return -1;
    }

    /**
     *    * 查找字符串pattern在str中出现的位置
     *    * @param str
     *    * @param pattern
     *    * @return
     *    
     */

    public List<Integer> indexOf(String str, String pattern) {
        List<Integer> indexs = new ArrayList<Integer>();
        for (int i = 0; i < (str.length() - pattern.length()); i++) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) indexs.add(i);
        }
        return indexs;
    }
}
