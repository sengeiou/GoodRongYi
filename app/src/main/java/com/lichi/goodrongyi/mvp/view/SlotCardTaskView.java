package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.UserCardBean;
import com.lichi.goodrongyi.mvp.model.UserCardNumBean;

/**
 * Created by ArDu on 2017/12/13.
 */

public interface SlotCardTaskView extends WrapView {
    void dataSuccee(UserCardBean bean);
    void dataSucceeAll(UserCardBean bean);
    void dataSueeccUn(UserCardBean bean);
    void dataUpdataSueeccUn(UserCardBean bean);
    void dataNumBeanSueeccUn(UserCardNumBean bean);
    void dataDefeated(String msg);
}
