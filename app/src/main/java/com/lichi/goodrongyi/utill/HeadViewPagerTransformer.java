package com.lichi.goodrongyi.utill;

/**
 * Created by smacr on 2017/3/23.
 * *
 *设置在不同位置显示的活动动画
 */

import android.support.v4.view.ViewPager;
import android.view.View;


public class HeadViewPagerTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 1f;

    @Override
    public void transformPage(View view, float position) {

        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(1f);
        }
        else if (position <= 0) { // [-1,0]
            if (position==0){
                view.setAlpha(1);
            }else {
                view.setAlpha(1f);
            }
            view.setTranslationX(0);
            float x = -0.626f * (1f / 3f) * pageWidth * position;
            view.setTranslationX(x);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1  - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
        else if (position <= 1) { // (0,1]
            view.setAlpha(1f);
            float x = -0.626f * (1f / 3f) * pageWidth * position;
            view.setTranslationX(x);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1  - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        }
    }
}
