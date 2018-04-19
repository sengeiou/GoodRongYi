package com.lichi.goodrongyi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.utill.DensityUtil;

/**
 * Created by ArDu on 2017/12/5.
 */

public class DynamicWave extends View {
    //波纹颜色
    //private static final int WAVE_PAINT_COLOR = 0xec2898fa;
    // y = Asin(wx+b) + n
    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;
    //第一条水波的移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 7;
    //第二条水波的移动速度
    private static final int TRANSLATE_X_SPEED_TWO = 5;
    private float mCycleFactorW;
    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;
    private int mXTwoOffset;
    private Paint mWavePaint;
    private DrawFilter mDrawFilter;
    private float mWaveHightRatio;
    private int mWaveColor;
    public DynamicWave(Context context, AttributeSet attrs) {
        super(context,attrs);
        //获得自定义的属性值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DynamicWave);
        mWaveHightRatio = a.getFloat(R.styleable.DynamicWave_waveHightRatio,0.5f);
        mWaveColor = a.getColor(R.styleable.DynamicWave_waveColor,0xec2898fa);
        // 将dp 转化为px，用于控制不同分辩率上的移动速度基本一致
        mXOffsetSpeedOne = DensityUtil.dip2px(context,TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = DensityUtil.dip2px(context,TRANSLATE_X_SPEED_TWO);
        //初始绘制波纹的画笔
        mWavePaint = new Paint();
        //去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        //设置风格为实线
        mWavePaint.setStyle(Paint.Style.FILL);
        //设置画笔yanse
        mWavePaint.setColor(mWaveColor);
        mDrawFilter = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //从canvas层面去除绘制时的锯齿
        canvas.setDrawFilter(mDrawFilter);
        resetPositionY();
        for(int i=0; i<mTotalWidth;i++) {
            //绘制第一条水波纹
            canvas.drawLine(i,mWaveHightRatio * mTotalHeight - mResetOneYPositions[i],i,0,mWavePaint);
            //绘制第二条水波纹
            canvas.drawLine(i,mWaveHightRatio * mTotalHeight - mResetTwoYPositions[i],i,0,mWavePaint);
        }
        //改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;
        //如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth)
             mXOneOffset = 0;
        if (mXTwoOffset > mTotalWidth)
            mXTwoOffset = 0;
        //引发view重绘
        postInvalidate();
    }

    private void resetPositionY() {
        //mXOneOffset 代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;

        System.arraycopy(mYPositions,mXOneOffset,mResetOneYPositions,0,yOneInterval);
        System.arraycopy(mYPositions,0,mResetOneYPositions,yOneInterval,mXOneOffset);

        //重新填充第二条波纹的数据
        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions,mXTwoOffset,mResetTwoYPositions,0,yTwoInterval);
        System.arraycopy(mYPositions,0,mResetTwoYPositions,yTwoInterval,mXTwoOffset);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        //记录下view的宽高
        mTotalWidth = w;
        mTotalHeight = h;
        //用于保存原始波纹的y值
        mYPositions = new float[mTotalWidth];
        //用于保存波纹一的y值
        mResetOneYPositions = new float[mTotalWidth];
        //用于保存波纹二的y值
        mResetTwoYPositions = new float[mTotalWidth];
        //将周期定为view总宽度
        mCycleFactorW = (float)(2 * Math.PI / mTotalWidth);
        //根据view 总宽度得出所有对应的y值
        for (int i=0;i<mTotalWidth;i++) {
            mYPositions[i] = (float)(STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }
}
