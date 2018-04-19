package com.lichi.goodrongyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.model.LoansBean;
import com.lichi.goodrongyi.mvp.model.QuickCardBean;
import com.lichi.goodrongyi.mvp.presenter.LoansPresenter;
import com.lichi.goodrongyi.mvp.presenter.QuickCardPresenter;
import com.lichi.goodrongyi.mvp.view.LoansView;
import com.lichi.goodrongyi.mvp.view.QuickCardView;
import com.lichi.goodrongyi.ui.activity.discover.CreditQueryWebActivity;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.GlideImageLoader;
import com.lichi.goodrongyi.utill.MyPicasso;
import com.lichi.goodrongyi.view.MyListView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快卡
 * 参考github banner  https://github.com/youth5201314/banner
 */
public class QuickCardFragment extends BaseFragment<QuickCardView, QuickCardPresenter> implements QuickCardView {
    private MyListView rlLoans;
    private WrapAdapter<QuickCardBean> adapter;
    private List<QuickCardBean> data = new ArrayList<>();
    private List<String> mimageurls = new ArrayList<String>(); //轮播数据源
    private Banner banner;
    public RefreshLayout refreshLayout; //刷新

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_quick_card, container, false);
        initData();
        init(view);
        setAdapter();
        mPresenter.getNetloan();
        mPresenter.getBanner();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {

    }

    private void init(View view) {
        rlLoans = (MyListView) view.findViewById(R.id.rl_loans);
        banner = (Banner) view.findViewById(R.id.banner);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh();
                refreshlayout.resetNoMoreData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshLayout.finishLoadmore();
                    }
                }, 1000);
            }
        });
    }

    public void setAdapter() {
        //rlLoans.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WrapAdapter<QuickCardBean>(getContext(), R.layout.quick_card_item, data) {
            @Override
            public void convert(ViewHolder holder, final QuickCardBean loansBean, int position) {
                TextView activity = holder.getView(R.id.tv_loans_activity);
                TextView des = holder.getView(R.id.tv_loans_des);
                ImageView icon = holder.getView(R.id.iv_loans_icon);
                TextView promptly = holder.getView(R.id.bt_loans_ok);
                MyPicasso.inject(loansBean.img, icon);
                activity.setText(loansBean.title);
                des.setText(loansBean.description);
                promptly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, loansBean.url);
                        args.put(Constants.IntentParams.ID2, loansBean.title);
                        CommonUtils.startNewActivity(mContext, args, CreditQueryWebActivity.class);
                    }
                });
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID, loansBean.url);
                        args.put(Constants.IntentParams.ID2, loansBean.title);
                        CommonUtils.startNewActivity(mContext, args, CreditQueryWebActivity.class);
                    }
                });
            }
        };

        //rlLoans.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 10, Color.TRANSPARENT));
        rlLoans.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public QuickCardPresenter initPresenter() {
        return new QuickCardPresenter();
    }


    @Override
    public void dataBillSucceed(List<QuickCardBean> loansBean) {
        data.clear();
        data.addAll(loansBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void datagetBannerSucceed(List<QuickCardBean> loansBean) {
        mimageurls.clear();
/*        for (int i = 1; i <= 5; i++) {
            mimageurls.add("http://img1.3lian.com/2015/a1/95/d/105.jpg");
        }*/
        for (QuickCardBean data : loansBean) {
            mimageurls.add(data.img);
        }
        banner.setImages(mimageurls)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        //  CommonUtils.showToast(mContext, "你点击了：" + position);
                    }
                })
                .start();
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);
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
    public void onDestroy() {
        mPresenter.dettach();
        super.onDestroy();
    }

}
