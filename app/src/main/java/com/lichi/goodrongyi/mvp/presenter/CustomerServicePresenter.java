package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.view.ChangePassView;
import com.lichi.goodrongyi.mvp.view.CustomerServiceView;
import com.lichi.goodrongyi.utill.ServiceFactory;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class CustomerServicePresenter extends BasePresenter<CustomerServiceView> {
    private MainService mService;

    public CustomerServicePresenter() {
        mService = ServiceFactory.getMainService();
    }


}
