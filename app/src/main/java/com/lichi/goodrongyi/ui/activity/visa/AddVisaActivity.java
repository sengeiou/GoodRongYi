package com.lichi.goodrongyi.ui.activity.visa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.mvp.presenter.MainPresenter;
import com.lichi.goodrongyi.mvp.view.IMainView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.view.ContentWithSpaceEditText;
import com.lichi.goodrongyi.utill.BankInfoUtil;

import java.util.ArrayList;
import java.util.List;

public class AddVisaActivity extends BaseActivity<IMainView, MainPresenter> implements View.OnClickListener {

    private TextView visaType, billDate, refondDate, mDate;
    private EditText visaName, visaLimit;
    private ContentWithSpaceEditText visaNumber;
    private PopupWindow mPopupWindow;
    private ListView mListView;
    private List<String> listDate = new ArrayList<>();
    private String date;
    private int popuType = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visa);
        initData();
        initView();

    }

    private void initData() {
        for (int i = 1; i <= 31; i++) {
            listDate.add(i + "日");
        }
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    private void initView() {
        View view = getLayoutInflater().inflate(R.layout.popu_bottom, null);
        mDate = (TextView) view.findViewById(R.id.tv_add_date_type);
        mListView = (ListView) view.findViewById(R.id.lv_add_date);
        mListView.setAdapter(new WrapAdapter<String>(this, listDate, R.layout.popu_item) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                TextView textView = helper.getView(R.id.tv_popu);
                textView.setText(item);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                date = listDate.get(position);
                if (popuType == 1) {
                    refondDate.setText(date);
                } else if (popuType == 0) {
                    billDate.setText(date);
                }
            }
        });

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.ll_add_bill_date).setOnClickListener(this);
        findViewById(R.id.ll_add_visa_refund_date).setOnClickListener(this);
        findViewById(R.id.tv_add_visa_back).setOnClickListener(this);
        findViewById(R.id.btn_add_visa_save).setOnClickListener(this);
        findViewById(R.id.ll_add_visa_type).setOnClickListener(this);

        visaType = (TextView) findViewById(R.id.tv_add_visa_type);
        billDate = (TextView) findViewById(R.id.tv_add_visa_bill_date);
        refondDate = (TextView) findViewById(R.id.tv_add_visa_refund_date);


        visaNumber = (ContentWithSpaceEditText) findViewById(R.id.et_add_visa_number);
        visaName = (EditText) findViewById(R.id.et_add_visa_name);
        visaLimit = (EditText) findViewById(R.id.et_add_visa_limit);

        visaNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                visaType.setText("请输入银行卡号");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cardNum = visaNumber.getText().toString().replace(" ", "");
                if (!TextUtils.isEmpty(cardNum) && checkBankCard(cardNum)) {
                    BankInfoUtil mInfoUtil = new BankInfoUtil(cardNum);
                    visaType.setText(mInfoUtil.getBankName());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x1222){
            String type = data.getStringExtra("type");
            visaType.setText(type);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_add_bill_date:
                mPopupWindow.showAsDropDown(v, this.getWindowManager().getDefaultDisplay().getHeight(), this.getWindowManager().getDefaultDisplay().getWidth() - 500);
                mDate.setText("账单日");
                popuType = 0;
                break;
            case R.id.ll_add_visa_refund_date:
                mPopupWindow.showAsDropDown(v, this.getWindowManager().getDefaultDisplay().getHeight(), this.getWindowManager().getDefaultDisplay().getWidth() - 600);
                mDate.setText("还款日");
                popuType = 1;
                break;
            case R.id.tv_add_visa_back:
                AddVisaActivity.this.finish();
                break;
            case R.id.btn_add_visa_save:
                break;
            case R.id.ll_add_visa_type:
                startActivityForResult(new Intent(AddVisaActivity.this,SelectBankActivity.class),0x1123);
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                //mPopupWindow.showAtLocation(findViewById(R.id.layout_main), Gravity.BOTTOM, 0, 0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
