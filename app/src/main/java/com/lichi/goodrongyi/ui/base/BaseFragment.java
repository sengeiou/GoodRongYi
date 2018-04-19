package com.lichi.goodrongyi.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.BasePresenter;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements FragmentBackHandler {
    public Context  mContext;
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mPresenter.attach((V) this);
        mPresenter.context = getContext();
    }


    @Override
    public void onDetach() {
       // mPresenter.dettach();
        super.onDetach();

    }

    // 实例化presenter
    public abstract T initPresenter();

    @Override
    public boolean onBackPressed() {
        return interceptBackPressed()
                || (getBackHandleViewPager() == null
                ? BackHandlerHelper.handleBackPress(this)
                : BackHandlerHelper.handleBackPress(getBackHandleViewPager()));
    }

    public boolean interceptBackPressed() {
        return false;
    }

    /**
     * 2.1 版本已经不在需要单独对ViewPager处理
     *
     * @deprecated
     */
    @Deprecated
    public ViewPager getBackHandleViewPager() {
        return null;
    }


    protected SweetAlertDialog mLoadingDialog;

    protected void showLoadingIndicator(boolean active) {
        if (active) {
            showLoadingDialog();
        } else {
            dismissLoadingDialog();
        }
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper()
                    .setBarColor(getResources().getColor(R.color.colorPrimary));
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setTitleText("数据加载中...");
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
