package com.lichi.goodrongyi.ui.activity.visa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.AddMaillboxBean;
import com.lichi.goodrongyi.mvp.model.DataItem;
import com.lichi.goodrongyi.mvp.presenter.ManuallyAddBillPresenter;
import com.lichi.goodrongyi.mvp.view.ManuallyAddBillView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.utill.DateFormatUtil;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManuallyAddBillActivity extends BaseActivity<ManuallyAddBillView, ManuallyAddBillPresenter> implements View.OnClickListener, ManuallyAddBillView {
    TextView timeText;
    EditText money;

    private String mTime = "";
    private String ID = "";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_add_bill);
        ID = getIntent().getStringExtra(Constants.IntentParams.ID);
        timeText = (TextView) findViewById(R.id.tv_consumption_date);
        money = (EditText) findViewById(R.id.money);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.ll_consumption_date).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    public ManuallyAddBillPresenter initPresenter() {
        return new ManuallyAddBillPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                ManuallyAddBillActivity.this.finish();
                break;
            case R.id.ll_consumption_date:
                Calendar calendar = Calendar.getInstance();
 /*               TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        //tvTime.setText(getTime(date));
                        String time = DateFormatUtil.dateToString(date, "yyyy-MM");
                        mTime = time;
                        timeText.setText(time);

                    }
                })
                        .setTitleText("日期")//标题文字
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentSize(18)
                        .setTitleSize(16)//标题文字大小
                        .setLayoutRes(R.layout.pickerview_time_ym)
                        .setRange(calendar.get(Calendar.YEAR) - 2, calendar.get(Calendar.YEAR) + 1)//默认是1900-2100年
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .build();
                pvTime.show();*/
                final List<String> mDataList = new ArrayList<>();
                final List<String> mMonthList = new ArrayList<>();
                final List<List<String>> mMonthList2 = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    mMonthList.add(i + 1 + "月");
                }

                int year = calendar.get(Calendar.YEAR);
                for (int i = 0; i < 4; i++) {
                    mDataList.add(year - (2 - i) + "年");
                    mMonthList2.add(mMonthList);
                }
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String times = mDataList.get(options1) + mMonthList.get(options2);
                        Date date = DateFormatUtil.strToDate(times, "yyyy年MM月");
                        String time = DateFormatUtil.dateToString(date, "yyyy-MM");
                        mTime = time;
                        timeText.setText(times);
                    }
                })
                        .setTitleText("日期")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(18)
                        .setTitleSize(16)//标题文字大小
                        .build();

                //pvOptions.setPicker(mDataList);//一级选择器
                pvOptions.setPicker(mDataList, mMonthList2);//二级选择器
                //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(money.getText().toString().trim())) {
                    CommonUtils.showToast(mContext, "请输入还款金额");
                    return;
                }
                if (TextUtils.isEmpty(mTime)) {
                    CommonUtils.showToast(mContext, "请选择日期");
                    return;
                }
                mPresenter.getAddBillData(builderParams());
                break;
        }
    }

    private Map<String, String> builderParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("money", money.getText().toString().trim());
        params.put("month", mTime);
        params.put("id", ID);
        return params;
    }

    @Override
    public void dataListSucceed(AddMaillboxBean maillboxListBean) {
        CommonUtils.showToast(mContext, "保存成功");
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
