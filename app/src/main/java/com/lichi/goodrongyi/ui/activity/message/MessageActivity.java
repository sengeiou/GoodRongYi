package com.lichi.goodrongyi.ui.activity.message;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.mvp.model.MessageListBean;
import com.lichi.goodrongyi.mvp.model.NewestVideoBean;
import com.lichi.goodrongyi.mvp.model.VideoDetailsBean;
import com.lichi.goodrongyi.mvp.presenter.MessagePresenter;
import com.lichi.goodrongyi.mvp.presenter.VideoPresenter;
import com.lichi.goodrongyi.mvp.view.MessageView;
import com.lichi.goodrongyi.mvp.view.VideoView;
import com.lichi.goodrongyi.ui.activity.video.VideoActivity;
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

/**
 * Created by test on 2017/12/26.
 */

public class MessageActivity extends BaseActivity<MessageView, MessagePresenter> implements MessageView {

    private RecyclerView mRlCourseList;
    private RefreshLayout refreshLayout;
    int page = 1;
    private CommonAdapter<MessageListBean.DataList> popularityAdapter; //视频
    private List<MessageListBean.DataList> mMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_all);
        initView();
        initAdapter();
        refreshLayout.autoRefresh();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    private void initView() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlCourseList = (RecyclerView) findViewById(R.id.rl_course_list);
        mRlCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
       // mRlCourseList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.WHITE));
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

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initAdapter() {
        popularityAdapter = new CommonAdapter<MessageListBean.DataList>(getContext(), R.layout.message_item, mMessages) {
            @Override
            public void convert(ViewHolder holder, final MessageListBean.DataList item, int position) {
                View convertView = holder.getConvertView();
                TextView title = holder.getView(R.id.title);
                TextView time = holder.getView(R.id.time);
                TextView content = holder.getView(R.id.content);
                title.setText(item.title);
                time.setText(item.createTime);
                content.setText(item.content);
            }

        };
        mRlCourseList.setAdapter(popularityAdapter);
    }

    public void loadMore() {
        mPresenter.getMessageList(IOUtils.getUserId(mContext), page, 10);
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
    public void dataMessageListSucceed(MessageListBean messageListBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mMessages.clear();
            mMessages.addAll(messageListBean.list);
        } else {
            mMessages.addAll(messageListBean.list);
        }
        if (messageListBean.isLastPage) {
            refreshLayout.setLoadmoreFinished(true);
        }
        popularityAdapter.notifyDataSetChanged();
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
    public MessagePresenter initPresenter() {
        return new MessagePresenter();
    }
}
