package com.lichi.goodrongyi.mvp.model;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 默小小 on 2017/12/27.
 * 刷卡任务
 */

public interface UserCardService {
    /**
     * 刷卡任务
     *
     * @param page
     * @param size
     * @param status
     * @param id
     * @return
     */
    @GET("/user/card")
    Observable<ResponseMessage<UserCardBean>> getUserCard(@Query("page") int page, @Query("size") int size, @Query("status") int status, @Query("id") String id);

    @GET("/user/card")
    Observable<ResponseMessage<UserCardBean>> getUserCardAll(@Query("page") int page, @Query("size") int size, @Query("id") String id);

    /**
     * 刷卡任务
     *
     * @param id
     * @param money
     * @return
     */
    @POST("/diagnosis/update")
    @FormUrlEncoded
    Observable<ResponseMessage<UserCardBean>> getUserCardUpdate(@Field("id") int id, @Field("money") String money);

    /**
     * 获取条数
     *
     * @param id
     * @return
     */
    @GET("/user/task-count")
    Observable<ResponseMessage<UserCardNumBean>> getUserCardTaskCount(@Query("id") String id);
}
