package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.AttestationBean;

/**
 * Created by ArDu on 2017/12/13.
 */

public interface AutonymAttestationView extends WrapView {
    void dataUserRealSucceed(AttestationBean attestationBean); 

    void dataDefeated(String msg); //返回失败
}
