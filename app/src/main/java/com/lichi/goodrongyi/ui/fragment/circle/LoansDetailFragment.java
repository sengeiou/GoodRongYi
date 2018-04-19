package com.lichi.goodrongyi.ui.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.LoansDetailPresenter;
import com.lichi.goodrongyi.mvp.view.LoansDetailView;
import com.lichi.goodrongyi.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 默小小 on 2017/12/13.
 */

public class LoansDetailFragment extends BaseFragment<LoansDetailView, LoansDetailPresenter> {
    @BindView(R.id.rl_data_list)
    RecyclerView rlDataList;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    Unbinder unbinder;

    @Override
    public LoansDetailPresenter initPresenter() {
        return new LoansDetailPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        rlDataList.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.dettach();
    }
}
