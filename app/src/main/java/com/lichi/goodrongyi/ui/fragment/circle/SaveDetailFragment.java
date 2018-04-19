package com.lichi.goodrongyi.ui.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.SaveDetailPresenter;
import com.lichi.goodrongyi.mvp.view.SaveDetailView;
import com.lichi.goodrongyi.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 默小小 on 2017/12/13.
 */

public class SaveDetailFragment extends BaseFragment<SaveDetailView, SaveDetailPresenter> {
    @BindView(R.id.rl_data_list)
    RecyclerView rlDataList;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    Unbinder unbinder;

    @Override
    public SaveDetailPresenter initPresenter() {
        return new SaveDetailPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.dettach();
    }
}
