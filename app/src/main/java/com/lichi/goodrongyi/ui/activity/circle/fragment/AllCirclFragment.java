package com.lichi.goodrongyi.ui.activity.circle.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.EssayListBean;
import com.lichi.goodrongyi.mvp.model.HotCircleBean;
import com.lichi.goodrongyi.mvp.presenter.AllPresenter;
import com.lichi.goodrongyi.mvp.view.DiscoverView;
import com.lichi.goodrongyi.ui.activity.circle.EssayActivity;
import com.lichi.goodrongyi.ui.activity.my.RankActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCirclFragment extends BaseFragment<DiscoverView, AllPresenter> implements DiscoverView {

    List<HotCircleBean.DateList> mDatas = new ArrayList<>();
    CommonAdapter<HotCircleBean.DateList> adapter;
    @BindView(R.id.rl_all_circle)
    RecyclerView mRecyclerView;
    RefreshLayout refreshLayout; //刷新
    int page = 1;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_circl, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        refreshLayout.autoRefresh();
        return view;
    }

    private void initView(View view) {
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.resetNoMoreData();
                page = 1;
                loadData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                page++;
                loadData();
            }
        });
    }

    private void loadData() {
        mPresenter.getSplendorCircle(page, 20);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 10, Color.WHITE));

        super.onViewCreated(view, savedInstanceState);
        adapter = new CommonAdapter<HotCircleBean.DateList>(getContext(), R.layout.discover_item1, mDatas) {
            @Override
            public void convert(ViewHolder holder, final HotCircleBean.DateList item, int position) {
                ImageView imageView = holder.getView(R.id.iv_discover_img1);
                TextView text1 = holder.getView(R.id.tv_discover_txt1);
                text1.setText(item.name);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                MyPicasso.inject(item.url, imageView);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, item.id);
                        args.put(Constants.IntentParams.ID2, item.name);
                        CommonUtils.startNewActivity(mContext, args, EssayActivity.class);
                    }
                });
            }
        };

        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    @Override
    public AllPresenter initPresenter() {
        return new AllPresenter();
    }

    @Override
    public void dataDefeated(String msg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(getContext(), msg);

    }

    @Override
    public void dataHotCircleSuccess(HotCircleBean bean) {

        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mDatas.clear();
            mDatas.addAll(bean.list);
        } else {
            mDatas.addAll(bean.list);
        }
        if (bean.isLastPage) {
            refreshLayout.setLoadmoreFinished(true); //没有数据了
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void dataCircleListSuccess(EssayListBean bean) {

    }


    @Override
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);

    }

    @Override
    public void showLoadingTasksError() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(getContext(), R.string.app_abnormal);

    }
}
