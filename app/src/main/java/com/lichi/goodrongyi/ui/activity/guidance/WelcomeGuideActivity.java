package com.lichi.goodrongyi.ui.activity.guidance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.ViewPagerAdapter;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎页
 *
 * @author wwj_748
 */
public class WelcomeGuideActivity extends BaseActivity<IMainView, MainPresenter> {

    private ViewPager vp;
    private ViewPagerAdapter adapter;
    private List<View> views;
    private ImageView startBtn;

    // 引导页图片资源
    private static final int[] pics = {R.drawable.guid1, R.drawable.guid2, R.drawable.guid3};

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        startBtn = (ImageView) findViewById(R.id.btn_enter);
        vp = (ViewPager) findViewById(R.id.vp_guide);
        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            views.add(getViewPagerView(pics[i]));
        }

        // 初始化adapter
        adapter = new ViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new PageChangeListener());
        startBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMainActivity();
            }
        });
    }


    public View getViewPagerView(int url) {
        ImageView mImageView;
        View view = View.inflate(this, R.layout.guid_view, null);
        mImageView = (ImageView) view.findViewById(R.id.image);
         //mImageView.setImageResource(url);

/*
        InputStream is = this.getResources().openRawResource(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10;   //width，hight设为原来的十分一
        Bitmap btp = BitmapFactory.decodeStream(is, null, options);*/

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = mContext.getResources().openRawResource(url);
        Bitmap btp = BitmapFactory.decodeStream(is, null, opt);
        mImageView.setImageBitmap(btp);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
        SharedPreferenceUtil.putFirst(WelcomeGuideActivity.this);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }


    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }


    private void enterMainActivity() {
        CommonUtils.startNewActivity(WelcomeGuideActivity.this, SplashActivity.class);
        SharedPreferenceUtil.putFirst(WelcomeGuideActivity.this);
        finish();
    }

    private class PageChangeListener implements OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            if (position + 1 >= pics.length) {
                startBtn.setVisibility(View.VISIBLE);
            } else {
                startBtn.setVisibility(View.GONE);
            }
        }

    }
}
