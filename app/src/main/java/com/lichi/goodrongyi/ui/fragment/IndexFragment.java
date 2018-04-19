package com.lichi.goodrongyi.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
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

import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.component.SimpleComponent;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.DiagnoseBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestCourseBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.UserBean;
import com.lichi.goodrongyi.mvp.presenter.IndexPresenter;
import com.lichi.goodrongyi.mvp.view.IIndexView;
import com.lichi.goodrongyi.ui.activity.course.CourseActivity;
import com.lichi.goodrongyi.ui.activity.course.CourseContentActivity;
import com.lichi.goodrongyi.ui.activity.loans.RefundActivity;
import com.lichi.goodrongyi.ui.activity.main.MainActivity;
import com.lichi.goodrongyi.ui.activity.message.MessageActivity;
import com.lichi.goodrongyi.ui.activity.video.VideoActivity;
import com.lichi.goodrongyi.ui.activity.video.VideoAllActivity;
import com.lichi.goodrongyi.ui.activity.visa.AddBillActivity;
import com.lichi.goodrongyi.ui.activity.visa.AddMailboxActivity;
import com.lichi.goodrongyi.ui.activity.visa.DiagnoseAddLimitActivity;
import com.lichi.goodrongyi.ui.activity.visa.MailAddBillActivity;
import com.lichi.goodrongyi.ui.activity.visa.VisaDetailsActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.RecycleViewDivider;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

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
public class IndexFragment extends BaseFragment<IIndexView, IndexPresenter> implements IIndexView, View.OnClickListener {


    @BindView(R.id.limit)
    public TextView limit; //额度
    @BindView(R.id.overalllimit)
    public TextView overalllimit; //总额度
    @BindView(R.id.usagerate)
    public TextView usagerate; //使用率

    @BindView(R.id.add_new_car_bill)
    public TextView tvAddNewCarBill; //添加账单
    @BindView(R.id.msg_tv)
    public VerticalTextview msgTv; //轮播文字
    @BindView(R.id.rec_car_list)
    public RecyclerView recCarList; //信用卡
    @BindView(R.id.rec_context_list)
    public RecyclerView recContextList;  //最新课程
    @BindView(R.id.gv_popularity)
    public RecyclerView gvPopularity;  //人气课程
    @BindView(R.id.tv_more_course)
    public TextView tvMoreCourse;  //更多课程
    @BindView(R.id.refreshLayout)
    public RefreshLayout refreshLayout; //刷新
    @BindView(R.id.cardmore)
    public LinearLayout cardMore; //更多账单
    @BindView(R.id.increase)
    public TextView increase; //一键提额
    @BindView(R.id.discover_more)
    public LinearLayout discoverMore; //人气课程更多
    @BindView(R.id.messagemore)
    public LinearLayout messagemore; //消息更多
    @BindView(R.id.scrollView)
    public ScrollView scrollView; //
    @BindView(R.id.bottomlayout)
    public LinearLayout bottomlayout; //


    private Unbinder unbinder;
    private List<IndexCreditCardBean> mdatas = new ArrayList<>();
    private CommonAdapter<IndexCreditCardBean> adapter; //信用卡

    private CommonAdapter<NewestVideoBean.DataList> popularityAdapter; //视频
    private List<NewestVideoBean.DataList> mVideos = new ArrayList<>();

    private CommonAdapter<NewestCourseBean.DataList> courseAdapter;
    private List<NewestCourseBean.DataList> courseData = new ArrayList<>(); //新课数据
    private int page = 1;
    private ArrayList<String> strs = new ArrayList<String>();
    private ArrayList<MessageListBean.DataList> messageListBeanlist = new ArrayList<>(); //信息

    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
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
        gvPopularity.setLayoutManager(new GridLayoutManager(getContext(), 2));
        gvPopularity.setNestedScrollingEnabled(false);
        gvPopularity.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 10, Color.WHITE));

        recCarList.setLayoutManager(new LinearLayoutManager(getContext()));
        //recCarList.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        // recCarList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 1, Color.parseColor("#dfdfdf")));
        recCarList.setNestedScrollingEnabled(false);

        recContextList.setLayoutManager(new LinearLayoutManager(getContext()));
        // recContextList.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        //   recContextList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 1, Color.parseColor("#dfdfdf")));
        recContextList.setNestedScrollingEnabled(false);

        tvAddNewCarBill.setOnClickListener(this);
        tvMoreCourse.setOnClickListener(this);
        cardMore.setOnClickListener(this);
        increase.setOnClickListener(this);
        discoverMore.setOnClickListener(this);
        messagemore.setOnClickListener(this);

        msgTv.setTextList(strs);//加入显示内容,集合类型
        msgTv.setText(12, 5, Color.WHITE);//设置属性,具体跟踪源码
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
        mPresenter.getIndexCourseList(page);
        mPresenter.getIndexVideoList(page);
        mPresenter.getIndexBillList(page, IOUtils.getUserId(getActivity()));
        if (!TextUtils.isEmpty(IOUtils.getToken(mContext))) {
            mPresenter.getMessageList(IOUtils.getUserId(getActivity()));
        }
    }

    public void initAdapter() {
        adapter = new CommonAdapter<IndexCreditCardBean>(getContext(), R.layout.item_car_bii_items, mdatas) {
            @Override
            public void convert(ViewHolder holder, final IndexCreditCardBean item, int position) {

                LinearLayout llBillDateLabel = holder.getView(R.id.ll_bill_date_label);  //几天后出账单布局
                TextView tvItemBillday = holder.getView(R.id.tv_item_bill_day);//几天
                LinearLayout llRefundLabel = holder.getView(R.id.ll_refund_label);  //今日需要还款布局
                TextView refund = holder.getView(R.id.refund); //多少天还钱
                TextView tvItemRefund = holder.getView(R.id.tv_item_refund); //多少钱
                TextView tvRefund = holder.getView(R.id.tv_refund); //立即还款
                ImageView itemBankIcon = holder.getView(R.id.item_im_bank_icon); //银行图标
                TextView bankName = holder.getView(R.id.bank_name); //银行名字
                TextView name = holder.getView(R.id.name); //用户名字及尾号

                bankName.setText(item.issueBank);
                name.setText(item.holderName + "   ***" + item.last4digit);

                if (item.payDay < 0) { //以出账多少天
                    tvItemBillday.setText(item.billDay + "");
                    llBillDateLabel.setVisibility(View.VISIBLE);
                    llRefundLabel.setVisibility(View.GONE);
                    tvRefund.setVisibility(View.INVISIBLE);
                } else {  //
                    refund.setText(item.payDay + "日后需还款");
                    tvItemRefund.setText(item.balanceRmb + "");
                    llBillDateLabel.setVisibility(View.GONE);
                    llRefundLabel.setVisibility(View.VISIBLE);
                    tvRefund.setVisibility(View.VISIBLE);
                }
                BankIconUtils.setBankIcon(itemBankIcon, item.issueBank);

                tvRefund.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // startActivity(new Intent(getContext(), RefundActivity.class));
                        CommonUtils.showToast(mContext, "正在开发中....");
                    }
                });
                View convertView = holder.getConvertView();
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, item.id);
                        CommonUtils.startNewActivity(mContext, args, VisaDetailsActivity.class);
                    }
                });
            }

        };

        recCarList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Map<String, Serializable> args = new HashMap<>();
                args.put(Constants.IntentParams.ID, mdatas.get(position).id);
                CommonUtils.startNewActivity(mContext, args, VisaDetailsActivity.class);

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

        popularityAdapter = new CommonAdapter<NewestVideoBean.DataList>(getContext(), R.layout.item_index_popularity, mVideos) {
            @Override
            public void convert(ViewHolder holder, final NewestVideoBean.DataList item, int position) {
                View convertView = holder.getConvertView();
                TextView title = holder.getView(R.id.title);
                ImageView icon = holder.getView(R.id.item_im_icon);
                title.setText(item.description);
                MyPicasso.inject(item.videoUrl, icon, 8);
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
            }

        };

        gvPopularity.setAdapter(popularityAdapter);

        courseAdapter = new CommonAdapter<NewestCourseBean.DataList>(getContext(), R.layout.fragment_course_context, courseData) {
            @Override
            public void convert(ViewHolder holder, final NewestCourseBean.DataList item, int position) {
                TextView title = holder.getView(R.id.tv_course_title);
                TextView context = holder.getView(R.id.tv_course_context);
                ImageView imageView = holder.getView(R.id.iv_course_icon);
                title.setText(item.title);
                context.setText(item.description);
                MyPicasso.inject(item.courseUrl, imageView);
                View convertView = holder.getConvertView();
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, item.id);
                        args.put(Constants.IntentParams.ID2, item.status);
                        CommonUtils.startNewActivity(mContext, args, CourseContentActivity.class);
                    }
                });
            }
        };
        recContextList.setAdapter(courseAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMore();
        msgTv.startAutoScroll();//开始滚动
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
    public IndexPresenter initPresenter() {
        return new IndexPresenter();
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
    public void dataSucceed(UserBean userBean) {

    }

    @Override
    public void dataBillSucceed(List<IndexCreditCardBean> indexCreditCardBean) {
        mdatas.clear();
        if (indexCreditCardBean.size() > 3) {
            for (int i = 0; i < 3; i++) {
                mdatas.add(indexCreditCardBean.get(i));
            }
        } else {
            mdatas.addAll(indexCreditCardBean);
        }
        adapter.notifyDataSetChanged();
        if (mdatas.size() != 0) {
            int num = 0;
            for (IndexCreditCardBean data : mdatas) {
                String creditAmt = data.creditAmt;
                String s = creditAmt.replace(",", "");
                int i = 0;
                if (!TextUtils.isEmpty(s)) {
                    //i = Integer.parseInt(s);
                    i = (int) Double.parseDouble(s);
                } else {
                    i = 0;
                }
                num = i + num;
            }
            limit.setText(num * 5 + "");
            overalllimit.setText("总额度: " + num);
        }
        if (!SharedPreferenceUtil.getFirstHome(getActivity())) {
            discoverMore.post(new Runnable() {
                @Override
                public void run() {
                    int height = bottomlayout.getMeasuredHeight() - scrollView.getHeight() + 500;
                    scrollView.scrollTo(0, height);
                    //  scrollView.scrollTo(0,  top*3/2);// 改变滚动条的位置
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            showGuideView();
                            SharedPreferenceUtil.putFirstHome(getActivity());
                        }
                    }, 500);
                }
            });
        }

    }


    private Map<String, String> builderParams(CreditCardBean dataList) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", dataList.id);
        params.put("userId", IOUtils.getUserId(mContext));
        params.put("balanceRmb", dataList.balanceRmb);
        params.put("issueBank", dataList.issueBank);
        params.put("holderName", dataList.holderName);
        params.put("last4digit", dataList.last4digit);
        params.put("paymentDueDate", dataList.paymentDueDate);
        params.put("statementDate", dataList.statementDate);
        params.put("freeDay", dataList.freeDay + "");
        params.put("statementEndDate", dataList.statementEndDate);
        params.put("statementStartDate", dataList.statementStartDate);
        params.put("minPaymentRmb", dataList.minPaymentRmb);
        params.put("availablePoints", dataList.availablePoints);
        params.put("creditAmt", dataList.creditAmt);
        params.put("cashLimitAmt", dataList.cashLimitAmt);
        params.put("payDay", dataList.payDay + "");
        params.put("billDay", dataList.billDay + "");
        return params;
    }

    @Override
    public void dataBillDefeated() {
        mdatas.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dataSchoolBeginsSucceed(NewestCourseBean courseBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            courseData.clear();
            courseData.addAll(courseBeans.list);
            if (courseBeans.isLastPage) {
                // refreshLayout.finishLoadmoreWithNoMoreData(); //没有数据了
            }
        } else {
            courseData.addAll(courseBeans.list);
        }
        courseAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataVodeoSucceed(NewestVideoBean newestVideoBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        mVideos.clear();
        mVideos.addAll(newestVideoBean.list);
        popularityAdapter.notifyDataSetChanged();
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
    public void dataMessageDefeated() {

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
            case R.id.add_new_car_bill:
                if (IOUtils.isLogin(mContext)) {
                    startActivity(new Intent(getContext(), AddBillActivity.class));
                }
                break;
            case R.id.tv_more_course:
                startActivity(new Intent(getContext(), CourseActivity.class));
                break;
            case R.id.cardmore:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.skipCard("0");
                break;
            case R.id.increase:
                // textView.setText("哎呀我去");
                MobclickAgent.onEvent(mContext, "home_onekey");
                if (IOUtils.isLogin(mContext)) {
                    startActivity(new Intent(getContext(), DiagnoseAddLimitActivity.class));
/*                    if (isComplete) {
                        isComplete = false;
                        mPresenter.getIndexBillLists(1, IOUtils.getUserId(mContext), 99999);
                        setLoadingIndicator(true);
                    } else {
                        CommonUtils.showToast(mContext, "请稍等正在请求数据");
                    }*/
                }
                break;
            case R.id.discover_more:
                CommonUtils.startNewActivity(mContext, VideoAllActivity.class);
                break;
            case R.id.messagemore:
                if (IOUtils.isLogin(mContext)) {
                    CommonUtils.startNewActivity(mContext, MessageActivity.class);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    Guide guide;

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(discoverMore)
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
                // showGuideView2();
            }
        });
        builder.addComponent(new SimpleComponent("课程页面有新改版"));
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(getActivity());
    }

}
