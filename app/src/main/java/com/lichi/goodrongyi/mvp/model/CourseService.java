package com.lichi.goodrongyi.mvp.model;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/12/7 0013
 * E-mail:
 */
public interface CourseService {
    /**
     * 获取课程列表
     */
    @GET("/course/list/")
    Observable<ResponseMessage<CourseBean>> getDataList(@Query("locationId") String locationId, @Query("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     * 获取课程详情
     */
    @GET("/course/{id}")
    Observable<ResponseMessage<CourseDetailsBean>> getDataCourse(@Path("id") int id);

    /**
     * 获取课程详情
     */
    @GET("/course/user/{id}")
    Observable<ResponseMessage<CourseDetailsBean>> getDataCourseUser(@Path("id") int id);

    /**
     * 获取支付
     */
    @POST("/payCourse/course/{courseId}")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getPayCourse(@Field("help") String help, @Path("courseId") String courseId);

    /**
     * 获取支付支付宝
     */
    @POST("/alipay/v1/course/{courseId}")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getPayZfbCourse(@Field("help") String help, @Path("courseId") String courseId);

    /**
     * 课程报名
     */
    @POST("/course/signUp")
    @FormUrlEncoded
    Observable<ResponseMessage<CourseSignUpBean>> getSignUp(@FieldMap Map<String, String> params);

    /**
     * 课程付费
     */
    @GET("/course/pay")
    Observable<ResponseMessage<CoursePayBean>> getPay(@Query("id") String id, @Query("userId") String userId, @Query("money") String money);

    /**
     * 获取验证码
     */
    @GET("/code/sms")
    Observable<ResponseMessage<VerificationCodeBean>> getCode(@Query("mobile") String mobile);


    /**
     * 获取用户信息
     */
    @GET("/user")
    Observable<ResponseMessage<UserBean>> getUser(@Header("token") String token);


    /**
     * 获取用户信息
     */
    @GET("/user")
    Observable<ResponseMessage<UserBean>> getUser();

    /**
     * 获取客服
     *
     * @param type
     * @return
     */
    @GET("/login-user/qq")
    Observable<ResponseMessage<CustomerBean>> getCustomer(@Query("type") String type);

}
