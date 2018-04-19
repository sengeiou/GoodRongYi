package com.lichi.goodrongyi.ui.activity.discover;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.CreditQueryPresenter;
import com.lichi.goodrongyi.mvp.view.CreditQueryView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class CreditQueryWebActivity extends BaseActivity<CreditQueryView, CreditQueryPresenter> {

    WebView mWebview;
    TextView title;
    public String Url = "";
    public String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_query_web);
        Url = getIntent().getStringExtra(Constants.IntentParams.ID);
        name = getIntent().getStringExtra(Constants.IntentParams.ID2);
        initView();
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        initWebView();
        initData();

        title.setText(name);
        mWebview.loadUrl(Url);
    }

    private void initView() {
        mWebview = (WebView) findViewById(R.id.webview);
        title = (TextView) findViewById(R.id.title);
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initData() {
    }

    private void initWebView() {
        WebSettings ws = mWebview.getSettings();
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
        //mWebview.setWebViewClient(new WebViewClient());
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                //handleMessage(Message msg); 其他处理
            }
        });
    }

    @Override
    public CreditQueryPresenter initPresenter() {
        return new CreditQueryPresenter();
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                CreditQueryWebActivity.this.finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
