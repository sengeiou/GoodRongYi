package com.lichi.goodrongyi.mvp.model;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/1/13 0013
 * E-mail:hekescott@qq.com
 */
public interface VideoService {
    /**
     * 获取视频详情
     */
    @GET("/video/{id}")
    Observable<ResponseMessage<VideoDetailsBean>> getDataCourse(@Path("id") String id);

    /**
     * 获取银行列表
     */
    @GET("/video/dict")
    Observable<ResponseMessage<List<SelectbankBean>>> getDataDict();

    /**
     * 最近一周的课
     */
    @GET("/video/last")
    Observable<ResponseMessage<List<WeekCourseBean>>> getDataLast();

    /**
     * 获取视频列表
     */
    @GET("/video")
    Observable<ResponseMessage<NewestVideoBean>> getIndexVideoList(@Query("page") int page, @Query("size") int size, @Query("code") String code);

    /**
     * 获取视频列表
     */
    @GET("/video/codeList")
    Observable<ResponseMessage<NewestVideoBean>> getVideoCodeList(@Query("page") int page, @Query("size") int size, @Query("code") String code);

    /**
     * 获取视频支付
     */
    @POST("/payCourse/video/{videoId}")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getPayCourse(@Field("help") String help, @Path("videoId") String courseId);

    /**
     * 获取视频支付(支付宝)
     */
    @POST("/alipay/v1/video/{videoId}")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getZfbPayVideo(@Field("help") String help, @Path("videoId") String courseId);
}
