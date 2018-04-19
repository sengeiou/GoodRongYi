package com.lichi.goodrongyi.ui.activity.loans;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.AplayPresenter;
import com.lichi.goodrongyi.mvp.view.AplayView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AplayActivity extends BaseActivity<AplayView,AplayPresenter> {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.iv_visa_detail_more)
    ImageView ivVisaDetailMore;
    @BindView(R.id.rll_toolbar)
    RelativeLayout rllToolbar;
    @BindView(R.id.ev_aply_price)
    EditText evAplyPrice;
    @BindView(R.id.tv_aply_type)
    TextView tvAplyType;
    @BindView(R.id.tv_aply_huan)
    TextView tvAplyHuan;
    @BindView(R.id.tv_play_lixi)
    TextView tvPlayLixi;
    @BindView(R.id.btn_refund_save)
    Button btnRefundSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplay);
        ButterKnife.bind(this);
    }

    @Override
    public AplayPresenter initPresenter() {
        return new AplayPresenter();
    }

    @OnClick({R.id.tv_back, R.id.tv_aply_type, R.id.tv_aply_huan, R.id.btn_refund_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                AplayActivity.this.finish();
                break;
            case R.id.tv_aply_type:
                break;
            case R.id.tv_aply_huan:
                break;
            case R.id.btn_refund_save:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
