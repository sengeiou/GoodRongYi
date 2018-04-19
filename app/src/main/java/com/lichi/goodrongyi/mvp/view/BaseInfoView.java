package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CreditBaseBean;
import com.lichi.goodrongyi.mvp.model.CreditInvestigationBean;
import com.lichi.goodrongyi.mvp.model.HouseFundBaseBean;
import com.lichi.goodrongyi.mvp.model.SocialBaseBean;
import com.lichi.goodrongyi.mvp.model.StudyBaseBean;

/**
 * Created by 默小小 on 2017/12/13.
 */

public interface BaseInfoView extends WrapView {
    void queryStudySuccess(StudyBaseBean bean);
    void queryCreditSuccess(CreditInvestigationBean bean);
    void querySocialSecuritySuccess(SocialBaseBean bean);
    void queryHouseFundSuccess(HouseFundBaseBean bean);
    void dataDefeated(String msg);
}
