package com.lichi.goodrongyi.ui.activity.my;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.UserRankBean;
import com.lichi.goodrongyi.mvp.presenter.RankPresenter;
import com.lichi.goodrongyi.mvp.view.RankView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CircleTransForm;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.view.waveview.WaveHelper;
import com.lichi.goodrongyi.view.waveview.WaveView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RankActivity extends BaseActivity<RankView, RankPresenter> implements RankView {

    @BindView(R.id.my_rank_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_rank_no2)
    ImageView ivRankNo2;
    @BindView(R.id.iv_rank_no1)
    ImageView ivRankNo1;
    @BindView(R.id.iv_rank_no3)
    ImageView ivRankNo3;
    @BindView(R.id.tv_rank_no2_name)
    TextView tvRankNo2Name;
    @BindView(R.id.tv_rank_no2_points)
    TextView tvRankNo2Points;
    @BindView(R.id.tv_rank_no1_name)
    TextView tvRankNo1Name;
    @BindView(R.id.tv_rank_no1_points)
    TextView tvRankNo1Points;
    @BindView(R.id.tv_rank_no3_name)
    TextView tvRankNo3Name;
    @BindView(R.id.tv_rank_no3_points)
    TextView tvRankNo3Points;
    @BindView(R.id.tv_rank_show_me)
    TextView tvRankShowMe;
    private RefreshLayout mRefreshLayou;

    @BindView(R.id.wave)
    WaveView waveView;
    private WaveHelper mWaveHelper;

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }


    private CommonAdapter<UserRankBean.UserBean> mAdapter;
    private List<UserRankBean.UserBean> data = new ArrayList<>();
    private List<UserRankBean.UserBean> mData = new ArrayList<>();

    private int page = 1;


    @Override
    public RankPresenter initPresenter() {
        return new RankPresenter();
    }

    int ranking = -1;
    boolean isRanking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.bind(this);
        ranking = getIntent().getIntExtra(Constants.IntentParams.ID, -1);
        initView();
        setAdapter();
        loadData();
    }

    private void initView() {
        waveView.setBorder(0, Color.parseColor("#44FFFFFF"));
        waveView.setWaveColor(
                Color.parseColor("#55ffffff"),
                Color.parseColor("#ffffffff"));
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        mWaveHelper = new WaveHelper(waveView, 0.3f);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRefreshLayou = (RefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayou.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayou.resetNoMoreData();
                        page = 1;
                        loadData();
                    }
                }, 1000);
            }
        });
        mRefreshLayou.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        loadData();
                    }
                }, 1000);
            }
        });
        tvRankShowMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ranking != -1) {

                    if (ranking <= 3) {
                        CommonUtils.showToast(mContext, "您已经在前三！");
                    } else {
                        int num = ranking % 3 == 0 ? ranking / 3 : ranking / 3 + 1;
                        page = 2;
                        isRanking = true;
                        mPresenter.getRank(num, 3);
                    }

                }
            }
        });
    }

    private void loadData() {
        mPresenter.getRank(page, 10);
    }

    public void setAdapter() {
        mAdapter = new CommonAdapter<UserRankBean.UserBean>(this, R.layout.my_rank_item, mData) {
            @Override
            public void convert(ViewHolder holder, UserRankBean.UserBean item, int position) {
                // 填充数据
                TextView number = holder.getView(R.id.my_rank_item_number);  // 排名每次
                ImageView headPic = holder.getView(R.id.my_rank_item_pic);   //  头像
                TextView name = holder.getView(R.id.my_rank_item_name);      // 名字
                TextView score = holder.getView(R.id.my_rank_item_score);    // 分数
                number.setText(item.rownum);
                name.setText(item.nickname);
                score.setText(item.invitation + "");
                if (!TextUtils.isEmpty(item.headImg)) {
                    if (!item.headImg.equals("http://tvax3.sinaimg.cn/default/images/default_avatar_male_180.gif")) {
                        Picasso.with(mContext)
                                .load(item.headImg)
                                .placeholder(R.drawable.defaulthead)
                                .error(R.drawable.defaulthead)
                                .transform(new CircleTransForm())
                                .into(headPic);
                    } else {
                        headPic.setImageResource(R.drawable.defaulthead);
                    }
                } else {
                    headPic.setImageResource(R.drawable.defaulthead);
                }

            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveHelper.start();
    }

    @Override
    public void rankSueecc(UserRankBean bean) {
        mRefreshLayou.finishRefresh();
        mRefreshLayou.finishLoadmore();
        if (page == 1) {
            mRefreshLayou.setLoadmoreFinished(false);
            mData.clear();
            mData.addAll(bean.list);
            MyPicasso.inject(mData.get(0).headImg, ivRankNo1, true);
            MyPicasso.inject(mData.get(1).headImg, ivRankNo2, true);
            MyPicasso.inject(mData.get(2).headImg, ivRankNo3, true);
            if (!TextUtils.isEmpty(mData.get(0).headImg)) {
                Picasso.with(mContext)
                        .load(mData.get(0).headImg)
                        .placeholder(R.drawable.defaulthead)
                        .error(R.drawable.defaulthead)
                        .transform(new CircleTransForm())
                        .into(ivRankNo1);
            } else {
                ivRankNo1.setImageResource(R.drawable.defaulthead);
            }
            if (!TextUtils.isEmpty(mData.get(1).headImg)) {
                Picasso.with(mContext)
                        .load(mData.get(1).headImg)
                        .placeholder(R.drawable.defaulthead)
                        .error(R.drawable.defaulthead)
                        .transform(new CircleTransForm())
                        .into(ivRankNo2);
            } else {
                ivRankNo1.setImageResource(R.drawable.defaulthead);
            }
            if (!TextUtils.isEmpty(mData.get(2).headImg)) {
                Picasso.with(mContext)
                        .load(mData.get(2).headImg)
                        .placeholder(R.drawable.defaulthead)
                        .error(R.drawable.defaulthead)
                        .transform(new CircleTransForm())
                        .into(ivRankNo3);
            } else {
                ivRankNo1.setImageResource(R.drawable.defaulthead);
            }

            tvRankNo1Name.setText(mData.get(0).nickname);
            tvRankNo2Name.setText(mData.get(1).nickname);
            tvRankNo3Name.setText(mData.get(2).nickname);
            tvRankNo1Points.setText(mData.get(0).invitation + "");
            tvRankNo2Points.setText(mData.get(1).invitation + "");
            tvRankNo3Points.setText(mData.get(2).invitation + "");
            mData.remove(0);
            mData.remove(0);
            mData.remove(0);
        } else {
            mData.addAll(bean.list);
        }
        if (bean.isLastPage) {
            mRefreshLayou.setLoadmoreFinished(true); //没有数据了
        }
        if (isRanking) {
            mData.clear();
            mData.addAll(bean.list);
            mRefreshLayou.setLoadmoreFinished(true); //没有数据了
            isRanking = false;
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void dataDefeated(String msg) {
        mRefreshLayou.finishRefresh();
        mRefreshLayou.finishLoadmore();
        CommonUtils.showToast(RankActivity.this, msg);


    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);


    }

    @Override
    public void showLoadingTasksError() {
        mRefreshLayou.finishRefresh();
        mRefreshLayou.finishLoadmore();
        CommonUtils.showToast(RankActivity.this, R.string.app_abnormal);

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}

