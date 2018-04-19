package com.lichi.goodrongyi.mvp.model;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/1/13 0013
 * E-mail:hekescott@qq.com
 */
public interface MainService {
    /**
     * 获取课程列表
     */
    @GET("/course/index-course")
    Observable<ResponseMessage<NewestCourseBean>> getIndexCourseList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     * 获取视频列表
     */
    @GET("/video")
    Observable<ResponseMessage<NewestVideoBean>> getIndexVideoList(@Query("page") int page, @Query("size") int size);

    /**
     * 获取信用卡列表
     */
    @GET("/bill/bill")
    Observable<ResponseMessage<List<IndexCreditCardBean>>> getIndexBillList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     * 获取全部信用卡列表
     */
    @GET("/bill/bill")
    Observable<ResponseMessage<List<CreditCardBean>>> getIndexBillLists(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);


    /**
     * 诊断
     */
    @GET("/diagnosis")
    Observable<ResponseMessage<DiagnoseBean>> getDiagnosis(@QueryMap Map<String, String> map);

    /**
     * 获取消息列表
     */
    @GET("/message/page")
    Observable<ResponseMessage<MessageListBean>> getMessageList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载

    @Streaming
    @GET("/version/new")
    Observable<ResponseMessage<VersionsBean>> getNewVersion();//获取版本

    @GET("/notice")
    Observable<ResponseMessage<NoticeBean>> getNotice(@Query("userId") String userId);//获取版本userId

    /**
     * 免费课程
     *
     * @return
     */
    @GET("/video/free")
    Observable<ResponseMessage<NewestVideoBean>> getVideoFree( @Query("page") int page, @Query("size") int size);

   /* @POST("publics/public_interface/uplod_file")
    @Multipart
    Observable<ResponseMessage> uploadFile(@Part("file\"; filename=\"avatar.png\"") RequestBody file);

    @Multipart
    @POST("attachment/upload")
    Observable<ResponseMessage<PathItem>> uploadImage(@Part("file\"; filename=\"img_file.png") RequestBody file,
                                                      @Part("module") RequestBody module,
                                                      @Part("itemid") RequestBody itemId, @Part("token") RequestBody token);*/

    /**
     * 轮播
     */
    @GET("/banner")
    Observable<ResponseMessage<List<IndexBannerBean>>> getBanner();
    /**
     * 最近一周的课
     */
    @GET("/video/last")
    Observable<ResponseMessage<List<WeekCourseBean>>> getDataLast();

    /**
     * 获取视频支付(支付宝)
     */
    @POST("/alipay/v1/video/{videoId}")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getZfbPayVideo(@Field("help") String help, @Path("videoId") String courseId);
}
