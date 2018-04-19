package com.lichi.goodrongyi.ui.activity.my;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.model.AttestationBean;
import com.lichi.goodrongyi.mvp.presenter.AuthorizeBrandPresenter;
import com.lichi.goodrongyi.mvp.presenter.AutonymAttestationPresenter;
import com.lichi.goodrongyi.mvp.view.AuthorizeBrandView;
import com.lichi.goodrongyi.mvp.view.AutonymAttestationView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;
import com.lichi.goodrongyi.utill.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by test on 2018/3/1.
 */

public class AutonymAttestationActivity extends BaseActivity<AutonymAttestationView, AutonymAttestationPresenter> implements AutonymAttestationView {

    @BindView(R.id.et_owner_name)
    EditText name;
    @BindView(R.id.certificate)
    EditText certificate;
    @BindView(R.id.btn_visa_save)
    Button mBtnVisaSave;
    String realName = "";
    String idCard = "";
    int type = 1;

    @Override
    public AutonymAttestationPresenter initPresenter() {
        return new AutonymAttestationPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonym_attestation);
        ButterKnife.bind(this);

        realName = getIntent().getStringExtra(Constants.IntentParams.ID);
        idCard = getIntent().getStringExtra(Constants.IntentParams.ID2);
        type = getIntent().getIntExtra(Constants.IntentParams.ID3, 1);
        name.setText(realName);
        certificate.setText(idCard);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnVisaSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString().trim();
                String certificateString = certificate.getText().toString().trim();
                if (TextUtils.isEmpty(nameString)) {
                    CommonUtils.showToast(mContext, "请填写真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(certificateString)) {
                    CommonUtils.showToast(mContext, "请填写证件号码");
                    return;
                }
                mPresenter.getUserReal(nameString, certificateString);
            }
        });
        if (type == 1) {
            name.setFocusable(true);
            certificate.setFocusable(true);
            mBtnVisaSave.setVisibility(View.VISIBLE);
        } else {
            name.setFocusable(false);
            certificate.setFocusable(false);
            mBtnVisaSave.setVisibility(View.GONE);
            SweetAlertDialog sd = new SweetAlertDialog(mContext);
            sd.setTitleText("提示");
            sd.setContentText("实名认证之后，无法进行修改，若有问题请联系客服");
            sd.setCancelable(true);
            sd.setConfirmText("确定");
            sd.setCanceledOnTouchOutside(true);
            sd.show();
        }

    }

    @Override
    public void dataUserRealSucceed(AttestationBean upLoadingFileBean) {
        CommonUtils.showToast(mContext, "保存成功");
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
        CommonUtils.showToast(mContext, "系统异常");
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
