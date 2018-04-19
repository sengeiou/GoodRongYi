package com.lichi.goodrongyi.ui.fragment.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.bean.QueryUserBean;
import com.lichi.goodrongyi.mvp.model.CreditInvestigationBean;
import com.lichi.goodrongyi.mvp.model.HouseFundBaseBean;
import com.lichi.goodrongyi.mvp.model.SocialBaseBean;
import com.lichi.goodrongyi.mvp.model.StudyBaseBean;
import com.lichi.goodrongyi.mvp.presenter.BaseInfoPresenter;
import com.lichi.goodrongyi.mvp.view.BaseInfoView;
import com.lichi.goodrongyi.mvp.view.BaseView;
import com.lichi.goodrongyi.ui.base.BaseFragment;
import com.lichi.goodrongyi.utill.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 默小小 on 2017/12/13.
 */

@SuppressLint("ValidFragment")
public class BaseInfoFragment extends BaseFragment<BaseInfoView, BaseInfoPresenter> implements BaseInfoView{

    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_user_no)
    TextView tvUserNo;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.tv_user_balance)
    TextView tvUserBalance;
    @BindView(R.id.tv_balance_save)
    TextView tvBalanceSave;
    @BindView(R.id.tv_jishu)
    TextView tvJiShu;
    @BindView(R.id.tv_end_save_time)
    TextView tvEndSaveTime;
    @BindView(R.id.tv_user_status)
    TextView tvUserStatus;
    @BindView(R.id.tv_user_start)
    TextView tvUserStart;
    @BindView(R.id.tv_user_add)
    TextView tvUserAdd;
    Unbinder unbinder;
    private Context context;
    private QueryUserBean bean;

    @Override
    public BaseInfoPresenter initPresenter() {
        return new BaseInfoPresenter();
    }

    @SuppressLint("ValidFragment")
    public BaseInfoFragment(QueryUserBean bean, Context context) {
        this.context = context;
        this.bean = bean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_info_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        switch (bean.getType()) {
            case 1:
                //社保
                mPresenter.getSocialsecurityBaseInfo(bean.getCityName(),bean.getUserName(),bean.getPassWord(),bean.getOtherInfo(),bean.getRealName());
                //tvUser.setText("社保账户:");
                break;
            case 2:
                //公积金
               // tvUser.setText("公积金账户:");
                mPresenter.getHouseFundBaseInfo(bean.getCityName(),bean.getUserName(),bean.getPassWord(),bean.getOtherInfo(),bean.getRealName());
                break;
            case 3:
                //学历
                mPresenter.getStudyBaseInfo(bean.getUserName(),bean.getPassWord(),bean.getCode());
                break;
            case 4:
                //征信
                mPresenter.getCreditInvestigationBaseInfo(bean.getCode(),bean.getPassWord(),bean.getUserName());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void queryStudySuccess(StudyBaseBean bean) {

    }

    @Override
    public void queryCreditSuccess(CreditInvestigationBean bean) {

    }

    @Override
    public void querySocialSecuritySuccess(SocialBaseBean bean) {

    }

    @Override
    public void queryHouseFundSuccess(HouseFundBaseBean bean) {

    }

    @Override
    public void dataDefeated(String msg) {
        CommonUtils.showToast(mContext, msg);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
