package com.lichi.goodrongyi.ui.activity.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.WebPresenter;
import com.lichi.goodrongyi.mvp.view.WebHtmlView;
import com.lichi.goodrongyi.ui.activity.course.PurchaseCourseActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.AppManager;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * H5支付界面
 */
public class VideoPayWebActivity extends BaseActivity<WebHtmlView, WebPresenter> implements View.OnClickListener {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_shar)
    ImageView ivShar;
    @BindView(R.id.webview)
    WebView webview;
    private PopupWindow mPopWindow;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    public String urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO,根据ID查询对的H5页面
        urls = getIntent().getStringExtra(Constants.IntentParams.ID);
        setContentView(R.layout.activity_pay_html);
        ButterKnife.bind(this);
        init();
        initWebView();
        webview.loadUrl(urls);
    }

    @Override
    public WebPresenter initPresenter() {
        return new WebPresenter();
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
        ws.setDomStorageEnabled(true);//开启DOM storage API功能

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //ws.setTextZoom(100);
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.setWebViewClient(new MyWebViewClient());

    }

    int page = 0;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            page++;
            if (page % 3 == 0) {
                try {
                    //String urlQQ = "mqqapi://forward/url?url_prefix=aHR0cHM6Ly9teXVuLnRlbnBheS5jb20vbXFxL3BheS9xcmNvZGUuaHRtbD9fd3Y9MTAyNyZfYmlkPTIxODMmdD02YWYwYzliOQ==&version=1&src_type=web";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    finish();
                    /**
                     * 打开QQ 聊天
                     */
                    //     String urlview = "mqqwpa://im/chat?chat_type=wpa&uin=";
                    //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlview)));

                    //                第一种方式：是可以的跳转到qq主页面，不能跳转到qq聊天界面
                    //  Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                    //  intent.putExtra("url",url);
                    // startActivity(intent);

                } catch (Exception e) {
                    //如果没有装ＱＱ，检测异常，不然会出问题滴！
                    e.printStackTrace();
                    CommonUtils.showToast(VideoPayWebActivity.this, "请检查是否安装QQ");
                    finish();
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        /**
         * 当网页里a标签target="_blank"，打开新窗口时，这里会调用
         */
        @Override
        public boolean onCreateWindow(WebView webView, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView newWebView = new WebView(VideoPayWebActivity.this);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            newWebView.setWebChromeClient(new MyWebChromeClient());
            newWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webview.loadUrl(url);
                    //防止触发现有界面的WebChromeClient的相关回调
                    return true;
                }
            });
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();
            return true;
//        return super.onCreateWindow(webView, isDialog, isUserGesture, message);
        }
    }

    public void init() {

    }

    @OnClick({R.id.tv_back, R.id.iv_shar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                VideoPayWebActivity.this.finish();
                break;
            case R.id.iv_shar:
                ShareBoardConfig config = new ShareBoardConfig();
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                config.setTitleText("分享");
                mShareAction.open(config);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
