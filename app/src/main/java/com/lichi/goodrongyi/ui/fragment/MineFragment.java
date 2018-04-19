package com.lichi.goodrongyi.ui.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.component.SimpleComponent;
import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.MyCourseBean;
import com.lichi.goodrongyi.mvp.model.RankingBean;
import com.lichi.goodrongyi.mvp.model.ShuaBean;
import com.lichi.goodrongyi.mvp.model.UpLoadingFileBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.presenter.MinePresenter;
import com.lichi.goodrongyi.mvp.view.MineView;
import com.lichi.goodrongyi.ui.activity.AllHtmlActivity;
import com.lichi.goodrongyi.ui.activity.main.MainActivity;
import com.lichi.goodrongyi.ui.activity.message.MessageActivity;
import com.lichi.goodrongyi.ui.activity.my.AccountSecurityActivity;
import com.lichi.goodrongyi.ui.activity.my.AuthorizeBrandActivity;
import com.lichi.goodrongyi.ui.activity.my.AutonymAttestationActivity;
import com.lichi.goodrongyi.ui.activity.my.CommissionActivity;
import com.lichi.goodrongyi.ui.activity.my.MyClassActivity;
import com.lichi.goodrongyi.ui.activity.my.QrcodeActivity;
import com.lichi.goodrongyi.ui.activity.my.RankActivity;
import com.lichi.goodrongyi.ui.activity.my.SlotCardTaskActivity;
import com.lichi.goodrongyi.ui.activity.video.VideoAllActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.BitmapUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.JudgeInstall;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.lichi.goodrongyi.view.YuEView;
import com.lichi.goodrongyi.view.waveview.WaveHelper;
import com.lichi.goodrongyi.view.waveview.WaveView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

//import com.lichi.goodrongyi.ui.activity.RegisterActivity;


/**
 * 我的
 */
public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {
    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    @BindView(R.id.wave)
    WaveView waveView;
    @BindView(R.id.head_pic)
    ImageView headImg;
    @BindView(R.id.tv_my_name)
    TextView mNmae;
    @BindView(R.id.tv_my_class)
    TextView mClass;
    @BindView(R.id.my_item_common_text)
    TextView mCommonText;
    @BindView(R.id.tv_my_money)
    YuEView mMoney;
    @BindView(R.id.tv_my_rank)
    TextView mRank;
    @BindView(R.id.tv_my_points)
    TextView mPoints;
    @BindView(R.id.tv_shuaka_num)
    TextView mShuaKaNum;
    @BindView(R.id.tv_sign)
    TextView mSign;
    @BindView(R.id.tv_pay)
    TextView mPay;
    @BindView(R.id.grade)
    ImageView grade;
    @BindView(R.id.my_commission)
    FrameLayout myCommission;
    @BindView(R.id.realnamelayout)
    FrameLayout realnameLayout;
    @BindView(R.id.authorizebrandlayout)
    FrameLayout authorizebrandlayout;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    private WaveHelper mWaveHelper;

    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    private UserBean userBeans;
    RankingBean rankingBean;
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/rkx/Camera/");
    Bitmap qrBitmap = null;
    MediaPlayer mp;

    @OnClick(R.id.head_pic)
    public void goheadPic() {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }

    @OnClick(R.id.namelayout)
    public void goNamelayout() {
        if (userBeans != null) {
            setName(userBeans.nickname);
        } else {
            CommonUtils.showToast(mContext, "正在请求数据....");
        }
    }

    @OnClick(R.id.rank_of_invitation)
    public void goToRank() {
        if (rankingBean != null) {
            Map<String, Serializable> args = new HashMap<>();
            args.put(Constants.IntentParams.ID, rankingBean.rownum);
            CommonUtils.startNewActivity(mContext, args, RankActivity.class);
        } else {
            CommonUtils.showToast(mContext, "正在请求数据....");
        }
    }

    @OnClick(R.id.slot_card_task)
    public void goToSlotCardTask() {
        CommonUtils.startNewActivity(getActivity(), SlotCardTaskActivity.class);
    }

    @OnClick(R.id.realnamelayout)
    public void goToRealName() { //实名认证
        if (userBeans != null) {
            if (TextUtils.isEmpty(userBeans.realName) && TextUtils.isEmpty(userBeans.idCard)) {
                CommonUtils.startNewActivity(getActivity(), AutonymAttestationActivity.class);
            } else {
                Map<String, Serializable> args = new HashMap<>();
                args.put(Constants.IntentParams.ID, userBeans.realName);
                args.put(Constants.IntentParams.ID2, userBeans.idCard);
                args.put(Constants.IntentParams.ID3, 2);
                CommonUtils.startNewActivity(getActivity(), args, AutonymAttestationActivity.class);
            }
        } else {
            CommonUtils.showToast(mContext, "正在请求数据....");
        }

    }

    @OnClick(R.id.authorizebrandlayout)
    public void goToAuthorizeBrand() {
        if (userBeans != null) {
            if (userBeans.realName != null && !userBeans.realName.equals("")) {

                if (userBeans.vipLevel >= 4) {
                    Map<String, Serializable> args = new HashMap<>();
                    args.put(Constants.IntentParams.ID, userBeans.vipLevel);
                    args.put(Constants.IntentParams.ID2, userBeans.realName);
                    CommonUtils.startNewActivity(mContext, args, AuthorizeBrandActivity.class);
                } else {
                    SweetAlertDialog sd = new SweetAlertDialog(mContext);
                    sd.setTitleText("提示");
                    sd.setContentText("请邀请用户升级成为白金等级才能拥有授权牌");
                    sd.setCancelable(true);
                    sd.setConfirmText("确定");
                    sd.setCanceledOnTouchOutside(true);
                    sd.show();
                }
            } else {
                SweetAlertDialog sd = new SweetAlertDialog(mContext);
                sd.setTitleText("提示");
                sd.setContentText("请先实名认证才能拥有授权牌");
                sd.setCancelable(true);
                sd.setConfirmText("确定");
                sd.setCanceledOnTouchOutside(true);
                sd.show();
            }
        } else {
            CommonUtils.showToast(mContext, "正在请求数据....");
        }

    }

    @OnClick(R.id.share)
    public void share() {
        if (userBeans != null) {
            if (userBeans.vipLevel >= 2) {
                ShareBoardConfig config = new ShareBoardConfig();
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                config.setTitleText("分享");
                mShareAction.open(config);
            } else {
                CommonUtils.showToast(mContext, "请购买课程成为银牌会员才能分享");
            }
        } else {
            CommonUtils.showToast(mContext, "正在获取数据，请稍等");
        }

    }

    @OnClick(R.id.my_commission)
    public void goTomycommission() {
        // CommonUtils.startNewActivity(getActivity(), AccountSecurityActivity.class);
        Map<String, Serializable> args = new HashMap<>();
        args.put(Constants.IntentParams.ID2, "佣金明细");
        args.put(Constants.IntentParams.ID3, Constants.BASE_H5URL + "/dist/statisticalData/?userId=" + IOUtils.getUserId(mContext));
        args.put(Constants.IntentParams.ID4, false);
        CommonUtils.startNewActivity(mContext, args, CommissionActivity.class);
    }

    @OnClick(R.id.credit_grade)
    public void goToCreditGrade() {
        CommonUtils.showToast(mContext, "正在开发");
    }

    @OnClick(R.id.my_class)
    public void goToMyClass() {
        CommonUtils.startNewActivity(getActivity(), MyClassActivity.class);
    }

    @OnClick(R.id.feedback)              // 跳转到意见反馈界面
    public void goToFeedback() {
        // CommonUtils.startNewActivity(getActivity(), LoginActivity.class);
        //CommonUtils.startNewActivity(getActivity(), FeedBackActivity.class);
        mPresenter.getCustomer(2);
    }

    @OnClick(R.id.account_and_security)   // 跳转到账户安全界面
    public void goToAccountSecurity() {
        CommonUtils.startNewActivity(getActivity(), AccountSecurityActivity.class);
    }

    @OnClick(R.id.my_service)      // 跳转到我的客服界面
    public void goToMyService() {
        // T.showShort(getContext(), "开发中...");
        mPresenter.getCustomer(1);
    }

    @OnClick(R.id.system_message)      // 跳转到我的消息
    public void goToSystemMessage() {
        if (IOUtils.isLogin(mContext)) {
            CommonUtils.startNewActivity(mContext, MessageActivity.class);
        }
    }

    public MineFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initData();

        waveView.setBorder(0, Color.parseColor("#44FFFFFF"));
        waveView.setWaveColor(
                Color.parseColor("#55ffffff"),
                Color.parseColor("#ffffffff"));
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        mWaveHelper = new WaveHelper(waveView, 0.5f);


        mShareListener = new CustomShareListener((MainActivity) getActivity());
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .addButton("umeng_sharebutton_code", "umeng_sharebutton_code", "qrcode", "qrcode")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_code")) {
                            // QRcode();
                            CommonUtils.startNewActivity(mContext, QrcodeActivity.class);
                            // Toast.makeText(getActivity(), "二维码文本按钮", Toast.LENGTH_LONG).show();
                        } else if (snsPlatform.mShowWord.equals("二维码")) {
                            Toast.makeText(getActivity(), "复制链接按钮", Toast.LENGTH_LONG).show();

                        } else if (share_media == SHARE_MEDIA.SMS) {
                            new ShareAction(getActivity()).withText("来自分享面板标题")
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        } else {
                            UMWeb web = new UMWeb(Constants.BASE_H5URL + "/dist/invite/index.html?pid=" + IOUtils.getUserId(mContext));
                            web.setTitle("融开心");
                            web.setDescription("信用让你更有钱 更赚钱");
                            web.setThumb(new UMImage(getActivity(), R.mipmap.ic_launcher));
                            new ShareAction(getActivity()).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    }
                });

        return view;
    }

    private void initData() {

    }

    public void QRcode() {
        View view = View.inflate(mContext, R.layout.dialog_qrcode, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.code);
        generate(imageView);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveBitmap();
                    }
                }).setNegativeButton("取消", null).show();

        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth()); //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p); //设置生效
    }

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }

    public void generate(ImageView view) {
        qrBitmap = generateBitmap(Constants.BASE_H5URL + "/dist/invite/index.html?pid=" + IOUtils.getUserId(mContext), 1000, 1000);
        // Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        view.setImageBitmap(qrBitmap);
    }


    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存
     */
    public void saveBitmap() {
        // setLoadingIndicator(true);
        PHOTO_DIR.mkdirs();// 创建照片的存储目录
        File file = createFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera"), "Qrcode", ".png");
        File file1 = BitmapUtils.saveBitmap(qrBitmap, file.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file1);
        intent.setData(uri);
        getActivity().sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
        CommonUtils.showToast(mContext, "二维码保存成功");
        //  setLoadingIndicator(false);
    }

    /**
     * 根据系统时间、前缀、后缀产生一个文件
     */
    public static File createFile(File folder, String prefix, String suffix) {
        if (!folder.exists() || !folder.isDirectory())
            folder.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename);
    }

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    public void ShuaKaSucceed(ShuaBean shuaBean) {
        mShuaKaNum.setText(shuaBean.undone + "");
    }

    @Override
    public void MyCourseSucceed(MyCourseBean myCourseBean) {
        mSign.setText(myCourseBean.signUp + "");
        mPay.setText(myCourseBean.pay + "");
    }

    @Override
    public void dataUserBeanucceed(UserBean userBean) {
        userBeans = userBean;
        //MyPicasso.inject(userBean.headImg, headImg, true);
        if (!TextUtils.isEmpty(userBean.headImg)) {
            if (!userBean.headImg.equals("http://tvax3.sinaimg.cn/default/images/default_avatar_male_180.gif")) {
/*                Picasso.with(mContext)
                        .load(userBean.headImg)
                        .placeholder(R.drawable.defaulthead)
                        .error(R.drawable.defaulthead)
                        .transform(new CircleTransForm())
                        .into(headImg);*/
                MyPicasso.inject(userBean.headImg, headImg, true);
            } else {
                headImg.setImageResource(R.drawable.defaulthead);
            }
        } else {
            headImg.setImageResource(R.drawable.defaulthead);
        }
        mNmae.setText(userBean.nickname);
        mClass.setText(userBean.levelName);
        // mMoney.setText(userBean.money);
        String commissionString = SharedPreferenceUtil.getCommission(mContext);
        float commission = 0;
        if (!TextUtils.isEmpty(commissionString)) {
            commission = Float.parseFloat(commissionString);
        }
        float money = Float.parseFloat(userBean.money);
        float num = money - commission;

        if (num > 0) {
            mMoney.setText(commission + "");
            mMoney.setValue(money, 0, 50);
            mMoney.setTextColor(Color.YELLOW);

            // 初始化音乐资源
            try {
                // 创建MediaPlayer对象
                mp = new MediaPlayer();
                // 将音乐保存在res/raw/coin.mp3,R.java中自动生成{public static final int xingshu=0x7f040000;}
                mp = MediaPlayer.create(mContext, R.raw.coin);//新建一个的实例
                // 在MediaPlayer取得播放资源与stop()之后要准备PlayBack的状态前一定要使用MediaPlayer.prepeare()
                mp.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 开始播放音乐
            mp.start();
            // 音乐播放完毕的事件处理
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    // 循环播放
                    try {
                        mp.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            setgold(num + "");
        } else {
            mMoney.setText(userBean.money);
        }

        mPoints.setText(userBean.points);

        SharedPreferenceUtil.putCommission(mContext, userBean.money);

        switch (userBean.vipLevel) {
            case 1: //铜牌
                grade.setImageResource(R.drawable.tongpai);
                break;
            case 2: //银牌
                grade.setImageResource(R.drawable.yinpai);
                break;
            case 3: //金牌
                grade.setImageResource(R.drawable.jinpai);
                break;
            case 4: //白金
                grade.setImageResource(R.drawable.baijing);
                break;
            case 5: //砖石
                grade.setImageResource(R.drawable.zhuangshi);
                break;
            case 6: //皇冠
                grade.setImageResource(R.drawable.huangguang);
                break;
        }
        //TODO
        mPresenter.getShuaKa(userBean.id);
        mPresenter.getCourse(userBean.id);
        mPresenter.getRanking(userBean.id);
        if (!SharedPreferenceUtil.getFirstMe(getActivity())) {
            myCommission.post(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            showGuideView();
                            SharedPreferenceUtil.putFirstMe(getActivity());
                        }
                    }, 1000);

                }
            });
        }

    }

    @Override
    public void dataModificationSucceed(UpLoadingFileBean userBean) {
        mPresenter.getSignUp();
    }

    @Override
    public void dataFileBeanucceed(String upLoadingFile) {
        mPresenter.getPostUser(builderParams(upLoadingFile));
    }

    private Map<String, String> builderParams(String upLoadingFile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", IOUtils.getUserId(mContext));
        params.put("headImg", upLoadingFile + "");
        return params;
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);

    }

    @Override
    public void rankingSucceed(RankingBean bean) {
        rankingBean = bean;
        mRank.setText(bean.rownum + "");
        mCommonText.setText(bean.diff + "");
    }

    @Override
    public void CustomerSucceed(CustomerBean bean, int type) {
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
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);
    }

    @Override
    public void showLoadingTasksError() {
        CommonUtils.showToast(mContext, R.string.app_abnormal);
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<MainActivity> mActivity;

        private CustomShareListener(BaseActivity activity) {
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
                    Toast.makeText(mActivity.get(), " 分享成功", Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(mActivity.get(), platform + " 分享失败啦11", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            //Toast.makeText(mActivity.get(), platform + " 分享取消了11", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(IOUtils.getToken(mContext))) {
            mPresenter.getSignUp();
        }
        mWaveHelper.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            File f = new File(imagePath);
            compressWithRx(f);
            c.close();
        }
    }

    /**
     * 图片压缩，RX链式处理
     *
     * @param file
     */
    private void compressWithRx(File file) {
        Observable.just(file).observeOn(Schedulers.io()).map(new Func1<File, File>() {
            @Override
            public File call(File file) {
                try {
                    return Luban.with(mContext).load(file).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<File>() {
            @Override
            public void call(File file) {

                if (file != null) {
                    Logger.d("图片压缩文件路径:" + file.getAbsolutePath());
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    mPresenter.uploadFile(part);
                }

            }
        });
    }

    public void setName(String name) {
        View diaEdtext = View.inflate(mContext, R.layout.dialog_edtext, null);
        final EditText editText = (EditText) diaEdtext.findViewById(R.id.edtext);
        TextView mTitle = (TextView) diaEdtext.findViewById(R.id.title);
        editText.setText(name);
        new AlertDialog.Builder(getActivity()).setView(diaEdtext)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String budget = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(budget)) {
                            CommonUtils.showToast(mContext, "请输入昵称");
                        } else {
                            mPresenter.getPostUser(builderParams1(budget));
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    AlertDialog showGold;

    public void setgold(String num) {
        View view = View.inflate(mContext, R.layout.showgold, null);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("X " + num);
        showGold = new AlertDialog.Builder(getActivity()).setView(view).show();
        Window window = showGold.getWindow();
        window.setBackgroundDrawableResource(R.color.transparency_color);
        window.setWindowAnimations(R.style.dialog_vanish_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.stop();
                showGold.dismiss();
                new Thread(mMoney).start();//突出此自定义控件特点的使用特征
            }
        }, 2000);
    }

    /**
     * 修改名字
     *
     * @return
     */
    private Map<String, String> builderParams1(String name) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", IOUtils.getUserId(mContext));
        params.put("nickname", name + "");
        return params;
    }

    @Override
    public void onDestroy() {
        mPresenter.dettach();
        mp.stop();
        mp.release();
        super.onDestroy();
    }

    Guide guide;

    public void showGuideView() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(myCommission)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
         /*       int height = realnameLayout.getMeasuredHeight() - scrollview.getHeight()+500 ;
                scrollview.scrollTo(0, height);*/
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        showGuideView2();
                    }
                }, 500);

            }
        });

        builder1.addComponent(new SimpleComponent("佣金可以提现了"));
        guide = builder1.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }

    public void showGuideView2() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(authorizebrandlayout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView3();
            }
        });

        builder1.addComponent(new SimpleComponent("白金、钻石、皇冠用户拥有授权牌了"));
        Guide guide = builder1.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }

    public void showGuideView3() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(realnameLayout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder1.addComponent(new SimpleComponent("认识真实的你，提升安全等级"));
        Guide guide = builder1.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }


}
