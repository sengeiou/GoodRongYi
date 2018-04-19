package com.lichi.goodrongyi.ui.activity.guidance;

import android.os.Bundle;
import android.os.Handler;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.ui.activity.main.MainActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;

/**
 * @desc 启动屏
 * Created by devilwwj on 16/1/23.
 */
public class SplashActivity extends BaseActivity<IMainView, MainPresenter> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SharedPreferenceUtil.getFirst(this);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            CommonUtils.startNewActivity(this, WelcomeGuideActivity.class);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    private void enterHomeActivity() {
        CommonUtils.startNewActivity(this, MainActivity.class);
        finish();
    }
}
