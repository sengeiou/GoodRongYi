package com.lichi.goodrongyi.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.EssayListBean;
import com.lichi.goodrongyi.mvp.model.HotCircleBean;
import com.lichi.goodrongyi.mvp.presenter.DiscoverPresenter;
import com.lichi.goodrongyi.mvp.view.DiscoverView;
import com.lichi.goodrongyi.ui.activity.AllHtmlActivity;
import com.lichi.goodrongyi.ui.activity.circle.CirclActivity;
import com.lichi.goodrongyi.ui.activity.circle.EssayActivity;
import com.lichi.goodrongyi.ui.activity.circle.SplendidEssayActivity;
import com.lichi.goodrongyi.ui.activity.discover.CreditQueryWebActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.RecycleViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 发现
 */
public class DiscoverFragment extends BaseFragment<DiscoverView, DiscoverPresenter> implements DiscoverView, View.OnClickListener {
    @BindView(R.id.iv_investigation_query)
    TextView ivInvestigationQuery;
    @BindView(R.id.iv_social_security_query)
    TextView ivSocialSecurityQuery;
    @BindView(R.id.iv_accumulation_fund_query)
    TextView ivAccumulationFundQuery;
    @BindView(R.id.iv_education_query)
    TextView ivEducationQuery;
    @BindView(R.id.tv_discover_more)
    LinearLayout tvDiscoverMore; //圈子更多
    @BindView(R.id.invitationmore)
    LinearLayout invitationMore; //最新圈子更多
    Unbinder unbinder;
    private TextView mIvCreditQuery;
    private RecyclerView mGvDiscover;
    private RecyclerView mRvDiscover;
    private List<HotCircleBean.DateList> dataCircle = new ArrayList<>();

    private List<EssayListBean.DateList> dataCircleList = new ArrayList<>();
    private CommonAdapter<HotCircleBean.DateList> adapterCircle;
    private CommonAdapter<EssayListBean.DateList> adapterCircleList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        unbinder = ButterKnife.bind(this, view);
        // initData();
        init(view);
        invitationMore.setOnClickListener(this);
        tvDiscoverMore.setOnClickListener(this);
        loadData();
        return view;
    }

    private void loadData() {
        mPresenter.getSplendorCircle(1, 4);
        mPresenter.getCircleList(1, 5);

    }

    private void init(View view) {
        mGvDiscover = (RecyclerView) view.findViewById(R.id.gv_discover);
        mRvDiscover = (RecyclerView) view.findViewById(R.id.rv_discover);

        mGvDiscover.setNestedScrollingEnabled(false);
        mGvDiscover.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mGvDiscover.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 10, Color.WHITE));

        mRvDiscover.setNestedScrollingEnabled(false);
        mRvDiscover.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCircle = new CommonAdapter<HotCircleBean.DateList>(getActivity(), R.layout.discover_item1, dataCircle) {
            @Override
            public void convert(ViewHolder holder, final HotCircleBean.DateList discoverBean1, int position) {
                ImageView imageView = holder.getView(R.id.iv_discover_img1);
                TextView text1 = holder.getView(R.id.tv_discover_txt1);
                text1.setText(discoverBean1.name);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                MyPicasso.inject(discoverBean1.url, imageView, 8);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, discoverBean1.id);
                        args.put(Constants.IntentParams.ID2, discoverBean1.name);
                        CommonUtils.startNewActivity(mContext, args, EssayActivity.class);
                    }
                });
            }
        };
        adapterCircleList = new CommonAdapter<EssayListBean.DateList>(getActivity(), R.layout.discover_essay_item, dataCircleList) {
            @Override
            public void convert(ViewHolder holder, final EssayListBean.DateList item, int position) {
                ImageView imageView = holder.getView(R.id.iv_discover_img2);
                TextView text1 = holder.getView(R.id.tv_discover_txt2);
                TextView text2 = holder.getView(R.id.tv_discover_txt3);
                TextView time = holder.getView(R.id.tv_discover_time);
                text1.setText(item.title);
                text2.setText(item.name);
                if (!TextUtils.isEmpty(item.createTime)) {
                    time.setText(DateFormatUtil.timeLogi(item.createTime));
                } else {
                    time.setText("");
                }
                // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                MyPicasso.inject(item.articleImg, imageView);

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, item.id);
                        args.put(Constants.IntentParams.ID2, item.name);
                        args.put(Constants.IntentParams.ID3, item.articleImg);
                        args.put(Constants.IntentParams.ID4, true);
                        CommonUtils.startNewActivity(mContext, args, AllHtmlActivity.class);
                    }
                });
            }
        };


        mGvDiscover.setAdapter(adapterCircle);
        mRvDiscover.setAdapter(adapterCircleList);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public DiscoverPresenter initPresenter() {
        return new DiscoverPresenter();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        mPresenter.dettach();
        super.onDestroyView();
    }

    @OnClick({R.id.iv_investigation_query, R.id.iv_social_security_query, R.id.iv_accumulation_fund_query, R.id.iv_education_query})
    public void onViewClicked(View view) {
        //Intent intent = new Intent(getContext(), CreditQueryActivity.class);
        Map<String, Serializable> args = new HashMap<>();
        switch (view.getId()) {
            case R.id.iv_investigation_query:
                args.put(Constants.IntentParams.ID, "https://ipcrs.pbccrc.org.cn/page/login/loginreg.jsp");
                args.put(Constants.IntentParams.ID2, "征信查询");
                break;
            case R.id.iv_social_security_query:
                args.put(Constants.IntentParams.ID, "http://www.12333sb.com/shebaoka/");
                args.put(Constants.IntentParams.ID2, "社保查询");
                break;
            case R.id.iv_accumulation_fund_query:
                args.put(Constants.IntentParams.ID, "http://gongjijin123.com/");
                args.put(Constants.IntentParams.ID2, "公积金查询");
                break;
            case R.id.iv_education_query:
                args.put(Constants.IntentParams.ID, "https://account.chsi.com.cn/passport/login?service=https%3A%2F%2Fmy.chsi.com.cn%2Farchive%2Fj_spring_cas_security_check");
                args.put(Constants.IntentParams.ID2, "学历查询");
                break;
        }
        CommonUtils.startNewActivity(mContext, args, CreditQueryWebActivity.class);

    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public void dataHotCircleSuccess(HotCircleBean bean) {
        dataCircle.clear();
        dataCircle.addAll(bean.list);
        adapterCircle.notifyDataSetChanged();
    }

    @Override
    public void dataCircleListSuccess(EssayListBean bean) {
        dataCircleList.clear();
        dataCircleList.addAll(bean.list);
        adapterCircleList.notifyDataSetChanged();
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.invitationmore:
                CommonUtils.startNewActivity(mContext, SplendidEssayActivity.class);
                break;
            case R.id.tv_discover_more:
                CommonUtils.startNewActivity(mContext, CirclActivity.class);
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

}
