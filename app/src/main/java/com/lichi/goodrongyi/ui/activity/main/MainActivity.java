package com.lichi.goodrongyi.ui.activity.main;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.application.MyApplication;
import com.lichi.goodrongyi.mvp.model.NoticeBean;
import com.lichi.goodrongyi.mvp.model.VersionsBean;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.service.IMyAidlInterface2;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.fragment.CreditCardsFragment;
import com.lichi.goodrongyi.ui.fragment.DiscoverFragment;
import com.lichi.goodrongyi.ui.fragment.IndexFragment;
import com.lichi.goodrongyi.ui.fragment.MineFragment;
import com.lichi.goodrongyi.ui.fragment.NewIndexFragment;
import com.lichi.goodrongyi.utill.AppManager;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.SDCardUtils;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;


public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {
    private static final int REQUEST_CODE = 1;

    public FragmentTabHost mTabHost;
    private Class fragmentArray[] = {NewIndexFragment.class, CreditCardsFragment.class, DiscoverFragment.class, MineFragment.class};
    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.index_selecter, R.drawable.car_selecter, R.drawable.discover_selecter, R.drawable.mine_selecter
    };
    //Tab选项卡的文字
    private String mTextviewArray[] = {"首页", "信用卡", "发现", "我的"};
    //定义一个布局
    private LayoutInflater layoutInflater;
    private int currentTab = 0;
    SweetAlertDialog pDialog;
    private static String apkName = "/rongkaixin.apk";
    private String[] mPermissionList = new String[]{Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ActivityCompat.requestPermissions(this, mPermissionList, 123);
        // 申请权限。
        AndPermission.with(this).requestCode(100).permission(mPermissionList).callback(mPListener)
                .start();
        mPresenter.getNewVersion();
        mPresenter.getNotice(IOUtils.getUserId(mContext));
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.frm_content);
        final int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中

            mTabHost.addTab(tabSpec, fragmentArray[i], null);

            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabName) {

                switchToTab(tabName);

            }

        });
        //   Intent i2 = new Intent(MainActivity.this, IMyAidlInterface2.class);
        //  startService(i2);
        // requestVersionCode();
    }


    private PermissionListener mPListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // Successfully.
            if (requestCode == 100) {
                //  CommonUtils.showToast(mContext, "成功");
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // Failure.
            if (requestCode == 100) {
                // CommonUtils.showToast(mContext, "失败");
                //AppManager.getAppManager().loginOut();//当前退出只是finish栈里的Activity,不是严格意义上的退出
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
        SharedPreferenceUtil.putToken(MainActivity.this, "");
    }

    private long mExitTime = 0;

    /**
     * 监听键盘键值，实现点击丙次退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                // 如果两次按键时间间隔大于2000毫秒，则不退出
                CommonUtils.showToast(mContext, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                AppManager.getAppManager().loginOut();//当前退出只是finish栈里的Activity,不是严格意义上的退出
                //System.exit(0);// 否则退出程序

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (REQUEST_CODE == requestCode && grantResults != null && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String indextype = MyApplication.getValue(Constants.IntentParams.LOGIN_OUT);
        if (!TextUtils.isEmpty(indextype)) {
            mTabHost.setCurrentTab(0);
            MyApplication.removeValue(Constants.IntentParams.LOGIN_OUT);
        }
    }

    public void skipCard(String num) {
        MyApplication.putValue(Constants.IntentParams.INDEX, num);
        mTabHost.setCurrentTab(1);
    }


    public void switchToTab(String tabName) {

        switch (tabName) {
            case "首页":
                mTabHost.setCurrentTab(0);
                currentTab = 0;
                break;
            case "信用卡":
                mTabHost.setCurrentTab(1);
                currentTab = 1;
                break;
            case "发现":
                mTabHost.setCurrentTab(2);
                currentTab = 2;
                break;
            case "我的":
                if (!IOUtils.isLogin(mContext)) {
                    mTabHost.setCurrentTab(currentTab);
                    return;
                }
                mTabHost.setCurrentTab(3);
                currentTab = 3;
                break;
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.bottom_menu_item_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);
        return view;
    }


    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void onResume() {
        super.onResume();
    }

    /**
     * 下载apk文件
     *
     * @param url
     */
    private void downloadFile(String url) {
        pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        pDialog.showCancelButton(false);
        pDialog.showContentText(false);
        //pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        //  pDialog.getProgressHelper().stopSpinning();
        OkHttpUtils.get().url(url).build().execute(new com.zhy.http.okhttp.callback.FileCallBack(SDCardUtils.getPublicDirectory(), apkName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("下载失败");
                pDialog.showCancelButton(false);
                pDialog.setConfirmText("确定");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pDialog.dismiss();
                        finish();
                        Message msg = new Message();
                    }
                });
            }

            @Override
            public void onResponse(File response, int id) {
                File file = new File(SDCardUtils.getPublicDirectory() + apkName);
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pDialog.setTitleText("下载完成!");
                pDialog.setConfirmText("安装");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                pDialog.setTitleText("已下载" + (int) (progress * 100) + "%");
                //pDialog.getProgressHelper().setInstantProgress(progress);
                // pDialog.getProgressHelper().setProgress(progress);
            }
        });
    }

    @Override
    public void dataVersionSucceed(VersionsBean versionsBean) {
      /*  if (Float.parseFloat(versionsBean.version) > getVersion() && versionsBean.url != null && !versionsBean.url.isEmpty()) {*/
        Log.i("TT", versionsBean.versionCode + "---" + getVersionCode(mContext));
        if (Float.parseFloat(versionsBean.versionCode) > getVersionCode(mContext) && versionsBean.url != null && !versionsBean.url.isEmpty()) {
            requestVersionCode(versionsBean);
        }
        // }
    }

    @Override
    public void dataNoticeSucceed(NoticeBean noticeBean) {
        if (!TextUtils.isEmpty(noticeBean.content)) {
            setgold(noticeBean.content);
        }
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public Float getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return Float.parseFloat(info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }
    }

    /**
     * 获取版本号(内部识别号)
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public void requestVersionCode(final VersionsBean versionsBean) {
        pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("版本更新")
                .setContentText("当前程序需要更新后才能运行!")
                .setConfirmText("立即更新")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        apkName = "/rongkaixin" + versionsBean.version + ".apk";
                        download(versionsBean.url);
                    }
                })
                .setCancelText("暂不更新")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (versionsBean.status != 1) {
                            //非强制更新
                            pDialog.dismiss();
                        } else {
                            pDialog.dismiss();
                            AppManager.getAppManager().loginOut();//当前退出只是finish栈里的Activity,不是严格意义上的退出
                        }
                        //  finish();
                    }
                });

        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void download(String url) {
        if (SDCardUtils.isSDCardEnable()) {
            downloadFile(url);
        } else {
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.showCancelButton(false);
            pDialog.setConfirmText("确定");
            pDialog.setTitleText("下载失败");
            pDialog.setContentText("请插入sd卡!");
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    pDialog.dismiss();
                    finish();
                }
            });
        }
    }


    public void setgold(String content) {
        View view = View.inflate(mContext, R.layout.shownotice, null);
        TextView contentView = (TextView) view.findViewById(R.id.content);
        ImageView close = (ImageView) view.findViewById(R.id.close);
        contentView.setText(content);
        final AlertDialog showGold = new AlertDialog.Builder(this).setView(view).show();
        Window window = showGold.getWindow();
        window.setBackgroundDrawableResource(R.color.transparency_color);
        // window.setWindowAnimations(R.style.dialog_vanish_anim);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = showGold.getWindow().getAttributes(); //获取对话框当前的参数值
        //p.height = (int) (d.getHeight() *0.5); //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.9); //宽度设置为屏幕的0.5
        showGold.getWindow().setAttributes(p); //设置生效

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGold.dismiss();
            }
        });

        showGold.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // CommonUtils.showToast(mContext, "关闭了");
            }
        });

    }
}
