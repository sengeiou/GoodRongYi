package com.lichi.goodrongyi.ui.activity.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.CourseBean;
import com.lichi.goodrongyi.mvp.model.CourseSelectBean;
import com.lichi.goodrongyi.mvp.presenter.CoursePresenter;
import com.lichi.goodrongyi.mvp.view.CourseView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
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

/**
 * 课程列表
 */
public class CourseActivity extends BaseActivity<CourseView, CoursePresenter> implements View.OnClickListener, CourseView {
    private List<CourseSelectBean> mTabTitle = new ArrayList<>();
    private RecyclerView rlSelectList;
    private CommonAdapter<CourseSelectBean> mSelectAdapter;
    private RecyclerView mRlCourseList;
    private RefreshLayout refreshLayout;
    int page = 1;
    private CommonAdapter<CourseBean.DataList> mCourseAdapter;
    private List<CourseBean.DataList> mCourseData = new ArrayList<>();
    private String locationId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        findViewById(R.id.tv_back).setOnClickListener(this);
        initData();
        initView();
        initAdapter();
        refreshLayout.autoRefresh();
    }

    private void initView() {
        rlSelectList = (RecyclerView) findViewById(R.id.rl_select_list);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlSelectList.setLayoutManager(linearLayoutManager);
        mRlCourseList = (RecyclerView) findViewById(R.id.rl_course_list);
        mRlCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlCourseList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.TRANSPARENT));
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


    }

    private void initData() {
        String[] selectName = getResources().getStringArray(R.array.course_select_name);
        String[] selectId = getResources().getStringArray(R.array.course_select_id);
        for (int i = 0; i < selectName.length; i++) {
            CourseSelectBean courseSelectBean = new CourseSelectBean();
            if (i == 0) {
                courseSelectBean.isSelect = true;
            } else {
                courseSelectBean.isSelect = false;
            }
            courseSelectBean.nama = selectName[i];
            courseSelectBean.ID = selectId[i];
            mTabTitle.add(courseSelectBean);
        }
    }

    public void initAdapter() {
        mSelectAdapter = new CommonAdapter<CourseSelectBean>(mContext, R.layout.typeselect_item, mTabTitle) {
            @Override
            public void convert(ViewHolder holder, final CourseSelectBean item, int position) {
                LinearLayout selectlayout = holder.getView(R.id.selectlayout);
                TextView name = holder.getView(R.id.name);
                TextView select = holder.getView(R.id.select);
                name.setText(item.nama);
                if (item.isSelect) {
                    select.setVisibility(View.VISIBLE);
                    name.setTextColor(getResources().getColor(R.color.tv_blue));
                } else {
                    select.setVisibility(View.INVISIBLE);
                    name.setTextColor(getResources().getColor(R.color.app_course_title));
                }
                selectlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!item.isSelect) {
                            for (CourseSelectBean data : mTabTitle) {
                                if (item.ID.equals(data.ID)) {
                                    data.isSelect = true;
                                } else {
                                    data.isSelect = false;
                                }
                            }
                            locationId = item.ID;
                            page = 1;
                            mSelectAdapter.notifyDataSetChanged();
                            refreshLayout.autoRefresh();
                        }
                    }
                });
            }
        };
        rlSelectList.setAdapter(mSelectAdapter);

        mCourseAdapter = new CommonAdapter<CourseBean.DataList>(getContext(), R.layout.course_list_item, mCourseData) {
            @Override
            public void convert(ViewHolder holder, final CourseBean.DataList item, int position) {
                View convertView = holder.getConvertView();
                ImageView ivCourseIcon = holder.getView(R.id.iv_course_icon); //课程图片
                TextView tvCourseTitle = holder.getView(R.id.tv_course_title);//课程标题
                TextView tvSignedup = holder.getView(R.id.tv_signedup);//已报名
                TextView tvCourseDescription = holder.getView(R.id.tv_course_description);//内容
                TextView tvTeacherName = holder.getView(R.id.tv_teacher_name);//老师名字
                TextView tvCourseStartTime = holder.getView(R.id.tv_course_start_time);//开课时间
                tvCourseTitle.setText(item.title);
                tvCourseStartTime.setText("开课时间：" + item.startTime);
                tvTeacherName.setText(item.teachername);
                tvCourseDescription.setText(item.description);
                MyPicasso.inject(item.courseUrl,ivCourseIcon);

                if (item.status == -1) {
                    tvSignedup.setVisibility(View.GONE);
                } else {
                    tvSignedup.setVisibility(View.VISIBLE);
                    if (item.status == 0) {
                        tvSignedup.setText(R.string.course_signedup);
                        tvSignedup.setTextColor(getResources().getColor(R.color.tv_blue));
                    } else if (item.status == 1) {
                        tvSignedup.setText(R.string.course_purchased);
                        tvSignedup.setTextColor(getResources().getColor(R.color.cred));
                    }

                }

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
        mRlCourseList.setAdapter(mCourseAdapter);
    }

    public void loadMore() {
        mPresenter.getDataList(locationId, IOUtils.getUserId(mContext), page);
    }

    @Override
    public CoursePresenter initPresenter() {
        return new CoursePresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                CourseActivity.this.finish();
                break;
        }
    }

    @Override
    public void dataListSucceed(CourseBean courseBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mCourseData.clear();
            mCourseData.addAll(courseBeans.list);
        } else {
            mCourseData.addAll(courseBeans.list);
        }
        if (courseBeans.isLastPage) {
            refreshLayout.setLoadmoreFinished(true); //没有数据了
        }
        mCourseAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataDefeated(String msg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(mContext, msg);
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
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
