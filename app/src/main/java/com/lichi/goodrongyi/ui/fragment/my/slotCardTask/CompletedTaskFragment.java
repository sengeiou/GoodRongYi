package com.lichi.goodrongyi.ui.fragment.my.slotCardTask;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.model.UserCardBean;
import com.lichi.goodrongyi.mvp.model.UserCardNumBean;
import com.lichi.goodrongyi.mvp.presenter.SlotCardTaskPresenter;
import com.lichi.goodrongyi.mvp.view.SlotCardTaskView;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.view.MyListView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ArDu on 2017/12/8.
 */

public class CompletedTaskFragment extends BaseFragment<SlotCardTaskView, SlotCardTaskPresenter> implements SlotCardTaskView, OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.slot_card_whole_task)
    MyListView mRecyclerView;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private List<UserCardBean.SlotCardTasksBean> mData = new ArrayList<>();
    private WrapAdapter<UserCardBean.SlotCardTasksBean> mAdapter;
    int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public SlotCardTaskPresenter initPresenter() {
        return new SlotCardTaskPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whole_task, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        loadData();
        setAdapter();
        return view;
    }

    public void loadData() {
        mPresenter.getUserCard(page, 1, IOUtils.getUserId(getContext()));
    }

    public void loadData1() {
        page = 1;
        mPresenter.getUserCard(page, 1, IOUtils.getUserId(getContext()));
    }


    public void setAdapter() {
        mAdapter = new WrapAdapter<UserCardBean.SlotCardTasksBean>(getActivity(), R.layout.slot_card_task_item_layout, mData) {
            @Override
            public void convert(ViewHolder holder, UserCardBean.SlotCardTasksBean item, int position) {
                LinearLayout taskLayout = holder.getView(R.id.task_item);        // 显示刷卡任务的item布局
                LinearLayout dateLayout = holder.getView(R.id.date_item);        // 显示日期额item布局
                ImageView completedTaskImg = holder.getView(R.id.slot_card_task_c_img);     // 已完成任务的图标
                ImageView bankIcon = holder.getView(R.id.slot_card_task_bank_img);         //  银行的图标
                TextView bankName = holder.getView(R.id.slot_card_task_bank_name);         // 银行的名称
                TextView userName = holder.getView(R.id.slot_card_task_user_name);         // 用户名称
                TextView score = holder.getView(R.id.slot_card_task_score);                // 用户名称旁边的数字
                TextView number = holder.getView(R.id.slot_card_task_num);                 // 推荐消费上边的数字
                TextView recommentType = holder.getView(R.id.slot_card_task_comment_type); // 推荐消费类型
                TextView date = holder.getView(R.id.slot_card_task_date);                  // 日期
                TextView practical = holder.getView(R.id.practical);                  // 实际消费
                // 初始化隐藏
                taskLayout.setVisibility(View.GONE);
                dateLayout.setVisibility(View.GONE);
                completedTaskImg.setVisibility(View.GONE);
                practical.setVisibility(View.GONE);

                //
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String s = DateFormatUtil.dateToString(curDate, "yyyy-MM-dd");

                if (position == 0) {
                    dateLayout.setVisibility(View.VISIBLE);
                    if (s.equals(item.time)) {
                        date.setText("今天");
                    } else {
                        date.setText(item.time);
                    }

                } else {
                    if (item.time.equals(mData.get(position - 1).time)) {
                        dateLayout.setVisibility(View.GONE);
                    } else {
                        dateLayout.setVisibility(View.VISIBLE);
                        date.setText(item.time);
                    }
                }


                //
                if (item.type == 0) {             // 该item项显示时间
                    // dateLayout.setVisibility(View.VISIBLE);
                    //  date.setText(item.time);
                } else {
                    taskLayout.setVisibility(View.VISIBLE);
                    if (item.status == 1) {
                        completedTaskImg.setVisibility(View.VISIBLE);
                        practical.setText("实际消费：" + item.consumeMoney);
                    }
                }

                bankName.setText(item.bankname);
                userName.setText(item.holderName);
                score.setText(item.last4);
                number.setText(item.money + "");
                recommentType.setText("推荐消费：" + item.name);
                BankIconUtils.setBankIcon(bankIcon, item.bankname);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void dataSuccee(UserCardBean bean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            refreshLayout.setLoadmoreFinished(false);
            mData.clear();
        }
        mData.addAll(bean.list);

        if (bean.isLastPage) {
            refreshLayout.setLoadmoreFinished(true);
        }
        if (mData.size() < 1) {
            noData.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            noData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataSucceeAll(UserCardBean bean) {

    }

    @Override
    public void dataSueeccUn(UserCardBean bean) {

    }

    @Override
    public void dataUpdataSueeccUn(UserCardBean bean) {

    }

    @Override
    public void dataNumBeanSueeccUn(UserCardNumBean bean) {

    }

    @Override
    public void dataDefeated(String msg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(mContext, msg);

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
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                loadData();
            }
        }, 1000);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData();
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
