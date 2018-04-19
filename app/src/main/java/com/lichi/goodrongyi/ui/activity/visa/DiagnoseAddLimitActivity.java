package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.ResultDiagnoseBean;
import com.lichi.goodrongyi.mvp.presenter.DiagnosePresenter;
import com.lichi.goodrongyi.mvp.view.DiagnoseView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.BankIconUtils;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.IOUtils;
import com.lichi.goodrongyi.utill.JudgeInstall;
import com.lichi.goodrongyi.view.CustomCircleProgress;
import com.lichi.goodrongyi.view.MyListView;
import com.lichi.goodrongyi.view.radarscan.RadarScanView;
import com.lichi.goodrongyi.view.verticarolling.DataSetAdapter;
import com.lichi.goodrongyi.view.verticarolling.VerticalRollingTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnoseAddLimitActivity extends BaseActivity<DiagnoseView, DiagnosePresenter> implements View.OnClickListener, DiagnoseView {
    boolean isAchieve = false; //是否诊断

    private MyListView mRlLimit;
    private TextView complete;
    private WrapAdapter<ResultDiagnoseBean> mAdapter;
    public static final int PROGRESS_CIRCLE_STARTING = 0x110;
    public static final int END = 10014; //诊断结束
    public CreditCardBean dataVisaBean;
    public CustomCircleProgress mIvState = null;
    public int selectPosition = -1;
    private List<String> strs = new ArrayList<String>();
    private VerticalRollingTextView mVerticalRollingTextView;
    private RadarScanView mRadarScanView;

    List<ResultDiagnoseBean> ResultdiagnoseBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_add_limit);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_add_credit_card).setOnClickListener(this);
        findViewById(R.id.accomplish).setOnClickListener(this);
        initView();
        initData();
        setAdapter();
        mPresenter.getResultDiagnosis(IOUtils.getUserId(mContext));
    }

    public void initView() {
        mVerticalRollingTextView = (VerticalRollingTextView) findViewById(R.id.verticalRollingView);
        mRadarScanView = (RadarScanView) findViewById(R.id.radaeScan);
        complete = (TextView) findViewById(R.id.complete);
        mRlLimit = (MyListView) findViewById(R.id.rec_limit_list);
    }

    public void setAdapter() {
        mAdapter = new WrapAdapter<ResultDiagnoseBean>(this, R.layout.credit_card_limit_item, ResultdiagnoseBeans) {
            @Override
            public void convert(ViewHolder holder, final ResultDiagnoseBean item, final int position) {
                ImageView ivCver = holder.getView(R.id.iv_over); //完成
                LinearLayout insideLayout = holder.getView(R.id.insidelayout); //加载完成后显示
                TextView money = holder.getView(R.id.money); //可以提额到多少
                TextView increase = holder.getView(R.id.increase); //马上提额
                TextView result = holder.getView(R.id.result); //诊断接口

                LinearLayout awaitLayout = holder.getView(R.id.awaitlayout); //等待布局
                ProgressBar progressBar = holder.getView(R.id.progressBar); //进度条
                TextView tvWaitLabel = holder.getView(R.id.tv_wait_label); //等待中

                TextView name = holder.getView(R.id.name); //名字额度
                TextView bankName = holder.getView(R.id.bank_name); //银行名字
                ImageView itemBankIcon = holder.getView(R.id.iv_bank_icon); //银行

                LinearLayout gradedlayout = holder.getView(R.id.gradedlayout); //评分布局
                TextView graded = holder.getView(R.id.graded); //评分多少
                ImageView arrowsAcross = holder.getView(R.id.arrows_across); //评分箭头

                graded.setText(item.score);
                result.setText(item.result);
                money.setText(item.money);
                bankName.setText(item.issueBank);
                name.setText(item.holderName + "   ***" + item.last4digit);

                if (isAchieve) {
                 //   tvWaitLabel.setText("诊断完成");
                    ivCver.setVisibility(View.VISIBLE);
                    gradedlayout.setVisibility(View.VISIBLE);
                    awaitLayout.setVisibility(View.GONE);
                    //   progressBar.setVisibility(View.GONE);
                } else {
                   // tvWaitLabel.setText("等待中");
                    ivCver.setVisibility(View.GONE);
                    gradedlayout.setVisibility(View.GONE);
                    awaitLayout.setVisibility(View.VISIBLE);
                    // progressBar.setVisibility(View.VISIBLE);
                }

                if (item.isUnfold) {
                    insideLayout.setVisibility(View.VISIBLE);
                    arrowsAcross.setImageResource(R.drawable.arrows_down);
                } else {
                    insideLayout.setVisibility(View.GONE);
                    arrowsAcross.setImageResource(R.drawable.arrows_across);
                }
                BankIconUtils.setBankIcon(itemBankIcon, item.issueBank);

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isAchieve) {
                            if (item.isUnfold) {
                                item.isUnfold = false;
                            } else {
                                for (int i = 0; i < ResultdiagnoseBeans.size(); i++) {
                                    if (i == position) {
                                        ResultdiagnoseBeans.get(i).isUnfold = true;
                                    } else {
                                        ResultdiagnoseBeans.get(i).isUnfold = false;
                                    }
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });

                increase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // CommonUtils.showToast(mContext, "马上提额");
                        Map<String, Serializable> args = new HashMap<>();
                        args.put(Constants.IntentParams.ID2, item.issueBank);
                        args.put(Constants.IntentParams.ID3, item.url);
                        CommonUtils.startNewActivity(mContext, args, HtmlActivity.class);
                    }
                });

            }
        };
        mRlLimit.setAdapter(mAdapter);
    }

    public void setVerticaAdapter(List<String> strs) {
        DataSetAdapter mDataSetAdapter = new DataSetAdapter<String>(strs) {
            @Override
            protected String text(String s) {
                return s;
            }
        };
        mVerticalRollingTextView.setDataSetAdapter(mDataSetAdapter);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_CIRCLE_STARTING:
                    if (dataVisaBean != null && mIvState != null) {
                        if (dataVisaBean.progress == 0) {
                        } else {
                        }
                        dataVisaBean.progress = mIvState.getProgress();
                        mIvState.setProgress(dataVisaBean.progress + 4);
                        if (dataVisaBean.progress >= 100) {
                            handler.removeMessages(PROGRESS_CIRCLE_STARTING);
                            dataVisaBean.progress = 100;
                            dataVisaBean.isAchieve = true;
                            dataVisaBean.isBeing = false;
                            //mIvState.setProgress(0);
                            mIvState.setStatus(CustomCircleProgress.Status.End);//修改显示状态为完成
                            mAdapter.notifyDataSetChanged();
                        } else {
                            //延迟100ms后继续发消息，实现循环，直到progress=100
                            handler.sendEmptyMessageDelayed(PROGRESS_CIRCLE_STARTING, 100);
                        }
                    }
                    break;
                case END:
                    mRadarScanView.Start(mContext, false);
                    mVerticalRollingTextView.stop();
                    mVerticalRollingTextView.setVisibility(View.GONE);
                    complete.setVisibility(View.VISIBLE);
                    isAchieve = true;
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

  /*  public void setHandler(final CustomCircleProgress ivState, int position) {
        selectPosition = position + 1;
        dataVisaBean = diagnoseList.get(position);
        mIvState = ivState;
        if (ivState.getStatus() == CustomCircleProgress.Status.Starting) {//如果是开始状态
            mRadarScanView.Start(this, false);
            mVerticalRollingTextView.stop();
            //点击则变成关闭暂停状态
            ivState.setStatus(CustomCircleProgress.Status.End);
            //注意，当我们暂停时，同时还要移除消息，不然的话进度不会被停止
            handler.removeMessages(PROGRESS_CIRCLE_STARTING);
            dataVisaBean.isBeing = false;
        } else {
            dataVisaBean.isBeing = true;
            //点击则变成开启状态
            ivState.setStatus(CustomCircleProgress.Status.Starting);
            Message message = Message.obtain();
            message.what = PROGRESS_CIRCLE_STARTING;
            handler.sendMessage(message);

        }
    }
*/
    @Override
    public DiagnosePresenter initPresenter() {
        return new DiagnosePresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                DiagnoseAddLimitActivity.this.finish();
                break;
            case R.id.btn_add_credit_card:
                startActivity(new Intent(getContext(), AddBillActivity.class));
                //startActivity(new Intent(this, AddVisaActivity.class));
                break;
            case R.id.accomplish:
                //CommonUtils.showToast(mContext, "完成诊断");
                mPresenter.getCustomer(3);
                //startActivity(new Intent(this, AddVisaActivity.class));
                break;
        }
    }

    private void initData() {

    }

    @Override
    public void RadarListSucceed(List<String> stringList) {
        strs.clear();
        strs.addAll(stringList);
        strs.remove(stringList.size() - 1);
        DataSetAdapter mDataSetAdapter = new DataSetAdapter<String>(strs) {
            @Override
            protected String text(String s) {
                return s;
            }
        };
        mVerticalRollingTextView.setDataSetAdapter(mDataSetAdapter);
        mAdapter.notifyDataSetChanged();
        mVerticalRollingTextView.run();
        setLoadingIndicator(false);
        mRadarScanView.Start(mContext, true);
        //延迟
        handler.sendEmptyMessageDelayed(END, 5000);
    }

    @Override
    public void CustomerSucceed(CustomerBean bean) {
        if (!TextUtils.isEmpty(bean.qq)) {
            if (JudgeInstall.isQQClientAvailable(mContext)) {
                String urlview = "mqqwpa://im/chat?chat_type=wpa&uin=" + bean.qq;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlview)));
            } else {
                CommonUtils.showToast(mContext, "请先安装QQ或IM");
            }
        } else {
            CommonUtils.showToast(mContext, "暂无客服，请稍等");
        }
/*        if (!TextUtils.isEmpty(bean.userId)) {
            Intent intent = new Intent(mContext, CustomerServiceActivity.class);
            intent.putExtra(EaseConstant.IS_SEND, false);
            intent.putExtra(EaseConstant.EXTRA_USER_ID, bean.userId);
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
            startActivity(intent);

        } else {
            CommonUtils.showToast(mContext, "暂无客服，请稍等");
        }*/
    }


    @Override
    public void dataResultDiagnoseBean(List<ResultDiagnoseBean> diagnoseBean) {
        ResultdiagnoseBeans.clear();
        ResultdiagnoseBeans.addAll(diagnoseBean);
        if (ResultdiagnoseBeans.size() != 0) {
            mPresenter.getRadarList();
        } else {
            CommonUtils.showToast(mContext, "请先添加信用卡");
            finish();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
