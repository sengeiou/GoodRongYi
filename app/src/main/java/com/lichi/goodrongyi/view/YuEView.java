package com.lichi.goodrongyi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by test on 2018/1/30.
 */

public class YuEView extends TextView implements Runnable {
    private static int COUNT = 50;//表示数字共变化多少次
    private int mTextColor = 0xF3E5C9;//我们给控件一个默认的显示颜色
    private float mValue = 0.0f;//我们要显示的数字内容
    private float mAverage = 0.0f;//一个平均数值；使我们每次加的数值都是一个平均值
    private int mCount = 0;//当前文本内容变化的次数

    public YuEView(Context context) {
        this(context, null);
    }

    public YuEView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAverage = mValue / COUNT;
        setTextColor(mTextColor);
        setText(String.valueOf(0.0f));
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(float mValue, float num,int count) {
        this.mValue = num;
        COUNT = count;
        mAverage = (mValue - num) / COUNT;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    @Override
    public void run() {
        while (mCount < COUNT) {
            mCount++;
            mValue += mAverage;
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            post(new Runnable() {
                @Override
                public void run() {
                    setText(String.format("%.2f", mValue));
                }
            });
        }
    }
}

