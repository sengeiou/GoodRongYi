package com.lichi.goodrongyi.ui.activity.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.EssayListBean;
import com.lichi.goodrongyi.mvp.presenter.EssayPresenter;
import com.lichi.goodrongyi.mvp.view.EssayView;
import com.lichi.goodrongyi.ui.activity.AllHtmlActivity;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.utill.RecycleViewDivider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2017/12/27.
 * 帖子
 */

public class EssayActivity extends BaseActivity<EssayView, EssayPresenter> implements EssayView {

    private RecyclerView mRlCourseList;
    private RefreshLayout refreshLayout;
    private TextView mName;

    int page = 1;
    private CommonAdapter<EssayListBean.DateList> EssayAdapter; //帖子
    private List<EssayListBean.DateList> mEssayData = new ArrayList<>();
    public int ID;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        ID = getIntent().getIntExtra(Constants.IntentParams.ID, -1);
        name = getIntent().getStringExtra(Constants.IntentParams.ID2);
        initView();
        mName.setText(name);
        refreshLayout.autoRefresh();
    }

    private void initView() {
        mName = (TextView) findViewById(R.id.name);
        mRlCourseList = (RecyclerView) findViewById(R.id.rl_course_list);
        mRlCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlCourseList.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRlCourseList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 4, R.color.app_title_color1));

        EssayAdapter = new CommonAdapter<EssayListBean.DateList>(mContext, R.layout.discover_essay_item, mEssayData) {
            @Override
            public void convert(ViewHolder holder, final EssayListBean.DateList item, int position) {
                ImageView imageView = holder.getView(R.id.iv_discover_img2);
                TextView title = holder.getView(R.id.tv_discover_txt2);
                TextView name = holder.getView(R.id.tv_discover_txt3);
                TextView time = holder.getView(R.id.tv_discover_time);
                title.setText(item.title);
                name.setText(item.nickname);
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
                        args.put(Constants.IntentParams.ID2, item.title);
                        args.put(Constants.IntentParams.ID3, item.articleImg);
                        args.put(Constants.IntentParams.ID4, true);
                        CommonUtils.startNewActivity(mContext, args, AllHtmlActivity.class);
                    }
                });
            }
        };
        mRlCourseList.setAdapter(EssayAdapter);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                page = 1;
                loadMore();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                loadMore();
            }
        });

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void loadMore() {
        mPresenter.getSplendorCircle(ID + "", page);
    }

    @Override
    public EssayPresenter initPresenter() {
        return new EssayPresenter();
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public void dataEssayListSuccess(EssayListBean bean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mEssayData.clear();
            mEssayData.addAll(bean.list);
        } else {
            mEssayData.addAll(bean.list);
        }
        if (bean.isLastPage) {
            refreshLayout.setLoadmoreFinished(true); //没有数据了
        }
        EssayAdapter.notifyDataSetChanged();
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
}
