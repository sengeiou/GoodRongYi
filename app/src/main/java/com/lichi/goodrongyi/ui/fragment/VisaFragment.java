package com.lichi.goodrongyi.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.bean.CourseBean;
import com.lichi.goodrongyi.bean.VisaBean;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.IndexCreditCardBean;
import com.lichi.goodrongyi.mvp.presenter.VisaPresenter;
import com.lichi.goodrongyi.mvp.view.VisaView;
import com.lichi.goodrongyi.ui.activity.visa.AddBillActivity;
import com.lichi.goodrongyi.ui.activity.visa.AddVisaActivity;
import com.lichi.goodrongyi.ui.activity.visa.VisaDetailsActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.mvp.presenter.IndexPresenter;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.RecycleViewDivider;
import com.lichi.goodrongyi.view.MyListView;
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
 * 信用卡
 */
public class VisaFragment extends BaseFragment<VisaView, VisaPresenter> implements VisaView {
    private MyListView rlVisa;
    public RefreshLayout refreshLayout; //刷新
    private List<CreditCardBean> mCourseData = new ArrayList<>();
    private WrapAdapter<CreditCardBean> adapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_visa, container, false);
        init(view);
        initAdapter();
        return view;
    }

    public void initAdapter() {
        adapter = new WrapAdapter<CreditCardBean>(getContext(), R.layout.visa_item, mCourseData) {
            @Override
            public void convert(ViewHolder holder, final CreditCardBean item, int position) {

                LinearLayout llRepayDay = holder.getView(R.id.ll_repay_day); //离还款日还剩
                TextView day = holder.getView(R.id.tv_repay_day); //多少天
                LinearLayout llSurplus = holder.getView(R.id.ll_surplus); //今日需还款
                TextView surplus = holder.getView(R.id.tv_visa_item_surplus);//多少钱
                TextView bankName = holder.getView(R.id.tv_visa_bank_name); //银行名字
                TextView name = holder.getView(R.id.tv_visa_name); //客户名字
                TextView limit = holder.getView(R.id.tv_visa_limit); //额度
                TextView tempLimit = holder.getView(R.id.tv_visa_temp_limit); //临时额度
                ImageView itemBankIcon = holder.getView(R.id.iv_visa_icon);//图标
                TextView refund = holder.getView(R.id.refund); //还款日
                TextView bill = holder.getView(R.id.bill); //账单日

                String paytime = DateFormatUtil.stringToString(item.paymentDueDate, "yyyy-MM-dd", "MM-dd");
                String statementtime = DateFormatUtil.stringToString(item.statementDate, "yyyy-MM-dd", "MM-dd");
                bankName.setText(item.issueBank);
                limit.setText("额度：" + item.creditAmt);
                name.setText(item.holderName + "   ***" + item.last4digit);
                refund.setText(paytime);
                bill.setText(statementtime);

                if (item.payDay < 0) { //以出账多少天
                    surplus.setText(item.balanceRmb + "");
                    llRepayDay.setVisibility(View.INVISIBLE);
                    llSurplus.setVisibility(View.VISIBLE);
                } else {  //
                    day.setText(item.payDay + "");
                    llRepayDay.setVisibility(View.VISIBLE);
                    llSurplus.setVisibility(View.INVISIBLE);
                }
                BankIconUtils.setBankIcon(itemBankIcon, item.issueBank);
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
        //  rl_visa.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.TRANSPARENT));
        rlVisa.setAdapter(adapter);
    }

    public void loadMore() {
        mPresenter.getIndexBillList(page, 20, IOUtils.getUserId(getActivity()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  refreshLayout.autoRefresh();
    }


    public void init(View view) {
        rlVisa = (MyListView) view.findViewById(R.id.rl_visa);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        loadMore();
                    }
                }, 1000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        loadMore();
                    }
                }, 1000);
            }
        });

        view.findViewById(R.id.btn_visa_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IOUtils.isLogin(mContext)) {
                    startActivity(new Intent(getContext(), AddBillActivity.class));
                }
            }
        });
    }

    @Override
    public VisaPresenter initPresenter() {
        return new VisaPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        refreshLayout.autoRefresh();
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
    public void dataBillSucceed(List<CreditCardBean> indexCreditCardBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        mCourseData.clear();
        mCourseData.addAll(indexCreditCardBean);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void dataBillDefeated() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        mCourseData.clear();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void dataDefeated(String msg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        CommonUtils.showToast(mContext, msg);
    }

    @Override
    public void onDestroyView() {
        mPresenter.dettach();
        super.onDestroyView();
    }
}
