package com.lichi.goodrongyi.mvp.presenter;

import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.view.AddMailView;
import com.lichi.goodrongyi.mvp.view.DataListView;
import com.lichi.goodrongyi.utill.ServiceFactory;

/**
 * Created by 默小小 on 2017/11/30.
 */

public class DataListPresenter extends BasePresenter<DataListView> {
    private MainService mService;

    public DataListPresenter() {
        mService = ServiceFactory.getMainService();
    }


}
