package com.lichi.goodrongyi.ui.activity.my;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.UserCardBean;
import com.lichi.goodrongyi.mvp.model.UserCardNumBean;
import com.lichi.goodrongyi.mvp.presenter.SlotCardTaskPresenter;
import com.lichi.goodrongyi.mvp.view.SlotCardTaskView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.ui.fragment.my.slotCardTask.CompletedTaskFragment;
import com.lichi.goodrongyi.ui.fragment.my.slotCardTask.UnCompletedTaskFragment;
import com.lichi.goodrongyi.ui.fragment.my.slotCardTask.WholeTaskFragment;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.IOUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SlotCardTaskActivity extends BaseActivity<SlotCardTaskView, SlotCardTaskPresenter> implements SlotCardTaskView {

    @BindView(R.id.slot_card_task_tab)            // 绑定顶部的tab页控件
            TabLayout mTabLayout;
    @BindView(R.id.slot_card_task_viewpager)
    ViewPager mViewPager;
    TextView tab1Num, tab2Num, tab3Num;

    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    private List<Fragment> mFragments;
    private List<String> mTabTitle;
    private List<String> mTabTaskNum;
    private List<Integer> mTabNumColor;
    private String id = IOUtils.getUserId(SlotCardTaskActivity.this);

    @Override
    public SlotCardTaskPresenter initPresenter() {
        return new SlotCardTaskPresenter();
    }


    public void gainTaskCount(){
        mPresenter.getUserCardTaskCount(id);
        if (mFragments.size()>=2){
            CompletedTaskFragment completedTaskFragment = (CompletedTaskFragment) mFragments.get(1);
            completedTaskFragment.loadData1();
        }
    }

    private void initData() {

        mFragments = new ArrayList<>();
        mTabTitle = new ArrayList<>();
        mTabTaskNum = new ArrayList<>();
        mTabNumColor = new ArrayList<>();
        mFragments.clear();
        mTabTitle.clear();
        mTabTaskNum.clear();
        mTabTaskNum.clear();
        gainTaskCount();
        //初始化tab栏的标题
        mTabTitle.add("消费任务");
        mTabTitle.add("已完成");
        mTabTitle.add("未完成");
        //
        mFragments.add(new WholeTaskFragment());
        mFragments.add(new CompletedTaskFragment());
        mFragments.add(new UnCompletedTaskFragment());
        //初始化tab栏显示的任务数量
       /* mTabTaskNum.add("255");
        mTabTaskNum.add("825");
        mTabTaskNum.add("738");*/
        //初始化tab栏显示任务数量字体的颜色
        mTabNumColor.add(Color.BLACK);
        mTabNumColor.add(Color.GREEN);
        mTabNumColor.add(Color.RED);


    }


    private void initView() {
        //设置顶部tab页的内容(使用自定义的item)
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        //加入定制的tab栏的布局
        TabLayout.Tab tab1 = mTabLayout.getTabAt(0);
        tab1.setCustomView(R.layout.slot_card_task_tab_item);
        tab1Num = (TextView) tab1.getCustomView().findViewById(R.id.slot_card_task_tab_num);
        TextView tab1Text = (TextView) tab1.getCustomView().findViewById(R.id.slot_card_task_tab_text);
        tab1Num.setTextColor(mTabNumColor.get(0));
        tab1Text.setText(mTabTitle.get(0));

        TabLayout.Tab tab2 = mTabLayout.getTabAt(1);
        tab2.setCustomView(R.layout.slot_card_task_tab_item);
        tab2Num = (TextView) tab2.getCustomView().findViewById(R.id.slot_card_task_tab_num);
        TextView tab2Text = (TextView) tab2.getCustomView().findViewById(R.id.slot_card_task_tab_text);
        tab2Num.setTextColor(mTabNumColor.get(1));
        tab2Text.setText(mTabTitle.get(1));

        TabLayout.Tab tab3 = mTabLayout.getTabAt(2);
        tab3.setCustomView(R.layout.slot_card_task_tab_item);
        tab3Num = (TextView) tab3.getCustomView().findViewById(R.id.slot_card_task_tab_num);
        TextView tab3Text = (TextView) tab3.getCustomView().findViewById(R.id.slot_card_task_tab_text);
        tab3Num.setTextColor(mTabNumColor.get(2));
        tab3Text.setText(mTabTitle.get(2));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_card_task);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void dataSuccee(UserCardBean bean) {

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
        tab1Num.setText(bean.total + "");
        tab2Num.setText(bean.done + "");
        tab3Num.setText(bean.undone + "");
    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(SlotCardTaskActivity.this, msg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showLoadingIndicator(active);

    }

    @Override
    public void showLoadingTasksError() {
        CommonUtils.showToast(SlotCardTaskActivity.this, R.string.app_abnormal);

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
