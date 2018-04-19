package com.lichi.goodrongyi.ui.activity.visa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.BankBean;
import com.lichi.goodrongyi.mvp.model.CreditCardBean;
import com.lichi.goodrongyi.mvp.model.DataItem;
import com.lichi.goodrongyi.mvp.model.ModificationMaillboxBean;
import com.lichi.goodrongyi.mvp.presenter.ImportBillPresenter;
import com.lichi.goodrongyi.mvp.presenter.ModificationBillPresenter;
import com.lichi.goodrongyi.mvp.view.ImportBillView;
import com.lichi.goodrongyi.mvp.view.ModificationBillView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.DateFormatUtil;
import com.lichi.goodrongyi.view.popwindow.DropUpDatePopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 手动添加账单
 */
public class ModifyVisaActivity extends BaseActivity<ModificationBillView, ModificationBillPresenter> implements View.OnClickListener, ModificationBillView {
    //选择银行
    private static final int BANK = 1;

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.et_visa_number)
    TextView mEtVisaNumber;
    @BindView(R.id.tv_visa_type)
    TextView mTvVisaType;
    @BindView(R.id.ll_visa_type)
    LinearLayout mLlVisaType;
    @BindView(R.id.et_owner_name)
    TextView mEtOwnerName;
    @BindView(R.id.tv_visa_bill_date)
    TextView mTvVisaBillDate;
    @BindView(R.id.ll_bill_date)
    LinearLayout mLlBillDate;
    @BindView(R.id.tv_visa_refund_date)
    TextView mTvVisaRefundDate;
    @BindView(R.id.ll_visa_refund_date)
    LinearLayout mLlVisaRefundDate;
    @BindView(R.id.et_visa_limit)
    EditText mEtVisaLimit;
    @BindView(R.id.invoice_amount)
    EditText mInvoiceAmount;
    @BindView(R.id.btn_visa_save)
    Button mBtnVisaSave;

    private DropUpDatePopupWindow mDropUpDatePopupWindow;
    private List<DataItem> mDataList = new ArrayList<>();

    private String billDate = ""; //账单日
    private String dueDate = "";  //还款日
    private String bankName = "";//银行

    private CreditCardBean creditCardBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_creditcard);
        ButterKnife.bind(this);
        creditCardBean = (CreditCardBean) getIntent().getSerializableExtra(Constants.IntentParams.ID);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        //  findViewById(R.id.ll_visa_type).setOnClickListener(this);
        // findViewById(R.id.ll_bill_date).setOnClickListener(this);
        //findViewById(R.id.ll_visa_refund_date).setOnClickListener(this);
        findViewById(R.id.btn_visa_save).setOnClickListener(this);
        initData();
        initView();
    }

    @Override
    public ModificationBillPresenter initPresenter() {
        return new ModificationBillPresenter();
    }

    private void initView() {
        //输入过滤器可以实现很多效果
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        mEtOwnerName.setFilters(new InputFilter[]{filter});
    }

    /**
     * 判定输入汉字，通过Unicode表
     *
     * @param c
     * @return
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    private void initData() {
        mDataList.clear();
        for (int i = 1; i <= 31; i++) {
            DataItem dataItem = new DataItem();
            dataItem.name = i + "";
            mDataList.add(dataItem);
        }

        mEtVisaNumber.setText(creditCardBean.last4digit);
        bankName = creditCardBean.issueBank;
        mTvVisaType.setText(bankName);
        mEtOwnerName.setText(creditCardBean.holderName);
        billDate = DateFormatUtil.stringToString(creditCardBean.statementDate, "yyyy-MM-dd", "yyyy-MM-dd");
        String stateme = DateFormatUtil.stringToString(creditCardBean.statementDate, "yyyy-MM-dd", "dd日");
        mTvVisaBillDate.setText(stateme);

        dueDate = DateFormatUtil.stringToString(creditCardBean.paymentDueDate, "yyyy-MM-dd", "yyyy-MM-dd");
        String paytime = DateFormatUtil.stringToString(creditCardBean.paymentDueDate, "yyyy-MM-dd", "dd日");

        mTvVisaRefundDate.setText(paytime);
        mEtVisaLimit.setText(creditCardBean.creditAmt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                ModifyVisaActivity.this.finish();
                break;
            case R.id.ll_visa_type:
                CommonUtils.startResultNewActivity(ModifyVisaActivity.this, null, SelectBankActivity.class, BANK);
                break;
            case R.id.ll_bill_date:
                ShowBill();
                break;
            case R.id.ll_visa_refund_date:
                ShowRefund();
                break;
            case R.id.btn_visa_save:
                if (TextUtils.isEmpty(mEtVisaNumber.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入银行后四位");
                    return;
                }
                if (TextUtils.isEmpty(mEtOwnerName.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入持卡人姓名");
                    return;
                }
                if (TextUtils.isEmpty(mEtVisaLimit.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输信用额度");
                    return;
                }
/*
                if (TextUtils.isEmpty(mInvoiceAmount.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入账单金额");
                    return;
                }
*/

                if (TextUtils.isEmpty(billDate)) {
                    CommonUtils.showToast(mContext, "请选择账单日");
                    return;
                }
                if (TextUtils.isEmpty(dueDate)) {
                    CommonUtils.showToast(mContext, "请选择还款日");
                    return;
                }
                mPresenter.getBillDataAdd(builderParams());
                break;
        }
    }

    private Map<String, String> builderParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", creditCardBean.id);
        params.put("last4digit", mEtVisaNumber.getText().toString().trim());
        params.put("issueBank", bankName);
        params.put("holderName", mEtOwnerName.getText().toString().trim());
        params.put("paymentDueDate", dueDate);
        params.put("statementDate", billDate);
        params.put("creditAmt", mEtVisaLimit.getText().toString().trim());
        params.put("balanceRmb", mInvoiceAmount.getText().toString().trim());
        return params;
    }

    private void ShowBill() {// 账单

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String date = sdf.format(new java.util.Date());
                billDate = date + "-" + mDataList.get(options1).name;
                mTvVisaBillDate.setText(mDataList.get(options1).name + "日");
            }
        })
                .setTitleText("账单日")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .setTitleSize(16)//标题文字大小
                .build();

        pvOptions.setPicker(mDataList);//一级选择器
       /* pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void ShowRefund() {//还款

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String date = sdf.format(new java.util.Date());
                dueDate = date + "-" + mDataList.get(options1).name;
                mTvVisaRefundDate.setText(mDataList.get(options1).name + "日");
            }
        })
                .setTitleText("还款日")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .setTitleSize(16)//标题文字大小
                .build();

        pvOptions.setPicker(mDataList);//一级选择器
       /* pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BANK && resultCode == Activity.RESULT_OK && data != null) {
            BankBean bankBean = data.getParcelableExtra(Constants.IntentParams.DATA);
            bankName = bankBean.bankname;
            mTvVisaType.setText(bankName);
        }
    }

    @Override
    public void dataListSucceed(String maillboxListBean) {
        CommonUtils.showToast(mContext, "修改成功");
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
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
