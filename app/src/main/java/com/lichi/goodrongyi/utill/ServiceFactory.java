package com.lichi.goodrongyi.utill;


import com.lichi.goodrongyi.mvp.model.BillService;
import com.lichi.goodrongyi.mvp.model.CourseService;
import com.lichi.goodrongyi.mvp.model.LoansService;
import com.lichi.goodrongyi.mvp.model.MessageService;
import com.lichi.goodrongyi.mvp.model.MyCenterService;
import com.lichi.goodrongyi.mvp.model.MyClassService;
import com.lichi.goodrongyi.mvp.model.QueryService;
import com.lichi.goodrongyi.mvp.model.DiscoverService;
import com.lichi.goodrongyi.mvp.model.LoginService;
import com.lichi.goodrongyi.mvp.model.MainService;
import com.lichi.goodrongyi.mvp.model.UserCardService;
import com.lichi.goodrongyi.mvp.model.VideoService;
import com.lichi.goodrongyi.mvp.model.VisaPresenterService;

/**
 * Author：Jenny
 * Date:2016/8/26 14:19
 * E-mail：fishloveqin@gmail.com
 * 网络接口服务工厂
 */
public final class ServiceFactory {

    private ServiceFactory() {

    }

    private static MainService mMainService;
    private static LoginService mLoginService;
    private static CourseService mCourseService;
    private static BillService mBillService;
    private static VisaPresenterService mVisaPresenterService;
    private static DiscoverService mDiscoverService;
    private static QueryService mQueryService;
    private static MyCenterService mMyCenterService;
    private static VideoService mVideoService;
    private static MyClassService myClassService;
    private static LoansService mLoansService;
    private static UserCardService userCardService;
    private static MessageService mMessageService; //消息


    /***
     * 首页接口对象
     * @return
     */
    public static synchronized MainService getMainService() {
        if (mMainService == null) {
            mMainService = RXUtil.Factory.create(MainService.class);

        }
        return mMainService;
    }

    /***
     * 登陆注册接口对象
     * @return
     */
    public static synchronized LoginService getLoginService() {
        if (mLoginService == null) {
            mLoginService = RXUtil.Factory.create(LoginService.class);

        }
        return mLoginService;
    }

    /***
     * 课程接口对象
     * @return
     */
    public static synchronized CourseService getCourseService() {
        if (mCourseService == null) {
            mCourseService = RXUtil.Factory.create(CourseService.class);

        }
        return mCourseService;
    }

    /***
     * 账单接口对象
     * @return
     */
    public static synchronized BillService getBillService() {
        if (mBillService == null) {
            mBillService = RXUtil.Factory.create(BillService.class);

        }
        return mBillService;
    }

    /***
     * 信用卡接口对象
     * @return
     */
    public static synchronized VisaPresenterService getVisaPresenterService() {
        if (mVisaPresenterService == null) {
            mVisaPresenterService = RXUtil.Factory.create(VisaPresenterService.class);

        }
        return mVisaPresenterService;
    }

    /***
     * 发现对象
     * @return
     */
    public static synchronized DiscoverService getDiscoverPresenterService() {
        if (mDiscoverService == null) {
            mDiscoverService = RXUtil.Factory.create(DiscoverService.class);

        }
        return mDiscoverService;
    }

    /***
     * 查询对象
     * @return
     */
    public static synchronized QueryService getmQueryService() {
        if (mDiscoverService == null) {
            mQueryService = RXUtil.Factory.create(QueryService.class);

        }
        return mQueryService;
    }


    /***
     * 个人主页获取数据
     * @return
     */
    public static synchronized MyCenterService getMyCenterService() {
        if (mMyCenterService == null) {
            mMyCenterService = RXUtil.Factory.create(MyCenterService.class);

        }
        return mMyCenterService;
    }


    /***
     *
     * @return
     */
    public static synchronized VideoService getVideoService() {
        if (mVideoService == null) {
            mVideoService = RXUtil.Factory.create(VideoService.class);

        }
        return mVideoService;

    }


    /***
     * 我的课程
     * @return
     */
    public static synchronized MyClassService getMyClass() {
        if (myClassService == null) {
            myClassService = RXUtil.Factory.create(MyClassService.class);
        }
        return myClassService;
    }

    /***
     * 网贷
     * @return
     */
    public static synchronized LoansService getLoansService() {
        if (mLoansService == null) {
            mLoansService = RXUtil.Factory.create(LoansService.class);
        }
        return mLoansService;
    }

    /**
     * 个人信息
     *
     * @return
     */
    public static synchronized UserCardService getUserCard() {
        if (userCardService == null) {
            userCardService = RXUtil.Factory.create(UserCardService.class);
        }
        return userCardService;
    }

    /**
     * 消息
     *
     * @return
     */
    public static synchronized MessageService getMessageService() {
        if (mMessageService == null) {
            mMessageService = RXUtil.Factory.create(MessageService.class);
        }
        return mMessageService;
    }
}
