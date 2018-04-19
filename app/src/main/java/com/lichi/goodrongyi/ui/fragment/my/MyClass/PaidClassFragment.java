package com.lichi.goodrongyi.ui.fragment.my.MyClass;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.presenter.PaidClassPresenter;
import com.lichi.goodrongyi.mvp.presenter.SignedClassPresenter;
import com.lichi.goodrongyi.mvp.view.PaidClassView;
import com.lichi.goodrongyi.ui.activity.course.CourseContentActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
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
 * 已付费
 */
public class PaidClassFragment extends BaseFragment<PaidClassView, PaidClassPresenter> implements PaidClassView, OnRefreshListener, OnLoadmoreListener {


    @BindView(R.id.rl_course_list)
    RecyclerView mRlCourseList;
    @BindView(R.id.no_data)
    TextView noData;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private CommonAdapter<CourseBean.DataList> mCourseAdapter;
    private List<CourseBean.DataList> mCourseData = new ArrayList<>();
    private int page = 1;
    private String id;


    public PaidClassFragment() {
    }

    @Override
    public PaidClassPresenter initPresenter() {
        return new PaidClassPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        id = IOUtils.getUserId(getContext());
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRlCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mCourseAdapter = new CommonAdapter<CourseBean.DataList>(getContext(), R.layout.course_list_item, mCourseData) {
            @Override
            public void convert(ViewHolder holder, CourseBean.DataList item, int position) {
                ImageView imageView = holder.getView(R.id.iv_course_icon);
                TextView txtTitle = holder.getView(R.id.tv_course_title);
                TextView txtDesc = holder.getView(R.id.tv_course_description);
                TextView txtName = holder.getView(R.id.tv_teacher_name);
                TextView txtStart = holder.getView(R.id.tv_course_start_time);

                MyPicasso.inject(item.courseUrl, imageView);
                txtTitle.setText(item.title);
                txtDesc.setText(item.description);
                txtName.setText(item.teachername);
                txtStart.setText("开始时间:" + item.startTime);

            }

        };
        mCourseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Map<String, Serializable> map = new HashMap<>();
                map.put(Constants.IntentParams.ID, mCourseData.get(position).id);
                map.put(Constants.IntentParams.ID2, mCourseData.get(position).status);
                CommonUtils.startNewActivity(getContext(), map, CourseContentActivity.class);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mRlCourseList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.TRANSPARENT));
        mRlCourseList.setAdapter(mCourseAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void initData() {
        mPresenter.getMyClass(page, 1, id);
        Log.e("id", id);
    }

    @Override
    public void dataDefeated(String msg) {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        CommonUtils.showToast(mContext, msg);

    }

    @Override
    public void dataSueecc(CourseBean bean) {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mCourseData.clear();
        }
        mCourseData.addAll(bean.list);
        if (bean.isLastPage) {
            refreshLayout.setLoadmoreFinished(true);
        }
        mCourseAdapter.notifyDataSetChanged();
        if (mCourseData.size() < 1) {
            noData.setVisibility(View.VISIBLE);
            mRlCourseList.setVisibility(View.GONE);
        } else {
            noData.setVisibility(View.GONE);
            mRlCourseList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        showLoadingIndicator(active);
    }

    @Override
    public void showLoadingTasksError() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
        CommonUtils.showToast(mContext, R.string.app_abnormal);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
            }
        }, 1000);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                initData();
            }
        }, 1000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

}
