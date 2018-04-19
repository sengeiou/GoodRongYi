package com.lichi.goodrongyi.mvp.model;

import com.lichi.goodrongyi.bean.cheshi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/12/7 0013
 * E-mail:
 */
public interface LoginService {
    /**
     * 获取验证码
     */
    @GET("/code/sms")
    Observable<ResponseMessage<VerificationCodeBean>> getCode(@Query("mobile") String mobile);

    /**
     * 登录
     */
    //@Headers({"Content-type:application/x-www-form-urlencoded", "Authorization:Basic bGljaGk6YWJjZGVm"})
    @POST("/authentication/mobile")
    @FormUrlEncoded
    Observable<ResponseMessage<LogInBean>> getLogIn(@Field("mobile") String mobile, @Field("smsCode") String smsCode);

    /**
     * 获取用户信息
     */
    @GET("/user")
    Observable<ResponseMessage<UserBean>> getUser(@Header("token") String token);

    /**
     * 第三方登录
     */
    @POST("/authentication/openid")
    @FormUrlEncoded
    Observable<ResponseMessage<LogInBean>> getOpenid(@Field("openid") String openid,@Field("providerId") String providerId ,@Field("nickname") String nickname,@Field("headimgurl") String headimgurl);
}
