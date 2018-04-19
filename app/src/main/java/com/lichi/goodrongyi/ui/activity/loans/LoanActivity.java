package com.lichi.goodrongyi.ui.activity.loans;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.adapter.ViewPagerAdapter;
import com.lichi.goodrongyi.bean.LoansBean;
import com.lichi.goodrongyi.mvp.presenter.LoanPresenter;
import com.lichi.goodrongyi.mvp.presenter.LoansPresenter;
import com.lichi.goodrongyi.mvp.view.LoanView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoanActivity extends BaseActivity<LoanView, LoanPresenter> implements View.OnClickListener {

    private CommonAdapter<LoansBean> mRlAdapter;
    private List<LoansBean> mData;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<View> mViews = new ArrayList<>();

    private int[] ids={R.mipmap.temp1,R.mipmap.temp2,R.mipmap.temp3,R.mipmap.temp2,R.mipmap.temp1};

    @BindView(R.id.vp_loans)
    ViewPager mVpLoans;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rl_loans)
    RecyclerView mRlLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        mData = new ArrayList<>();
        LoansBean bean = new LoansBean();
        for(int i = 0; i < 5; i++) {
            mData.add(bean);
        }
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);

        mRlAdapter = new CommonAdapter<LoansBean>(this, R.layout.item_activity_loan, mData) {
            @Override
            public void convert(ViewHolder holder, LoansBean item, int position) {
                Button aplyButton = holder.getView(R.id.btn_loan_aply);
                aplyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoanActivity.this, AplayActivity.class));
                    }
                });
            }
        };
        mRlLoans.setLayoutManager(new LinearLayoutManager(this));
        mRlLoans.setAdapter(mRlAdapter);
        mViews.clear();
        for (int i = 0; i < 5; i++) {
            mViews.add(getViewPagerView(ids[i]));
        }
        mViewPagerAdapter=new ViewPagerAdapter(mViews);
        mVpLoans.setAdapter(mViewPagerAdapter);
    }

    public View getViewPagerView(int url){
        ImageView mImageView;
        View view = View.inflate(this, R.layout.item_loans_pager, null);
        mImageView= (ImageView) view.findViewById(R.id.iv_loans_ad);
        mImageView.setImageResource(url);
        return view;
    }

    @Override
    public LoanPresenter initPresenter(){
        return new LoanPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                LoanActivity.this.finish();
                break;
        }
    }

}
