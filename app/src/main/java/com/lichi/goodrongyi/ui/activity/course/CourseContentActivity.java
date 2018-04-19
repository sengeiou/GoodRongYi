package com.lichi.goodrongyi.ui.activity.course;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.CourseDetailsBean;
import com.lichi.goodrongyi.mvp.presenter.CourseContentPresenter;
import com.lichi.goodrongyi.mvp.view.CourseContentView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.WebviewUtil;
import com.lichi.goodrongyi.utill.webviewutil.FullscreenHolder;
import com.lichi.goodrongyi.utill.webviewutil.IWebPageView;
import com.lichi.goodrongyi.utill.webviewutil.MyCourseWebChromeClient;
import com.lichi.goodrongyi.utill.webviewutil.MyCourseWebViewClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 课程详情
 */
public class CourseContentActivity extends BaseActivity<CourseContentView, CourseContentPresenter> implements View.OnClickListener, CourseContentView, IWebPageView {
    //回调
    private static final int TYPE = 10001;
    public WebView webview;
    private Button mBTNPurchase;
    private Button mBTNSignUp;

    private ImageView mHead; //头像

    private TextView mTVPurchasedLabel; // 蓝色已注册标签
    private TextView mTVSignedUpLabel;  // 红色已购买标签

    private TextView mTVCourseTitle;    // 课程名称
    private TextView mTVCourseTeacher;  // 课程教师姓名
    private TextView mTVTeacherType;    // 教师类别（主讲等）
    private TextView mTVTeacherDescription; // 教师介绍

    private TextView mTVCoursePrice;    // 课程价格（带单位）
    private TextView Originalprice;    // 课程价格原价
    private TextView mTVCourseOpenDate; // 开课日期（精确到时）
    private TextView mTVCourseStudentAmount;  // 课程人数（带单位：人）
    private TextView mTVCourseAddress;  // 开课地址
    private TextView mTVCourseDescription;  // 开课说明

    private int ID;
    // 进度条是否加载到90%
    public boolean mProgress90;
    // 网页是否加载完成
    public boolean mPageFinish;

    // 加载视频相关
    private MyCourseWebChromeClient mWebChromeClient;
    private FrameLayout mFullscreenContainer;   // 全屏时视频加载view

    CourseDetailsBean mCourseDetailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);

        ID = getIntent().getIntExtra(Constants.IntentParams.ID, -1);
        //  type = getIntent().getIntExtra(Constants.IntentParams.ID2, -1);
        initView();
        initData();
        initWebView();
    }


    public void initView() {
        Originalprice = (TextView) findViewById(R.id.originalprice);
        mBTNPurchase = (Button) findViewById(R.id.btn_purchase);
        mBTNPurchase.setOnClickListener(this);

        mBTNSignUp = (Button) findViewById(R.id.btn_signup);
        mBTNSignUp.setOnClickListener(this);

        mHead = (ImageView) findViewById(R.id.head);  // 头像

        mTVPurchasedLabel = (TextView) findViewById(R.id.tv_course_state_purchased); // 红色已购买标签
        mTVSignedUpLabel = (TextView) findViewById(R.id.tv_course_state_signed_up);  // 蓝色已报名标签

        mTVCourseTitle = (TextView) findViewById(R.id.tv_course_title);    // 课程名称
        mTVCourseTeacher = (TextView) findViewById(R.id.tv_teacher_name);  // 课程教师姓名
        mTVTeacherType = (TextView) findViewById(R.id.tv_teacher_type);    // 教师类别（主讲等）
        mTVTeacherDescription = (TextView) findViewById(R.id.tv_teacher_description); // 教师介绍

        mTVCoursePrice = (TextView) findViewById(R.id.tv_course_price);    // 课程价格（带单位）
        mTVCourseOpenDate = (TextView) findViewById(R.id.tv_course_open_date); // 开课日期（精确到时）
        mTVCourseStudentAmount = (TextView) findViewById(R.id.tv_course_student_amount);  // 课程人数（带单位：人）
        mTVCourseAddress = (TextView) findViewById(R.id.tv_course_address);  // 开课地址
        mTVCourseDescription = (TextView) findViewById(R.id.tv_course_description);  // 开课说明

        webview = (WebView) findViewById(R.id.webview); //视频播放
        mFullscreenContainer = (FrameLayout) findViewById(R.id.video_fullView);
    }

    private void initWebView() {
        WebSettings ws = webview.getSettings();
        //这句话去掉也没事。。只是设置了编码格式
        ws.setDefaultTextEncodingName("utf-8");
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
//        webView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //ws.setTextZoom(100);

        mWebChromeClient = new MyCourseWebChromeClient(CourseContentActivity.this);
        webview.setWebChromeClient(mWebChromeClient);
        // 与js交互
        // webView.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");
        webview.setWebViewClient(new MyCourseWebViewClient(this));
    }

    @Override
    public CourseContentPresenter initPresenter() {
        return new CourseContentPresenter();
    }

    @Override
    public void onClick(View v) {
        Map<String, Serializable> args = null;
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                CourseContentActivity.this.finish();
                break;
            case R.id.btn_signup:
                if (mCourseDetailsBean != null) {
                    args = new HashMap<>();
                    args.put(Constants.IntentParams.ID, mCourseDetailsBean.id + "");
                    args.put(Constants.IntentParams.ID2, mCourseDetailsBean.disCount + "");
                    args.put(Constants.IntentParams.ID3, mCourseDetailsBean.title + "");
                    CommonUtils.startResultNewActivity(CourseContentActivity.this, args, SignUpCourseActivity.class, TYPE);
                } else {
                    CommonUtils.showToast(mContext, "正在请求数据");
                }

                break;
            case R.id.btn_purchase:
                if (mCourseDetailsBean != null) {
                    if (mCourseDetailsBean.status == 0) {
                        args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, mCourseDetailsBean.id + "");
                        args.put(Constants.IntentParams.ID2, mCourseDetailsBean.disCount + "");
                        args.put(Constants.IntentParams.ID3, mCourseDetailsBean.title + "");
                        CommonUtils.startNewActivity(mContext, args, PurchaseCourseActivity.class);
                    } else if (mCourseDetailsBean.status == 1) {
                        CommonUtils.showToast(mContext, "您已经已购买该课程");
                    } else {
                        CommonUtils.showToast(mContext, "请先报名");
                    }
                } else {
                    CommonUtils.showToast(mContext, "正在请求数据");
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TYPE && resultCode == Activity.RESULT_OK) {
            // type = 0;
        }
    }

    private void initData() {
    }

    @Override
    public void dataListSucceed(CourseDetailsBean courseBeans) {
        mCourseDetailsBean = courseBeans;
        mTVCourseTitle.setText(courseBeans.title);
        mTVCourseTeacher.setText(courseBeans.teachername);
        mTVTeacherDescription.setText(courseBeans.introduce);
        Originalprice.setText(courseBeans.money + "元");
        Originalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        mTVCoursePrice.setText(courseBeans.disCount + "元");
        mTVCourseOpenDate.setText(courseBeans.startTime);
        mTVCourseStudentAmount.setText(courseBeans.person + "人");
        mTVCourseAddress.setText(courseBeans.address);
        mTVCourseDescription.setText(courseBeans.description);
        MyPicasso.inject(courseBeans.imgUrl, mHead);
        try {
            String[] split = courseBeans.url.split(" ");
            for (String string : split) {
                Pattern pattern = Pattern.compile("^src=");
                Matcher matcher = pattern.matcher(string);
                if (matcher.find()) {
                    webview.loadData(WebviewUtil.getHtmlData("<iframe height=200 width=100% " + string +
                                    "frameborder=0 allowfullscreen></iframe>"), "text/html; charset=utf-8",
                            "utf-8");
                }
            }
        } catch (Exception e) {

        }

        int type = courseBeans.status;
        if (type == 0) {
            mBTNSignUp.setBackground(new ColorDrawable(getResources().getColor(R.color.line)));
            mBTNSignUp.setTextColor(getResources().getColor(R.color.cae));
            mBTNSignUp.setClickable(false);
            mBTNSignUp.setText("已报名");
            mTVSignedUpLabel.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            mBTNPurchase.setBackground(new ColorDrawable(getResources().getColor(R.color.gray_frame)));
            mBTNPurchase.setTextColor(getResources().getColor(R.color.cae));
            mBTNPurchase.setClickable(false);
            mBTNPurchase.setText("已购买");
            mBTNSignUp.setBackground(new ColorDrawable(getResources().getColor(R.color.line)));
            mBTNSignUp.setTextColor(getResources().getColor(R.color.cae));
            mBTNSignUp.setClickable(false);
            mBTNSignUp.setText("已报名");
            mTVSignedUpLabel.setVisibility(View.INVISIBLE);
            mTVPurchasedLabel.setVisibility(View.VISIBLE);
        } else {
            mTVSignedUpLabel.setVisibility(View.INVISIBLE);
            mTVPurchasedLabel.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);
    }

    @Override
    public void showLoadingTasksError() {
        CommonUtils.showToast(mContext, R.string.app_abnormal);
    }

    @Override
    public Context getContext() {
        return mContext;
    }


    @Override
    public void hindProgressBar() {

    }

    @Override
    public void showWebView() {
        webview.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView() {
        webview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress() {

    }

    @Override
    public void progressChanged(int newProgress) {

    }

    @Override
    public void addImageClickListener() {

    }

    @Override
    public void fullViewAddView(View view) {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        mFullscreenContainer = new FullscreenHolder(mContext);
        mFullscreenContainer.addView(view);
        decor.addView(mFullscreenContainer);
    }


    @Override
    public void showVideoFullView() {
        mFullscreenContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindVideoFullView() {
        mFullscreenContainer.setVisibility(View.GONE);
    }

    @Override
    public FrameLayout getVideoFullView() {
        return mFullscreenContainer;
    }

    @Override
    public void onPause() {// 继承自Activity
        super.onPause();
        webview.onPause();
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //全屏播放退出全屏
            if (mWebChromeClient.inCustomView()) {
                hideCustomView();
                return true;

                //返回网页上一页
            } else if (webview.canGoBack()) {
                webview.goBack();
                return true;

                //退出网页
            } else {
                webview.loadUrl("about:blank");
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        webview.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mPresenter.getDataCourse(ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFullscreenContainer.removeAllViews();
        mPresenter.dettach();
        if (webview != null) {
            ViewGroup parent = (ViewGroup) webview.getParent();
            if (parent != null) {
                parent.removeView(webview);
            }
            webview.removeAllViews();
            // webview.loadUrl("about:blank");
            webview.stopLoading();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroy();
            webview = null;
        }
    }
}
