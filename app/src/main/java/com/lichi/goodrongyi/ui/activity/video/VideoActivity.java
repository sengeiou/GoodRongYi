package com.lichi.goodrongyi.ui.activity.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.presenter.VideoPresenter;
import com.lichi.goodrongyi.mvp.view.VideoView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.PayResult;
import com.lichi.goodrongyi.utill.ThreadPoolProxy;
import com.lichi.goodrongyi.utill.WebviewUtil;
import com.lichi.goodrongyi.utill.webviewutil.FullscreenHolder;
import com.lichi.goodrongyi.utill.webviewutil.IWebPageView;
import com.lichi.goodrongyi.utill.webviewutil.MyWebChromeClient;
import com.lichi.goodrongyi.utill.webviewutil.MyWebViewClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by test on 2017/12/13.
 * 网页播放视频
 */

public class VideoActivity extends BaseActivity<VideoView, VideoPresenter> implements IWebPageView, VideoView, View.OnClickListener {

    public WebView webview;
    public RelativeLayout nopay; //没有支付的界面
    public ImageView nopayimage; //没有图片

    public Button btnPurchase;
    public TextView introduce;
    public TextView money;
    private TextView Originalprice;    // 课程价格原价
    private FrameLayout mFullscreenContainer;   // 全屏时视频加载view
    private View mCustomView = null;
    // 进度条是否加载到90%
    public boolean mProgress90;
    // 网页是否加载完成
    public boolean mPageFinish;

    // 加载视频相关
    private MyWebChromeClient mWebChromeClient;

    private String ID = "";
    VideoDetailsBean mCourseBeans;

    @Override
    public VideoPresenter initPresenter() {
        return new VideoPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ID = getIntent().getStringExtra(Constants.IntentParams.ID);
        webview = (WebView) findViewById(R.id.webview);
        introduce = (TextView) findViewById(R.id.introduce);
        money = (TextView) findViewById(R.id.money);
        btnPurchase = (Button) findViewById(R.id.btn_purchase);
        mFullscreenContainer = (FrameLayout) findViewById(R.id.video_fullView);
        Originalprice = (TextView) findViewById(R.id.originalprice);
        nopay = (RelativeLayout) findViewById(R.id.nopay);
        nopayimage = (ImageView) findViewById(R.id.nopayimage);
        nopay.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);

        if (getPhoneAndroidSDK() >= 14) {// 4.0 需打开硬件加速
            getWindow().setFlags(0x1000000, 0x1000000);
        }
        initWebView();
        //  webview.loadUrl("http://v.youku.com/v_show/id_XMzIyOTUxNTQzMg==.html?spm=a2hww.20027244.m_250036.5~5!2~5~5!3~5~5~A&f=51390299#paction");
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.getDataCourse(ID);
    }

    public static int getPhoneAndroidSDK() {
        // TODO Auto-generated method stub
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;

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

        mWebChromeClient = new MyWebChromeClient(VideoActivity.this);
        webview.setWebChromeClient(mWebChromeClient);
        // 与js交互
        // webView.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");
        webview.setWebViewClient(new MyWebViewClient(this));

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
        mFullscreenContainer.removeAllViews();
        if (webview != null) {
            ViewGroup parent = (ViewGroup) webview.getParent();
            if (parent != null) {
                parent.removeView(webview);
            }
            webview.removeAllViews();
            webview.loadUrl("about:blank");
            webview.stopLoading();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroy();
            webview = null;
        }
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
    public void dataVideoSucceed(VideoDetailsBean courseBeans) {
        mCourseBeans = courseBeans;

        introduce.setText(courseBeans.description);
        money.setText("￥" + courseBeans.disCount);
        Originalprice.setText("￥" + courseBeans.price);
        Originalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        double disCount = Double.parseDouble(courseBeans.disCount);
        if (disCount == 0) {
            nopay.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
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

            btnPurchase.setBackground(new ColorDrawable(getResources().getColor(R.color.gray_frame)));
            btnPurchase.setTextColor(getResources().getColor(R.color.cae));
            btnPurchase.setClickable(false);
            btnPurchase.setText("免费课程");

        } else {
            if (courseBeans.status == 1) {
                btnPurchase.setBackground(new ColorDrawable(getResources().getColor(R.color.gray_frame)));
                btnPurchase.setTextColor(getResources().getColor(R.color.cae));
                btnPurchase.setClickable(false);
                btnPurchase.setText("已购买");

                nopay.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
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
            } else {
                nopay.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);
                MyPicasso.inject(courseBeans.videoUrl, nopayimage);
            }
        }


    }

    @Override
    public void PayVodeoSucceed(String url) {
        Map<String, Serializable> args = new HashMap<>();
        args.put(Constants.IntentParams.ID, url);
        CommonUtils.startNewActivity(mContext, args, VideoPayWebActivity.class);
    }

    @Override
    public void PayZfbVodeoSucceed(String data) {
        execAlipay(data);
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nopay:
                if (IOUtils.isLogin(mContext)) {
                    CommonUtils.showToast(mContext, "请先购买该课程");
                }
                break;
            case R.id.btn_purchase:

                if (IOUtils.isLogin(mContext)) {
                    if (mCourseBeans != null) {
                        // mPresenter.getPayCourse(ID);
                        mPresenter.getZfbPayVideo(ID);
                    } else {
                        CommonUtils.showToast(mContext, "正在获取数据");
                    }
                }
                break;
        }
    }


    /**
     * 支付宝支付
     */
    private void execAlipay(final String orderInfo) {


        ThreadPoolProxy.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {

                PayTask alipay = new PayTask(VideoActivity.this);
                Map map = alipay.payV2(orderInfo, true);

                Message msg = mHandler.obtainMessage();
                msg.obj = map;
                msg.what = SDK_PAY_FLAG;
                mHandler.sendMessage(msg);

                Logger.d("支付宝:" + map);
            }
        });

    }

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(VideoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        mPresenter.getDataCourse(ID);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(VideoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

}
