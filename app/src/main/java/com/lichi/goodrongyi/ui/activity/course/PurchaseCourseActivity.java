package com.lichi.goodrongyi.ui.activity.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.mvp.model.CoursePayBean;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.VerificationCodeBean;
import com.lichi.goodrongyi.mvp.presenter.PurchaseCoursePresenter;
import com.lichi.goodrongyi.mvp.view.PurchaseCourseView;
import com.lichi.goodrongyi.ui.activity.video.VideoActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.CountDownTimerUtils;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.JudgeInstall;
import com.lichi.goodrongyi.utill.PayResult;
import com.lichi.goodrongyi.utill.PhoneUtil;
import com.lichi.goodrongyi.utill.ThreadPoolProxy;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class PurchaseCourseActivity extends BaseActivity<PurchaseCourseView, PurchaseCoursePresenter> implements View.OnClickListener, PurchaseCourseView {

    private ImageView mIVWeinxinCheck;
    private ImageView mIVAlipayCheck;

    private TextView mTVOriginalPrice;
    private TextView mTVCourseTitle;
    private TextView mTVChangePhone;
    private Button mBtnCustomer; //购买

    private TextView mETUserName;
    private EditText mETNote;
    private EditText mEtVerifyCode;

    private Button mBTNSignUp;
    private LinearLayout mApplylayout; //验证码布局
    private TextView mTvUserType;
    private TextView mTvPrice;
    private TextView mPhoneNumber; //本机号码
    private EditText mTvPhoneNumber; //手机号
    private TextView mTvChangePhone; //切换
    private TextView mBtnSendVerifyCode; //验证码
    public String ID;
    public String money;
    public String title;
    public boolean isSwitchover = true;

    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_course);
        ID = getIntent().getStringExtra(Constants.IntentParams.ID);
        money = getIntent().getStringExtra(Constants.IntentParams.ID2);
        title = getIntent().getStringExtra(Constants.IntentParams.ID3);
        initData();
        initView();
/*        if (TextUtils.isEmpty(IOUtils.getToken(mContext))) {
            mApplylayout.setVisibility(View.VISIBLE);
            mTvChangePhone.setVisibility(View.GONE);
            mTvPhoneNumber.setVisibility(View.VISIBLE);
            mPhoneNumber.setVisibility(View.GONE);
        } else {
            mApplylayout.setVisibility(View.GONE);
            mTvChangePhone.setVisibility(View.VISIBLE);
            UserBean userBean = IOUtils.getUserBean(mContext);
            if (userBean != null) {
                mETUserName.setText(userBean.nickname);
                if (!TextUtils.isEmpty(userBean.mobile)) {
                    if (!TextUtils.isEmpty(userBean.mobile)) {
                        mPhoneNumber.setText(userBean.mobile);
                    } else {
                        mTvPhoneNumber.setText("");
                        mApplylayout.setVisibility(View.VISIBLE);
                        mTvChangePhone.setVisibility(View.GONE);
                    }
                } else {
                    mApplylayout.setVisibility(View.VISIBLE);
                    mTvChangePhone.setVisibility(View.GONE);
                    mTvPhoneNumber.setVisibility(View.VISIBLE);
                    mPhoneNumber.setVisibility(View.GONE);
                }
            }
        }*/
        userBean = IOUtils.getUserBean(mContext);
        mETUserName.setText(userBean.nickname);
        mPhoneNumber.setText(userBean.mobile);
        mTvPrice.setText("￥ " + money);
        mTVCourseTitle.setText("" + title);
    }

    public void initView() {
        mBtnCustomer = (Button) findViewById(R.id.btn_customer);
        mBtnSendVerifyCode = (TextView) findViewById(R.id.btn_send_verify_code);
        mTvChangePhone = (TextView) findViewById(R.id.tv_change_phone);
        mEtVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvPhoneNumber = (EditText) findViewById(R.id.tv_phone_number);
        mApplylayout = (LinearLayout) findViewById(R.id.applylayout);
        mTvUserType = (TextView) findViewById(R.id.tv_user_type);
        mTVCourseTitle = (TextView) findViewById(R.id.tv_course_title);
        mETUserName = (TextView) findViewById(R.id.et_user_name);
        mETNote = (EditText) findViewById(R.id.et_note);

        mTVOriginalPrice = (TextView) findViewById(R.id.tv_original_price);
        mTVOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  // 给原价添加中划线

        mIVWeinxinCheck = (ImageView) findViewById(R.id.iv_weixin_checked);
        mIVAlipayCheck = (ImageView) findViewById(R.id.iv_alipay_checked);

        mIVWeinxinCheck.setOnClickListener(this);
        mIVAlipayCheck.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        mTvChangePhone.setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        mBtnSendVerifyCode.setOnClickListener(this);
        mBtnCustomer.setOnClickListener(this);
    }

    @Override
    public PurchaseCoursePresenter initPresenter() {
        return new PurchaseCoursePresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                PurchaseCourseActivity.this.finish();
                break;
            case R.id.btn_customer:
                // PurchaseCourseActivity.this.finish();
                mPresenter.getCustomer("4");
                break;
            case R.id.iv_weixin_checked:
                mIVWeinxinCheck.setImageResource(R.mipmap.checked_true);
                mIVAlipayCheck.setImageResource(R.mipmap.checked_false);
                break;
            case R.id.iv_alipay_checked:
                mIVWeinxinCheck.setImageResource(R.mipmap.checked_false);
                mIVAlipayCheck.setImageResource(R.mipmap.checked_true);
                break;
            case R.id.btn_send_verify_code:
                if (TextUtils.isEmpty(mTvPhoneNumber.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入手机号");
                    return;
                }
                if (!PhoneUtil.isMobileNO(mTvPhoneNumber.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入正确的手机号");
                    return;
                }
                closeKeyboard();
                mPresenter.verificationCode(mTvPhoneNumber.getText().toString().trim());
                break;
            case R.id.btn_signup:
/*                ShareBoardConfig config = new ShareBoardConfig();
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                config.setTitleText("分享");
                mShareAction.open(config);*/
                // mPresenter.getPayCourse(ID);
                mPresenter.getPayZfbCourse(ID);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    private void initData() {
        mShareListener = new CustomShareListener(PurchaseCourseActivity.this);
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(PurchaseCourseActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
                            Toast.makeText(PurchaseCourseActivity.this, "复制文本按钮", Toast.LENGTH_LONG).show();
                        } else if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")) {
                            Toast.makeText(PurchaseCourseActivity.this, "复制链接按钮", Toast.LENGTH_LONG).show();

                        } else if (share_media == SHARE_MEDIA.SMS) {
                            new ShareAction(PurchaseCourseActivity.this).withText("来自分享面板标题")
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        } else {
                            UMImage imageurl = new UMImage(PurchaseCourseActivity.this, R.drawable.receipt);
                            new ShareAction(PurchaseCourseActivity.this).withMedia(imageurl)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener).share();
                        }
                    }
                });

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
    public void dataListSucceed(CoursePayBean courseBeans) {

    }

    @Override
    public void dataCodeSucceed(VerificationCodeBean verificationCodeBean) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mBtnSendVerifyCode, 60000, 1000, R.color.line, R.color.colorPrimary);
        mCountDownTimerUtils.start();
    }

    @Override
    public void CustomerSucceed(CustomerBean bean) {
        if (!TextUtils.isEmpty(bean.qq)) {
            if (JudgeInstall.isQQClientAvailable(mContext)) {
                String urlview = "mqqwpa://im/chat?chat_type=wpa&uin=" + bean.qq;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlview)));
            } else {
                CommonUtils.showToast(mContext, "请先安装QQ或IM");
            }
        } else {
            CommonUtils.showToast(mContext, "暂无客服，请稍等");
        }
    }

    @Override
    public void PayCourseSucceed(String url) {
        Map<String, Serializable> args = new HashMap<>();
        args.put(Constants.IntentParams.ID, url);
        CommonUtils.startNewActivity(mContext, args, PayWebActivity.class);
        // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void PayZfbCourseSucceed(String data) {
        execAlipay(data);
    }

    /**
     * 支付宝支付
     */
    private void execAlipay(final String orderInfo) {


        ThreadPoolProxy.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {

                PayTask alipay = new PayTask(PurchaseCourseActivity.this);
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
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<PurchaseCourseActivity> mActivity;

        private CustomShareListener(PurchaseCourseActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            //Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
