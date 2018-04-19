package com.lichi.goodrongyi.mvp.view;

import com.lichi.goodrongyi.mvp.model.CustomerBean;
import com.lichi.goodrongyi.mvp.model.MyCourseBean;
import com.lichi.goodrongyi.mvp.model.RankingBean;
import com.lichi.goodrongyi.mvp.model.ShuaBean;
import com.lichi.goodrongyi.mvp.model.ShuaKaRenWuBean;
import com.lichi.goodrongyi.mvp.model.UpLoadingFileBean;
import com.lichi.goodrongyi.mvp.model.UserBean;

import java.io.IOException;

/**
 * Created by Mic-roo on 2017/12/1.
 * Describe：
 */

public interface MineView extends WrapView {
    void ShuaKaSucceed(ShuaBean str);

    void MyCourseSucceed(MyCourseBean str);

    void dataUserBeanucceed(UserBean userBean) throws IOException; //用户信息成功

    void dataModificationSucceed(UpLoadingFileBean userBean); //修改用户信息成功

    void dataFileBeanucceed(String upLoadingFileBean); //上传文件成功

    void dataDefeated(String msg); //返回失败

    void rankingSucceed(RankingBean bean);

    void CustomerSucceed(CustomerBean bean,int type);
}
