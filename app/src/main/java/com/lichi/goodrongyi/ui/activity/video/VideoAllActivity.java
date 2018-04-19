package com.lichi.goodrongyi.ui.activity.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.component.SimpleComponent;
import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.SelectbankBean;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;
import com.lichi.goodrongyi.mvp.presenter.VideoAllPresenter;
import com.lichi.goodrongyi.mvp.presenter.VideoPresenter;
import com.lichi.goodrongyi.mvp.view.VideoAllView;
import com.lichi.goodrongyi.mvp.view.VideoView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.PayResult;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.lichi.goodrongyi.utill.ThreadPoolProxy;
import com.lichi.goodrongyi.view.DropDownBankSpinner;
import com.lichi.goodrongyi.view.MyListView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2017/12/26.
 */

public class VideoAllActivity extends BaseActivity<VideoAllView, VideoAllPresenter> implements VideoAllView, View.OnClickListener {

    private MyListView mRlCourseList;
    private RecyclerView mWeekList;
    private RefreshLayout refreshLayout;
    int page = 1;
    private WrapAdapter<NewestVideoBean.DataList> popularityAdapter; //视频
    private List<NewestVideoBean.DataList> mVideos = new ArrayList<>();
    private CommonAdapter<WeekCourseBean> mWeekListAdapter; //本周课程
    private List<WeekCourseBean> mWeekCourseBeans = new ArrayList<>();

    private String locationId = "1";
    private String codeString = "4";
    DropDownBankSpinner mDropDownBankSpinner;
    LinearLayout cardmore;
    TextView tvBack;
    TextView zsyh;
    TextView gsyh;
    TextView jsyh;
    LinearLayout courseLayout;
    LinearLayout conditionLayout;
    List<SelectbankBean> dataList = new ArrayList<>();

    Guide guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_all);
        initView();
        initAdapter();
        mPresenter.getDataDict();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
        mPresenter.getDataLast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }


    private void initView() {
        zsyh = (TextView) findViewById(R.id.zsyh);
        gsyh = (TextView) findViewById(R.id.gsyh);
        jsyh = (TextView) findViewById(R.id.jsyh);
        courseLayout = (LinearLayout) findViewById(R.id.courselayout);
        conditionLayout = (LinearLayout) findViewById(R.id.conditionlayout);
        cardmore = (LinearLayout) findViewById(R.id.cardmore);
        tvBack = (TextView) findViewById(R.id.tv_back);
        mWeekList = (RecyclerView) findViewById(R.id.weeklist);
        //设置布局管理器
        LinearLayoutManager mWeekListManager = new LinearLayoutManager(mContext);
        mWeekListManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mWeekList.setLayoutManager(mWeekListManager);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlCourseList = (MyListView) findViewById(R.id.rl_course_list);
        //mRlCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRlCourseList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.WHITE));
        //  mRlCourseList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.TRANSPARENT));
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                page = 1;
                loadMore();
                refreshlayout.resetNoMoreData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                loadMore();
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zsyh.setOnClickListener(this);
        gsyh.setOnClickListener(this);
        jsyh.setOnClickListener(this);
        cardmore.setOnClickListener(this);
    }

    public void initAdapter() {
        popularityAdapter = new WrapAdapter<NewestVideoBean.DataList>(getContext(), R.layout.video_all_item, mVideos) {
            @Override
            public void convert(ViewHolder holder, final NewestVideoBean.DataList item, int position) {
                View convertView = holder.getConvertView();
                TextView title = holder.getView(R.id.tv_discover_txt1);
                ImageView icon = holder.getView(R.id.iv_discover_img1);
                TextView purchase = holder.getView(R.id.purchase);
                TextView description = holder.getView(R.id.description);
                title.setText(item.title);
                description.setText(item.description);
                //MyPicasso.inject(item.videoUrl, icon);
                TextView price = holder.getView(R.id.price);
                MyPicasso.inject(item.videoUrl, icon, 8);
                if (item.status == 1) {
                    purchase.setBackgroundResource(R.drawable.video_purchase_ybg);
                    purchase.setText("已购买");
                } else {
                    purchase.setBackgroundResource(R.drawable.video_purchase_bg);
                    purchase.setText("购买");
                }
                if (item.disCount.equals("-1")) {
                    price.setText("￥" + item.price);
                } else {
                    price.setText("￥" + item.disCount);
                }
                if (item.price.equals("0.0")) {
                    purchase.setVisibility(View.GONE);
                } else {
                    purchase.setVisibility(View.VISIBLE);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.VIDEO_URL, item.url);
                        args.put(Constants.IntentParams.ID, item.id + "");
                        CommonUtils.startNewActivity(mContext, args, VideoActivity.class);
                        //JCVideoPlayerStandard.startFullscreen(mContext, JCVideoPlayerStandard.class, "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", "测试一下");
                    }
                });
                purchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IOUtils.isLogin(mContext)) {
                            if (item.status == 1) {

                            } else {
                                if (IOUtils.isLogin(mContext)) {
                                    mPresenter.getZfbPayVideo(item.id + "");
                                }
                            }
                        }
                    }
                });
            }

        };
        mRlCourseList.setAdapter(popularityAdapter);
        mWeekListAdapter = new CommonAdapter<WeekCourseBean>(getContext(), R.layout.video_week_item, mWeekCourseBeans) {
            @Override
            public void convert(ViewHolder holder, final WeekCourseBean item, int position) {
                View convertView = holder.getConvertView();
                TextView title = holder.getView(R.id.tv_discover_txt1);
                TextView purchase = holder.getView(R.id.purchase);
                ImageView icon = holder.getView(R.id.iv_discover_img1);
                TextView price = holder.getView(R.id.price);
                title.setText(item.title);
                //MyPicasso.inject(item.videoUrl, icon);
                MyPicasso.inject(item.videoUrl, icon, 8);
                if (item.status == 1) {
                    purchase.setBackgroundResource(R.drawable.video_purchase_ybg);
                    purchase.setText("已购买");
                } else {
                    purchase.setBackgroundResource(R.drawable.video_purchase_bg);
                    purchase.setText("购买");
                }
                if (item.disCount.equals("-1")) {
                    price.setText("￥" + item.price);
                } else {
                    price.setText("￥" + item.disCount);
                }
                if (item.price.equals("0.0")) {
                    purchase.setVisibility(View.GONE);
                } else {
                    purchase.setVisibility(View.VISIBLE);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.VIDEO_URL, item.url);
                        args.put(Constants.IntentParams.ID, item.id + "");
                        CommonUtils.startNewActivity(mContext, args, VideoActivity.class);
                        //JCVideoPlayerStandard.startFullscreen(mContext, JCVideoPlayerStandard.class, "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", "测试一下");
                    }
                });
                purchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.status == 1) {

                        } else {
                            if (IOUtils.isLogin(mContext)) {
                                mPresenter.getZfbPayVideo(item.id + "");
                            }
                        }
                    }
                });
            }

        };
        mWeekList.setAdapter(mWeekListAdapter);
    }


    /**
     * 支付宝支付
     */
    private void execAlipay(final String orderInfo) {


        ThreadPoolProxy.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {

                PayTask alipay = new PayTask(VideoAllActivity.this);
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
                        //mPresenter.getDataCourse(ID);
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

    public void loadMore() {
        mPresenter.getIndexVideoList(page, codeString);
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
    public void dataScreenSucceed(List<SelectbankBean> selectbankBeans) {
        dataList.clear();
        dataList.addAll(selectbankBeans);
    }

    @Override
    public void dataWeekCourseSucceed(List<WeekCourseBean> selectbankBeans) {
        mWeekCourseBeans.clear();
        mWeekCourseBeans.addAll(selectbankBeans);
        mWeekListAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataVodeoSucceed(NewestVideoBean newestVideoBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mVideos.clear();
            mVideos.addAll(newestVideoBean.list);
        } else {
            mVideos.addAll(newestVideoBean.list);
        }
        if (newestVideoBean.isLastPage) {
            refreshLayout.setLoadmoreFinished(true);
        }
        popularityAdapter.notifyDataSetChanged();
        if (!SharedPreferenceUtil.getFirstVideo(mContext)) {
            courseLayout.post(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            showGuideView();
                            SharedPreferenceUtil.putFirstVideo(mContext);
                        }
                    }, 1000);

                }
            });
        }

    }

    @Override
    public void PayVodeoSucceed(String url) {
        Map<String, Serializable> args = new HashMap<>();
        args.put(Constants.IntentParams.ID, url);
        CommonUtils.startNewActivity(mContext, args, VideoPayWebActivity.class);
    }

    @Override
    public void PayZfbVodeoSucceed(String url) {
        execAlipay(url);
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
    public VideoAllPresenter initPresenter() {
        return new VideoAllPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zsyh:
                codeString = "4";
                selectBank(1);
                page = 1;
                refreshLayout.resetNoMoreData();
                refreshLayout.autoRefresh();
                for (SelectbankBean data : dataList) {
                    if (data.code.equals("4")) {
                        data.isSelected = true;
                    } else {
                        data.isSelected = false;
                    }
                }
                break;
            case R.id.gsyh:
                codeString = "13";
                page = 1;
                selectBank(2);
                refreshLayout.resetNoMoreData();
                refreshLayout.autoRefresh();
                for (SelectbankBean data : dataList) {
                    if (data.code.equals("13")) {
                        data.isSelected = true;
                    } else {
                        data.isSelected = false;
                    }
                }
                break;
            case R.id.jsyh:
                codeString = "3";
                selectBank(3);
                page = 1;
                refreshLayout.resetNoMoreData();
                refreshLayout.autoRefresh();
                for (SelectbankBean data : dataList) {
                    if (data.code.equals("3")) {
                        data.isSelected = true;
                    } else {
                        data.isSelected = false;
                    }
                }
                break;
            case R.id.cardmore:
                if (dataList.size() == 0 || dataList == null) {
                    CommonUtils.showToast(mContext, "正在获取数据请稍等....");
                } else {
                    mDropDownBankSpinner = new DropDownBankSpinner(mContext, dataList);
                    mDropDownBankSpinner.showAsDropDownBelwBtnView(tvBack);
                    mDropDownBankSpinner.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SelectbankBean selectDataItem = mDropDownBankSpinner.getSelectDataItem();
                            if (selectDataItem != null && mDropDownBankSpinner.getIsSelec()) {
                                if (selectDataItem.code.equals("4")) {
                                    selectBank(1);
                                } else if (selectDataItem.code.equals("13")) {
                                    selectBank(2);
                                } else if (selectDataItem.code.equals("3")) {
                                    selectBank(3);
                                } else {
                                    selectBank(4);
                                }
                                codeString = selectDataItem.code;
                                page = 1;
                                refreshLayout.resetNoMoreData();
                                refreshLayout.autoRefresh();
                            }
                        }
                    });
                }
                break;
        }

    }

    public void selectBank(int type) {
        switch (type) {
            case 1:
                zsyh.setTextColor(getResources().getColor(R.color.tv_blue));
                gsyh.setTextColor(getResources().getColor(R.color.text_title));
                jsyh.setTextColor(getResources().getColor(R.color.text_title));
                break;
            case 2:
                zsyh.setTextColor(getResources().getColor(R.color.text_title));
                gsyh.setTextColor(getResources().getColor(R.color.tv_blue));
                jsyh.setTextColor(getResources().getColor(R.color.text_title));
                break;
            case 3:
                zsyh.setTextColor(getResources().getColor(R.color.text_title));
                gsyh.setTextColor(getResources().getColor(R.color.text_title));
                jsyh.setTextColor(getResources().getColor(R.color.tv_blue));
                break;
            case 4:
                zsyh.setTextColor(getResources().getColor(R.color.text_title));
                gsyh.setTextColor(getResources().getColor(R.color.text_title));
                jsyh.setTextColor(getResources().getColor(R.color.text_title));
                break;
        }

    }

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(courseLayout)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                showGuideView2();
            }
        });
        builder.addComponent(new SimpleComponent("精心推荐每周优质课程"));
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(VideoAllActivity.this);
    }


    public void showGuideView2() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(conditionLayout)
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

        builder1.addComponent(new SimpleComponent("选择银行及网贷分类，筛选视频课程"));
        Guide guide = builder1.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(VideoAllActivity.this);
    }

}
