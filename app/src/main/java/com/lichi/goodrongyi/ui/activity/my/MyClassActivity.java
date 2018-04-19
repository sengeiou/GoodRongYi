package com.lichi.goodrongyi.ui.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.MyClassPresenter;
import com.lichi.goodrongyi.mvp.view.MyClassView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.fragment.VisaFragment;
import com.lichi.goodrongyi.ui.fragment.my.MyClass.PaidClassFragment;
import com.lichi.goodrongyi.ui.fragment.my.MyClass.SignedClassFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyClassActivity extends BaseActivity<MyClassView,MyClassPresenter> {
    @BindView(R.id.my_my_class_tab)
    TabLayout mTabLayout;
    @BindView(R.id.my_my_class)
    ViewPager mViewPager;
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }


    private List<String> title;
    private List<Fragment> fragments;
    @Override
    public MyClassPresenter initPresenter(){
        return new MyClassPresenter();
    }

    private void initData(){
        title = new ArrayList<>();
        fragments = new ArrayList<>();

        title.clear();
        fragments.clear();

        title.add("已交费");
        title.add("已报名");

        fragments.add(new PaidClassFragment());
        fragments.add(new SignedClassFragment());
    }

    private void initView() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        mTabLayout.addTab(mTabLayout.newTab().setText(title.get(0)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(title.get(1)));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
