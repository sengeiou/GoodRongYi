package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.UserRankBean;

/**
 * Created by ArDu on 2017/12/13.
 */

public interface RankView extends WrapView {

    void rankSueecc(UserRankBean bean);
    void dataDefeated(String msg); //返回失败

}
