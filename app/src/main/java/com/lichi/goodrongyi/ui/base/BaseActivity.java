package com.lichi.goodrongyi.ui.base;


import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.BasePresenter;
import com.lichi.goodrongyi.utill.AppManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.subjects.PublishSubject;


/**
 * 基础代码封装
 *
 * @param <V>
 * @param <T>
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    protected T mPresenter;
    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    protected Context mContext;
    protected Resources mRes;
    protected int Basecolors = R.color.style_bg7;

    /**
     * 在finish方法中覆盖原有动画样式，若之前不是通过activity启动的 则不覆盖，使动画样式一致
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        setStatusBar(Basecolors);
       /* //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();*/

        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
        super.onCreate(savedInstanceState);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        mPresenter = initPresenter();
        AppManager.getAppManager().addActivity(this);
        mPresenter.attach((V) this);
        mPresenter.context = this;
        mRes = mContext.getResources();
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_DUM_NORMAL);
    }


    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START);

    }

    @Override
    protected void onResume() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
        super.onResume();
        MobclickAgent.onResume(this);
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        MobclickAgent.onResume(mContext);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        // mPresenter.dettach();
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setStatusBar(int colors) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colors);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
            //overridePendingTransition(0, R.anim.slide_right_out);
        }
    }


    public Uri geturi(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    public abstract T initPresenter();

    protected SweetAlertDialog mLoadingDialog;

    protected void showLoadingIndicator(boolean active) {
        if (active) {
            showLoadingDialog();
        } else {
            dismissLoadingDialog();
        }
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper()
                    .setBarColor(getResources().getColor(R.color.colorPrimary));
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setTitleText("数据加载中...");
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 关闭输入框
     */
    protected void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 改变字体颜色
     *
     * @param textView
     * @param src
     * @param sta
     * @param end
     * @param color
     */
    public void setTextColors(TextView textView, String src, int sta, int end, int color) {
        SpannableString spannableString = new SpannableString(src);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(mRes.getColor(color));
        spannableString.setSpan(colorSpan, sta, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    public boolean isImmersionBar = true;

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return isImmersionBar;
    }
}
