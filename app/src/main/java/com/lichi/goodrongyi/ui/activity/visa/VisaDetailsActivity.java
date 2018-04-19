package com.lichi.goodrongyi.ui.activity.visa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.ExpandableListAdatapter;
import com.lichi.goodrongyi.adapter.ViewPagerAdapter;
import com.lichi.goodrongyi.bean.MorePopupwindowBean;
import com.lichi.goodrongyi.component.SimpleComponent;
import com.lichi.goodrongyi.mvp.model.BillDeleteBean;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.CreditCardDetailsBean;
import com.lichi.goodrongyi.mvp.model.CreditCardMonthBean;
import com.lichi.goodrongyi.mvp.presenter.VisDetailsPresenter;
import com.lichi.goodrongyi.mvp.view.VisaDetailView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.utill.HeadViewPagerTransformer;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.SharedPreferenceUtil;
import com.lichi.goodrongyi.view.MorePopupWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 信用卡详情
 */
public class VisaDetailsActivity extends BaseActivity<VisaDetailView, VisDetailsPresenter> implements View.OnClickListener, VisaDetailView {
    //添加账单
    private static final int ADDBILL = 1;
    private static final int MODIFICATION = 2; //修改信用卡

    private TextView tvAddBill;
    private ViewPager mCreditCardPager;
    private LinearLayout mLLPopup;
    private RelativeLayout mRLLToolBar;

    private ViewPagerAdapter mViewPagerAdapter;
    private List<View> mViews = new ArrayList<View>(); //数据源
    private List<CreditCardBean> cardDataList = new ArrayList<>(); //信用卡数据源
    private ExpandableListView mExpandList;
    private List<CreditCardMonthBean.DataList> group = new ArrayList<>();
    private ImageView imagMore;
    ExpandableListAdatapter mExpandableListAdatapter;
    private boolean mPopupShowed;
    private int page = 1;
    List<CreditCardBean> diagnoseList = new ArrayList<>();
    boolean isComplete = true;
    private int viewPagerPosition = 0;
    private String bankId = "";
    SweetAlertDialog billDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_details);
        mRLLToolBar = (RelativeLayout) findViewById(R.id.rll_toolbar);
        bankId = getIntent().getStringExtra(Constants.IntentParams.ID);
        initData();
        init();
        setAdapter();
        mPresenter.getIndexBillList(page, 9999, IOUtils.getUserId(mContext));
    }

    private void initData() {
        mPopupShowed = false;
    }


    private void init() {
        mCreditCardPager = (ViewPager) findViewById(R.id.ViewPager);
        mExpandList = (ExpandableListView) findViewById(R.id.elv_bill);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_diagnose_add_limit).setOnClickListener(this);
        tvAddBill = (TextView) findViewById(R.id.tv_add_bill);
        tvAddBill.setOnClickListener(this);
        imagMore = (ImageView) findViewById(R.id.iv_visa_detail_more);
        imagMore.setOnClickListener(this);
        mExpandableListAdatapter = new ExpandableListAdatapter(VisaDetailsActivity.this, group, new ExpandableListAdatapter.GroupViewClickListener() {
            @Override
            public void onGroupClick(int groupPosition) {
                List<CreditCardDetailsBean.DataList> child = group.get(groupPosition).child;
                if (child != null && !group.get(groupPosition).isLoad) {
                    mPresenter.getIndexBillDetail(group.get(groupPosition).id, groupPosition);
                } else {
                    boolean groupExpanded = mExpandList.isGroupExpanded(groupPosition);
                    if (groupExpanded) {
                        mExpandList.collapseGroup(groupPosition);
                    } else {
                        mExpandList.expandGroup(groupPosition, true);
                    }
                }
            }

            @Override
            public void onDeleteGroupClick(final int groupPosition) {
                if (TextUtils.isEmpty(cardDataList.get(viewPagerPosition).email)) {

                    new SweetAlertDialog(mContext)
                            .setTitleText("确定删除当前账单")
                            // .setContentText("确定删除当前账单?")
                            .setCancelText("取消")
                            .setConfirmText("确定")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    mPresenter.getDelete(group.get(groupPosition).id, IOUtils.getUserId(mContext));
                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    CommonUtils.showToast(mContext, "该账单由邮箱导入不能删除。");
                }

            }
        });
        mExpandList.setAdapter(mExpandableListAdatapter);
        mExpandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mExpandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                List<CreditCardDetailsBean.DataList> child = group.get(groupPosition).child;
                if (child != null && !group.get(groupPosition).isLoad) {
                    mPresenter.getIndexBillDetail(group.get(groupPosition).id, groupPosition);
                }
                return false;
            }
        });
        mExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return true;
            }
        });
    }

    public void setAdapter() {
        //设置监听，主要是设置当前页码
        mCreditCardPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerPosition = position;
                if (cardDataList.size() != 0 && cardDataList != null) {
                    mPresenter.getBill(cardDataList.get(position).id);
                    if (TextUtils.isEmpty(cardDataList.get(position).email)) {
                        tvAddBill.setVisibility(View.VISIBLE);
                    } else {
                        tvAddBill.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //mCreditCardPager.setCurrentItem(0);
    }

    @Override
    public VisDetailsPresenter initPresenter() {
        return new VisDetailsPresenter();
    }

    public View getViewPagerView(CreditCardBean dataList) {
        View view = View.inflate(mContext, R.layout.mainimage_item, null);
        ImageView itemBankIcon = (ImageView) view.findViewById(R.id.image);
        TextView bankName = (TextView) view.findViewById(R.id.tv_visa_detail_bank_name);
        TextView nameOfTemp = (TextView) view.findViewById(R.id.tv_visa_detail_name);
        TextView totilPrice = (TextView) view.findViewById(R.id.tv_visa_detail_price);
        TextView dateDay = (TextView) view.findViewById(R.id.tv_visa_refund_date_day);
        TextView cardNum = (TextView) view.findViewById(R.id.tv_visa_detail_num);

        String paytime = DateFormatUtil.stringToString(dataList.paymentDueDate, "yyyy-MM-dd", "MM月dd日");
        String statementtime = DateFormatUtil.stringToString(dataList.statementDate, "yyyy-MM-dd", "MM月dd日");
        bankName.setText(dataList.issueBank);
        nameOfTemp.setText(dataList.holderName);
        totilPrice.setText("总额度: " + dataList.creditAmt);
        dateDay.setText("还款日  " + paytime + " 出账日: " + statementtime + " 免息期: " + dataList.freeDay + "天");
        cardNum.setText("****  ****  ****  " + dataList.last4digit);
        BankIconUtils.setBankIcon(itemBankIcon, dataList.issueBank);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                VisaDetailsActivity.this.finish();
                break;
            case R.id.btn_diagnose_add_limit:
                if (cardDataList.size() != 0 && cardDataList != null) {
                    CommonUtils.startNewActivity(mContext, DiagnoseAddLimitActivity.class);
                } else {
                    CommonUtils.showToast(mContext, "请先添加信用卡");
                }

                break;
            case R.id.tv_add_bill:
                if (cardDataList.size() != 0 && cardDataList != null) {
                    Map<String, Serializable> args = new HashMap<>();
                    args.put(Constants.IntentParams.ID, cardDataList.get(viewPagerPosition).id + "");
                    CommonUtils.startResultNewActivity(VisaDetailsActivity.this, args, ManuallyAddBillActivity.class, ADDBILL);
                } else {
                    CommonUtils.showToast(mContext, "请先添加信用卡");
                }
                break;
            case R.id.iv_visa_detail_more:
                showPopupWindow(v);
                break;
        }
    }

    /**
     * 显示PopupWindow
     */
    private void showPopupWindow(View view) {

        List<MorePopupwindowBean> dataList = new ArrayList<>();
        MorePopupwindowBean morePopupwindowBean = null;
        morePopupwindowBean = new MorePopupwindowBean();
        morePopupwindowBean.title = "修改卡信息";
        morePopupwindowBean.id = 1;
        morePopupwindowBean.icon = R.mipmap.mod_edit;
        dataList.add(morePopupwindowBean);

        morePopupwindowBean = new MorePopupwindowBean();
        morePopupwindowBean.title = "删除信用卡";
        morePopupwindowBean.id = 2;
        morePopupwindowBean.icon = R.drawable.icon_delete;
        dataList.add(morePopupwindowBean);

        MorePopupWindow morePopupWindow = new MorePopupWindow(this, dataList,
                new MorePopupWindow.MoreOnClickListener() {
                    @Override
                    public void moreOnClickListener(int position, MorePopupwindowBean item) {
                        switch (item.id) {
                            case 1:
                                if (cardDataList != null && cardDataList.size() != 0) {
                                    if (TextUtils.isEmpty(cardDataList.get(viewPagerPosition).email)) {
                                        Map<String, Serializable> args = new HashMap<>();
                                        args.put(Constants.IntentParams.ID, cardDataList.get(viewPagerPosition));
                                        CommonUtils.startResultNewActivity(VisaDetailsActivity.this, args, ModifyVisaActivity.class, MODIFICATION);
                                    } else {
                                        CommonUtils.showToast(mContext, "为确保数据安全性，自动导入账单无法修改");
                                    }

                                } else {
                                    CommonUtils.showToast(mContext, "请先添加信用卡");
                                }
                                break;
                            case 2:
                                new SweetAlertDialog(mContext)
                                        .setTitleText("确定删除该信用卡?")
                                        //.setContentText("确定删除?")
                                        .setCancelText("取消")
                                        .setConfirmText("确定")
                                        .showCancelButton(true)
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismiss();
                                            }
                                        })
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                if (cardDataList != null && cardDataList.size() != 0) {
                                                    mPresenter.getCreditDelete(cardDataList.get(viewPagerPosition).id);
                                                } else {
                                                    CommonUtils.showToast(mContext, "请先添加信用卡");
                                                }
                                                sDialog.dismiss();
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                });
        morePopupWindow.showPopupWindow(view);

    }

    @Override
    public void dataBillSucceed(List<CreditCardBean> indexCreditCardBean) {
        cardDataList.clear();
        mViews.clear();
        cardDataList.addAll(indexCreditCardBean);
        for (int i = 0; i < cardDataList.size(); i++) {
            mViews.add(getViewPagerView(cardDataList.get(i)));
        }
        mViewPagerAdapter = new ViewPagerAdapter(mViews);
        mCreditCardPager.setAdapter(mViewPagerAdapter);
        mCreditCardPager.setPageTransformer(true, new HeadViewPagerTransformer());
        //mCreditCardPager.setPageTransformer(true, new GalleryPageTransformer());
        mCreditCardPager.setOffscreenPageLimit(4);
        mViewPagerAdapter.notifyDataSetChanged();

        if (TextUtils.isEmpty(bankId)) {
            if (cardDataList.size() != 0 && cardDataList != null) {
                if (viewPagerPosition == 0) {
                    viewPagerPosition = 0;
                } else {
                    viewPagerPosition = viewPagerPosition - 1;
                }
                mCreditCardPager.setCurrentItem(viewPagerPosition);
                mPresenter.getBill(cardDataList.get(viewPagerPosition).id);
                //mPresenter.getBill(IOUtils.getUserId(mContext), cardDataList.get(viewPagerPosition).issueBank, cardDataList.get(viewPagerPosition).holderName, cardDataList.get(viewPagerPosition).last4digit);
                if (TextUtils.isEmpty(cardDataList.get(viewPagerPosition).email)) {
                    tvAddBill.setVisibility(View.VISIBLE);
                } else {
                    tvAddBill.setVisibility(View.GONE);
                }
            } else {
                tvAddBill.setVisibility(View.GONE);
                group.clear();
                mExpandableListAdatapter.notifyDataSetChanged();
            }
        } else {
            if (cardDataList.size() != 0 && cardDataList != null) {
                for (int i = 0; i < cardDataList.size(); i++) {
                    if (bankId.equals(cardDataList.get(i).id)) {
                        viewPagerPosition = i;
                        mCreditCardPager.setCurrentItem(i);
                        mPresenter.getBill(cardDataList.get(viewPagerPosition).id);
                        //  mPresenter.getBill(IOUtils.getUserId(mContext), cardDataList.get(i).issueBank, cardDataList.get(i).holderName, cardDataList.get(i).last4digit);
                        if (TextUtils.isEmpty(cardDataList.get(i).email)) {
                            tvAddBill.setVisibility(View.VISIBLE);
                        } else {
                            tvAddBill.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void dataBillSucceed1(List<CreditCardBean> indexCreditCardBean) {
        cardDataList.clear();
        mViews.clear();
        cardDataList.addAll(indexCreditCardBean);
        for (int i = 0; i < cardDataList.size(); i++) {
            mViews.add(getViewPagerView(cardDataList.get(i)));
        }
        mViewPagerAdapter = new ViewPagerAdapter(mViews);
        mCreditCardPager.setAdapter(mViewPagerAdapter);
        mCreditCardPager.setPageTransformer(true, new HeadViewPagerTransformer());
        //mCreditCardPager.setPageTransformer(true, new GalleryPageTransformer());
        mCreditCardPager.setOffscreenPageLimit(4);
        mViewPagerAdapter.notifyDataSetChanged();
        if (cardDataList.size() != 0 && cardDataList != null) {
            mCreditCardPager.setCurrentItem(viewPagerPosition);
            mPresenter.getBill(cardDataList.get(viewPagerPosition).id);
            //  mPresenter.getBill(IOUtils.getUserId(mContext), cardDataList.get(viewPagerPosition).issueBank, cardDataList.get(viewPagerPosition).holderName, cardDataList.get(viewPagerPosition).last4digit);
            if (TextUtils.isEmpty(cardDataList.get(viewPagerPosition).email)) {
                tvAddBill.setVisibility(View.VISIBLE);
            } else {
                tvAddBill.setVisibility(View.GONE);
            }
        } else {
            tvAddBill.setVisibility(View.GONE);
            group.clear();
            mExpandableListAdatapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void dataBillDetailsSucceed(CreditCardDetailsBean creditCardDetailsBean, int groupPosition) {
        group.get(groupPosition).child.clear();
        group.get(groupPosition).child.addAll(creditCardDetailsBean.list);
        mExpandableListAdatapter.notifyDataSetChanged();
        group.get(groupPosition).isLoad = true;
        boolean groupExpanded = mExpandList.isGroupExpanded(groupPosition);
        if (groupExpanded) {
            // mExpandList.expandGroup(groupPosition, false);
            mExpandList.collapseGroup(groupPosition);
        } else {
            mExpandList.expandGroup(groupPosition, true);
        }
    }

    @Override
    public void dataBillMonthSucceed(CreditCardMonthBean creditCardMonthBean) {
        group.clear();
        group.addAll(creditCardMonthBean.list);
        mExpandableListAdatapter.notifyDataSetChanged();
        if (!SharedPreferenceUtil.getFirstCreditCard(mContext)) {
            imagMore.post(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            showGuideView();
                            SharedPreferenceUtil.putFirstCreditCard(mContext);
                        }
                    }, 200);

                }
            });
        }

    }

    @Override
    public void dataBillDeleteSucceed(BillDeleteBean billDeleteBean) {
/*
        billDelete.setTitleText("Deleted!")
                .setContentText("Your imaginary file has been deleted!")
                .setConfirmText("OK")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);*/

        if (cardDataList.size() != 0 && cardDataList != null) {
            mPresenter.getBill(cardDataList.get(viewPagerPosition).id);
            // mPresenter.getBill(IOUtils.getUserId(mContext), cardDataList.get(viewPagerPosition).issueBank, cardDataList.get(viewPagerPosition).holderName, cardDataList.get(viewPagerPosition).last4digit);
        }
    }

    @Override
    public void datagetCreditDeleteSucceed(String billDeleteBean) {
        bankId = "";
        mPresenter.getIndexBillList(page, 9999, IOUtils.getUserId(mContext));
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
    public Context getContext() {
        return mContext;
    }

    private Map<String, String> builderParams(CreditCardBean dataList) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", dataList.id);
        params.put("userId", IOUtils.getUserId(mContext));
        params.put("balanceRmb", dataList.balanceRmb);
        params.put("issueBank", dataList.issueBank);
        params.put("holderName", dataList.holderName);
        params.put("last4digit", dataList.last4digit);
        params.put("paymentDueDate", dataList.paymentDueDate);
        params.put("statementDate", dataList.statementDate);
        params.put("freeDay", dataList.freeDay + "");
        params.put("statementEndDate", dataList.statementEndDate);
        params.put("statementStartDate", dataList.statementStartDate);
        params.put("minPaymentRmb", dataList.minPaymentRmb);
        params.put("availablePoints", dataList.availablePoints);
        params.put("creditAmt", dataList.creditAmt);
        params.put("cashLimitAmt", dataList.cashLimitAmt);
        params.put("payDay", dataList.payDay + "");
        params.put("billDay", dataList.billDay + "");
        return params;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //修改了账单
        if (requestCode == ADDBILL && resultCode == Activity.RESULT_OK) {
            if (cardDataList.size() != 0 && cardDataList != null) {
                mPresenter.getBill(cardDataList.get(viewPagerPosition).id);
                //mPresenter.getBill(IOUtils.getUserId(mContext), cardDataList.get(viewPagerPosition).issueBank, cardDataList.get(viewPagerPosition).holderName, cardDataList.get(viewPagerPosition).last4digit);
            }
        }

        //修改了信用卡
        if (requestCode == MODIFICATION && resultCode == Activity.RESULT_OK) {
            mPresenter.getIndexBillList1(page, 9999, IOUtils.getUserId(mContext));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    Guide guide;


    public void showGuideView() {
        final GuideBuilder builder1 = new GuideBuilder();
        builder1.setTargetView(imagMore)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder1.addComponent(new SimpleComponent("久等的信用卡修改和删除上线了"));
        guide = builder1.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(VisaDetailsActivity.this);
    }
}
