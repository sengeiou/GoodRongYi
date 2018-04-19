package com.lichi.goodrongyi.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.application.MyApplication;
import com.lichi.goodrongyi.mvp.presenter.CreditCardsPresenter;
import com.lichi.goodrongyi.mvp.view.CreditCardsView;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

/**
 * 信用卡
 */
public class CreditCardsFragment extends BaseFragment<CreditCardsView, CreditCardsPresenter> implements View.OnClickListener {
    BaseFragment VisaFragment, LoansFragment, QuickCardFragment, knowFragment;
    View view = null;
    private LinearLayout mCreditselectlayout;
    private TextView mCreditselectImage;
    private LinearLayout mLendingselectlayout;
    private TextView mLendingselectImage;
    private LinearLayout mQuickcardLayout;
    private TextView mQuickcardSelect;

    public CreditCardsFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_credit_cards, container, false);
        initData();
        init(view);
        String index = MyApplication.getValue(Constants.IntentParams.INDEX);
        if (!TextUtils.isEmpty(index)) {
            if (index.equals("0")) {
                mCreditselectImage.setVisibility(View.VISIBLE);
                mLendingselectImage.setVisibility(View.INVISIBLE);
                if (knowFragment == null) {
                    VisaFragmentTab();
                } else {
                    switchoverVisaFragment();
                }
            } else {
                mCreditselectImage.setVisibility(View.INVISIBLE);
                mLendingselectImage.setVisibility(View.VISIBLE);
                if (knowFragment == null) {
                    LoansFragmentTab();
                } else {
                    switchoverLoansFragment();
                }
            }
            MyApplication.removeValue(Constants.IntentParams.INDEX);
        } else {
            if (knowFragment == null) {
                VisaFragmentTab();
            } else {
                if (knowFragment == VisaFragment) {
                    mCreditselectImage.setVisibility(View.VISIBLE);
                    mLendingselectImage.setVisibility(View.INVISIBLE);
                    switchoverVisaFragment();
                } else {
                    switchoverLoansFragment();
                    mCreditselectImage.setVisibility(View.INVISIBLE);
                    mLendingselectImage.setVisibility(View.VISIBLE);
                }
            }
        }
        return view;
    }


    private void initData() {
    }

    private void init(View view) {
        mCreditselectlayout = (LinearLayout) view.findViewById(R.id.creditselectlayout);
        mCreditselectImage = (TextView) view.findViewById(R.id.creditselect);
        mLendingselectlayout = (LinearLayout) view.findViewById(R.id.lendingselectlayout);
        mLendingselectImage = (TextView) view.findViewById(R.id.lendingselect);
        mQuickcardLayout = (LinearLayout) view.findViewById(R.id.quickcardlayout);
        mQuickcardSelect = (TextView) view.findViewById(R.id.quickcardselect);


        mQuickcardLayout.setOnClickListener(this);
        mCreditselectlayout.setOnClickListener(this);
        mLendingselectlayout.setOnClickListener(this);
    }

    @Override
    public CreditCardsPresenter initPresenter() {
        return new CreditCardsPresenter();
    }

    /**
     * 信用卡
     */
    private void VisaFragmentTab() {
        if (VisaFragment == null) {
            VisaFragment = new VisaFragment();
        }
        if (!VisaFragment.isAdded()) {
            // 提交事务
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_layout, VisaFragment).commit();
            // 记录当前Fragment
            knowFragment = VisaFragment;
        }
    }

    /**
     * 网贷
     */
    private void LoansFragmentTab() {
        if (LoansFragment == null) {
            LoansFragment = new LoansFragment();
        }
        if (!LoansFragment.isAdded()) {
            // 提交事务
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_layout, LoansFragment).commit();
            // 记录当前Fragment
            knowFragment = LoansFragment;
        }
    }

    public void switchoverVisaFragment() {
        VisaFragment = new VisaFragment();
        addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), VisaFragment);
        knowFragment = VisaFragment;
    }

    public void switchoverLoansFragment() {
        LoansFragment = new LoansFragment();
        addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), LoansFragment);
        knowFragment = LoansFragment;
    }

    public void switchoverQuickCardFragment() {
        QuickCardFragment = new QuickCardFragment();
        addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), QuickCardFragment);
        knowFragment = QuickCardFragment;
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, BaseFragment fragment) {
        if (knowFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(knowFragment).add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(knowFragment).show(fragment).commit();
        }

        knowFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.creditselectlayout:
                if (!(mCreditselectImage.getVisibility() == View.VISIBLE)) {
                    switchoverVisaFragment();
                    mCreditselectImage.setVisibility(View.VISIBLE);
                    mLendingselectImage.setVisibility(View.INVISIBLE);
                    mQuickcardSelect.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.lendingselectlayout:
                if (!(mLendingselectImage.getVisibility() == View.VISIBLE)) {
                    switchoverLoansFragment();
                    mLendingselectImage.setVisibility(View.VISIBLE);
                    mCreditselectImage.setVisibility(View.INVISIBLE);
                    mQuickcardSelect.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.quickcardlayout:
                if (!(mQuickcardSelect.getVisibility() == View.VISIBLE)) {
                    switchoverQuickCardFragment();
                    mLendingselectImage.setVisibility(View.INVISIBLE);
                    mCreditselectImage.setVisibility(View.INVISIBLE);
                    mQuickcardSelect.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.dettach();
        super.onDestroy();
    }

}

