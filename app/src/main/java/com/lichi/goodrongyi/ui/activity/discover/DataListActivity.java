package com.lichi.goodrongyi.ui.activity.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.bean.QueryUserBean;
import com.lichi.goodrongyi.mvp.presenter.DataListPresenter;
import com.lichi.goodrongyi.mvp.view.DataListView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.fragment.circle.BaseInfoFragment;
import com.lichi.goodrongyi.ui.fragment.circle.LoansDetailFragment;
import com.lichi.goodrongyi.ui.fragment.circle.SaveDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataListActivity extends BaseActivity<DataListView, DataListPresenter> {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.iv_data_list_more)
    ImageView ivDataListMore;
    @BindView(R.id.tab_data_list)
    TabLayout tabDataList;
    @BindView(R.id.vp_data_list)
    ViewPager mvp;
    private List<String> title;
    private List<Fragment> fragmets;
    private QueryUserBean  userBean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBean = (QueryUserBean) getIntent().getSerializableExtra("data");
        setContentView(R.layout.activity_data_list);
        ButterKnife.bind(this);
        init();

    }

    public void init(){
        title=new ArrayList<>();
        fragmets=new ArrayList<>();

        title.add("基本信息");
        title.add("存费明细");
        title.add("贷款明细");

        fragmets.add(new BaseInfoFragment(userBean,DataListActivity.this));
        fragmets.add(new SaveDetailFragment());
        fragmets.add(new LoansDetailFragment());

        tabDataList.setTabMode(TabLayout.MODE_FIXED);
        tabDataList.addTab(tabDataList.newTab().setText(title.get(0)));
        tabDataList.addTab(tabDataList.newTab().setText(title.get(1)));
        tabDataList.addTab(tabDataList.newTab().setText(title.get(2)));
        tabDataList.setupWithViewPager(mvp);
        mvp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmets.get(position);
            }

            @Override
            public int getCount() {
                return fragmets.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }
        });


    }
    @Override
    public DataListPresenter initPresenter() {
        return new DataListPresenter();
    }

    @OnClick({R.id.tv_back, R.id.iv_data_list_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                DataListActivity.this.finish();
                break;
            case R.id.iv_data_list_more:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
