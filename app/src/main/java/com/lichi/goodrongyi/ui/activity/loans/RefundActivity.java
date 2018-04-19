package com.lichi.goodrongyi.ui.activity.loans;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.RefundPresenter;
import com.lichi.goodrongyi.mvp.view.RefundView;
import com.lichi.goodrongyi.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 还款
 */
public class RefundActivity extends BaseActivity<RefundView, RefundPresenter> {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.iv_refund_more)
    ImageView ivRefundMore;
    @BindView(R.id.iv_refund_icon)
    ImageView ivRefundIcon;
    @BindView(R.id.ev_refund_price)
    EditText evRefundPrice;
    @BindView(R.id.tv_refund_type)
    TextView tvRefundType;
    @BindView(R.id.btn_refund_save)
    Button btnRefundSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        ButterKnife.bind(this);

    }

    @Override
    public RefundPresenter initPresenter() {
        return new RefundPresenter();
    }

    @OnClick({R.id.tv_back, R.id.iv_refund_more, R.id.btn_refund_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                RefundActivity.this.finish();
                break;
            case R.id.iv_refund_more:
                break;
            case R.id.btn_refund_save:
                break;
        }
    }
}
