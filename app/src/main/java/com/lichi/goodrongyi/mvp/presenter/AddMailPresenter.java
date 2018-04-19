package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.view.AddBillView;
import com.lichi.goodrongyi.mvp.view.AddMailView;
import com.lichi.goodrongyi.utill.ServiceFactory;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class AddMailPresenter extends BasePresenter<AddMailView> {
    private MainService mService;

    public AddMailPresenter() {
        mService = ServiceFactory.getMainService();
    }


}
