package com.lichi.goodrongyi.ui.activity.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.QrcodeBean;
import com.lichi.goodrongyi.mvp.presenter.QrcodePresenter;
import com.lichi.goodrongyi.mvp.view.QrcodeView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.BitmapUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.MyPicasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QrcodeActivity extends BaseActivity<QrcodeView, QrcodePresenter> implements QrcodeView, View.OnClickListener {


    ImageView ivBack;//返回
    ImageView headPic;//头像
    ImageView qrcode;//二维码
    TextView name;//名字
    TextView title;//标题
    TextView content;//内容
    TextView btnDownload;//下载
    LinearLayout layout;//布局
    ScrollView scrollview;//布局
    Bitmap qrBitmap = null;

    @Override
    public QrcodePresenter initPresenter() {
        return new QrcodePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        headPic = (ImageView) findViewById(R.id.head_pic);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        name = (TextView) findViewById(R.id.name);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        btnDownload = (TextView) findViewById(R.id.btn_download);
        layout = (LinearLayout) findViewById(R.id.layout);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        btnDownload.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        mPresenter.getqrCode();
    }

    @Override
    public void dataCodeSucceed(QrcodeBean qrcodeBean) {
        name.setText("我是 ：" + qrcodeBean.nickname);
        title.setText(qrcodeBean.desc);
        content.setText(qrcodeBean.text);
        MyPicasso.inject(qrcodeBean.headImg, headPic, true);
        qrBitmap = generateBitmap(Constants.BASE_H5URL + "/dist/invite/index.html?pid=" + IOUtils.getUserId(mContext), 1000, 1000);
        // Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        qrcode.setImageBitmap(qrBitmap);
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                btnDownload.setVisibility(View.GONE);
                // 获取ScrollView 实际高度
                int h = 0;
                for (int i = 0; i < scrollview.getChildCount(); i++) {
                    h += scrollview.getChildAt(i).getHeight();
                    //layout.getChildAt(i).setBackgroundResource(R.color.qrcode_background_color);
                }
                // 创建对应大小的bitmap
                qrBitmap = Bitmap.createBitmap(scrollview.getWidth(), h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(qrBitmap);
                scrollview.draw(canvas);
                if (qrBitmap != null) {
                    saveBitmap();
                } else {
                    CommonUtils.showToast(mContext, "二维码生成错误");
                }
                btnDownload.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
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

    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/rkx/Camera/");

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
        sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
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

}
