package com.lichi.goodrongyi.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.component.SimpleComponent;
import com.lichi.goodrongyi.logger.Logger;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.IndexBannerBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.model.WeekCourseBean;
import com.lichi.goodrongyi.mvp.presenter.IndexPresenter;
import com.lichi.goodrongyi.mvp.presenter.NewIndexPresenter;
import com.lichi.goodrongyi.mvp.view.IIndexView;
import com.lichi.goodrongyi.mvp.view.NewIIndexView;
import com.lichi.goodrongyi.ui.activity.circle.EssayActivity;
import com.lichi.goodrongyi.ui.activity.course.CourseActivity;
import com.lichi.goodrongyi.ui.activity.course.CourseContentActivity;
import com.lichi.goodrongyi.ui.activity.main.MainActivity;
import com.lichi.goodrongyi.ui.activity.message.MessageActivity;
import com.lichi.goodrongyi.ui.activity.my.SlotCardTaskActivity;
import com.lichi.goodrongyi.ui.activity.video.VideoActivity;
import com.lichi.goodrongyi.ui.activity.video.VideoAllActivity;
import com.lichi.goodrongyi.ui.activity.visa.AddBillActivity;
import com.lichi.goodrongyi.ui.activity.visa.DiagnoseAddLimitActivity;
import com.lichi.goodrongyi.ui.activity.visa.VisaDetailsActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.PayResult;
import com.lichi.goodrongyi.utill.RecycleViewDivider;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.lichi.goodrongyi.utill.ThreadPoolProxy;
import com.lichi.goodrongyi.view.MyListView;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 */
public class NewIndexFragment extends BaseFragment<NewIIndexView, NewIndexPresenter> implements NewIIndexView, View.OnClickListener {


    @BindView(R.id.banner)
    public Banner banner; //轮播
    @BindView(R.id.msg_tv)
    public VerticalTextview msgTv; //轮播文字
    @BindView(R.id.courselayout)
    public LinearLayout courseLayout; //课程
    @BindView(R.id.tasklayout)
    public LinearLayout taskLayout; //任务
    @BindView(R.id.channelayout)
    public LinearLayout channeLayout; //渠道
    @BindView(R.id.sharelayout)
    public LinearLayout shareLayout; //分享
    @BindView(R.id.messagemore)
    public LinearLayout messageMore; //消息更多
    @BindView(R.id.coursemore)
    public LinearLayout courseMore; //这周课程更多
    @BindView(R.id.gratismore)
    public LinearLayout gratisMore; //这免费课程更多
    @BindView(R.id.offlinemore)
    public LinearLayout offlineMore; //线下课程报名
    @BindView(R.id.weeklist)
    public RecyclerView weekList; //周课程列表
    @BindView(R.id.schoolbegins)
    public ImageView schoolBegins; //线下开课
    @BindView(R.id.rl_course_list)
    public MyListView rlCourseList; //免费课
    @BindView(R.id.scrollView)
    public ScrollView scrollView; //
    @BindView(R.id.refreshLayout)
    public RefreshLayout refreshLayout; //刷新

    private int page = 1;
    private Unbinder unbinder;
    private ArrayList<String> strs = new ArrayList<String>();
    private ArrayList<MessageListBean.DataList> messageListBeanlist = new ArrayList<>(); //信息

    private CommonAdapter<WeekCourseBean> mWeekListAdapter; //本周课程
    private List<WeekCourseBean> mWeekCourseBeans = new ArrayList<>();

    private List<NewestCourseBean.DataList> courseData = new ArrayList<>(); //新课数据线下

    private WrapAdapter<NewestVideoBean.DataList> popularityAdapter; //视频
    private List<NewestVideoBean.DataList> mVideos = new ArrayList<>();

    private List<IndexBannerBean> mBanner = new ArrayList<>(); //轮播
    private List<String> mBannerString = new ArrayList<>(); //轮播

    public NewIndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initAdapter();
        //refreshLayout.autoRefresh();
        return view;
    }

    public void init() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                page = 1;
                loadMore();
                refreshlayout.resetNoMoreData();
            }
        });

        //设置布局管理器
        LinearLayoutManager mWeekListManager = new LinearLayoutManager(mContext);
        mWeekListManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        weekList.setLayoutManager(mWeekListManager);

        messageMore.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        taskLayout.setOnClickListener(this);
        channeLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        offlineMore.setOnClickListener(this);

        msgTv.setTextList(strs);//加入显示内容,集合类型
        msgTv.setText(12, 5, Color.GRAY);//设置属性,具体跟踪源码
        msgTv.setTextStillTime(5000);//设置停留时长间隔
        msgTv.setAnimTime(500);//设置进入和退出的时间间隔
        //对单条文字的点击监听
        msgTv.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO
            }
        });
    }


    public void loadMore() {
        mPresenter.getBanner();
        mPresenter.getVideoFree(page);
        mPresenter.getIndexCourseList(page);
        mPresenter.getDataLast();
        if (!TextUtils.isEmpty(IOUtils.getToken(mContext))) {
            mPresenter.getMessageList(IOUtils.getUserId(getActivity()));
        }
    }

    public void initAdapter() {

        popularityAdapter = new WrapAdapter<NewestVideoBean.DataList>(getContext(), R.layout.video_gratis_item, mVideos) {
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
                MyPicasso.inject(item.videoUrl, icon, 10);
                if (item.status == 1) {
                    purchase.setBackgroundResource(R.drawable.video_purchase_ybg);
                    purchase.setText("已购买");
                } else {
                    purchase.setBackgroundResource(R.drawable.video_purchase_bg);
                    purchase.setText("购买");
                }
/*                if (item.disCount.equals("-1")) {
                    price.setText("￥" + item.price);
                } else {
                    price.setText("￥" + item.disCount);
                }
                if (item.price.equals("0.0")) {
                    purchase.setVisibility(View.GONE);
                } else {
                    purchase.setVisibility(View.VISIBLE);
                }*/
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
        rlCourseList.setAdapter(popularityAdapter);

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
        weekList.setAdapter(mWeekListAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMore();
        msgTv.startAutoScroll();//开始滚动;
    }

    //停止滚动
    @Override
    public void onPause() {
        super.onPause();
        if (msgTv != null) {
            msgTv.stopAutoScroll();
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        mPresenter.dettach();
        super.onDestroyView();
    }

    @Override
    public NewIndexPresenter initPresenter() {
        return new NewIndexPresenter();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);
    }

    @Override
    public void showLoadingTasksError() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(mContext, R.string.app_abnormal);
    }


    @Override
    public void dataSchoolBeginsSucceed(NewestCourseBean courseBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            courseData.clear();
            courseData.addAll(courseBeans.list);
            if (courseData.size() != 0) {
                schoolBegins.setVisibility(View.VISIBLE);
                MyPicasso.inject(courseData.get(0).courseUrl, schoolBegins, 10);
                schoolBegins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, courseData.get(0).id);
                        args.put(Constants.IntentParams.ID2, courseData.get(0).status);
                        CommonUtils.startNewActivity(mContext, args, CourseContentActivity.class);
                    }
                });
            } else {
                schoolBegins.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void dataVodeoSucceed(NewestVideoBean newestVideoBean) {
    }

    @Override
    public void dataMessageSucceed(MessageListBean messageListBean) {
        messageListBeanlist.clear();
        strs.clear();
        messageListBeanlist.addAll(messageListBean.list);
        for (MessageListBean.DataList item : messageListBeanlist) {
            strs.add(item.title);
        }
        if (strs.size() != 0) {
            msgTv.setTextList(strs);//加入显示内容,集合类型
            // msgTv.startAutoScroll();//开始滚动
        }
    }

    @Override
    public void dataWeekCourseSucceed(List<WeekCourseBean> selectbankBeans) {
        mWeekCourseBeans.clear();
        mWeekCourseBeans.addAll(selectbankBeans);
        mWeekListAdapter.notifyDataSetChanged();
    }

    @Override
    public void PayZfbVodeoSucceed(String url) {
        execAlipay(url);
    }

    @Override
    public void dataFreeVodeoSucceed(NewestVideoBean newestVideoBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        mVideos.clear();
        mVideos.addAll(newestVideoBean.list);
        popularityAdapter.notifyDataSetChanged();
    }


    @Override
    public void dataBannerSucceed(List<IndexBannerBean> indexBannerBeans) {
        mBanner.clear();
        mBannerString.clear();
        mBanner.addAll(indexBannerBeans);
        for (IndexBannerBean data : mBanner) {
            mBannerString.add(data.image);
        }
        banner.setImages(mBannerString)
                .setImageLoader(new ImageLoaderInterface() {
                    @Override
                    public void displayImage(Context context, Object path, View view) {
                        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                        MyPicasso.inject((String) path, imageView, 10);

                    }

                    @Override
                    public View createImageView(Context context) {
                        View view = View.inflate(mContext, R.layout.banner_item_view, null);
                        return view;
                    }
                })
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        //  CommonUtils.showToast(mContext, "你点击了：" + position);
                    }
                })
                .start();
    }


    @Override
    public void dataDefeated(String msg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(mContext, msg);
    }

    // TextView textView;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messagemore:
                if (IOUtils.isLogin(mContext)) {
                    CommonUtils.startNewActivity(mContext, MessageActivity.class);
                }
                break;
            case R.id.courselayout://课程
                CommonUtils.startNewActivity(mContext, VideoAllActivity.class);
                break;
            case R.id.tasklayout: //任务
                if (IOUtils.isLogin(mContext)) {
                    CommonUtils.startNewActivity(getActivity(), SlotCardTaskActivity.class);
                }
                break;
            case R.id.channelayout: //渠道
                Map<String, Serializable> args = new HashMap<>();
                args.put(Constants.IntentParams.ID, 7);
                args.put(Constants.IntentParams.ID2, "最新渠道");
                CommonUtils.startNewActivity(mContext, args, EssayActivity.class);
                break;
            case R.id.sharelayout: //分享
                break;
            case R.id.offlinemore: //线下开课报名
                startActivity(new Intent(getContext(), CourseActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }


    /**
     * 支付宝支付
     */
    private void execAlipay(final String orderInfo) {


        ThreadPoolProxy.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {

                PayTask alipay = new PayTask(getActivity());
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

}
