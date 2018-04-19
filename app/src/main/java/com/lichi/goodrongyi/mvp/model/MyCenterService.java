package com.lichi.goodrongyi.mvp.model;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 默小小 on 2017/12/22.
 */

public interface MyCenterService {

    @GET("/user/card")
    Observable<ResponseMessage<ShuaKaRenWuBean>> getShuaKaRenWu(@Query("page") int page, @Query("size") int size, @Query("status") int status, @Query("id") String id);

    @GET("/user/mycourse")
    Observable<ResponseMessage<MyCourseBean>> getMyCourse(@Query("id") String id);

    @GET("/user/task-count")
    Observable<ResponseMessage<ShuaBean>> getTaskCount(@Query("id") String id);

    @GET("/user/rank")
    Observable<ResponseMessage<UserRankBean>> getUserRank(@Query("page") int page, @Query("size") int size);

    @FormUrlEncoded
    @POST("/user/real")
    Observable<ResponseMessage<AttestationBean>> getUserReal(@Field("realName") String realName, @Field("idCard") String idCard);

    /**
     * 获取用户信息
     */
    @GET("/user")
    Observable<ResponseMessage<UserBean>> getUser();

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("/user")
    Observable<ResponseMessage<UpLoadingFileBean>> getPostUser(@FieldMap Map<String, String> map);

    /**
     * 上传文件
     *
     * @return
     */
    @POST("/file/upload")
    @Multipart
    Observable<ResponseMessage<String>> uploadFile(@Part() MultipartBody.Part parts);
    // Observable<ResponseMessage<String>> uploadFile(@Part("file\"; filename=\"img_head.png") RequestBody file);

    @GET("/user/rank/ranking")
    Observable<ResponseMessage<RankingBean>> getRanking(@Query("id") String id);

    @GET("/user/qrCode")
    Observable<ResponseMessage<QrcodeBean>> getqrCode();

    /**
     * 获取客服
     * @param type
     * @return
     */
    @GET("/login-user/qq")
    Observable<ResponseMessage<CustomerBean>> getCustomer(@Query("type") String type);

}
