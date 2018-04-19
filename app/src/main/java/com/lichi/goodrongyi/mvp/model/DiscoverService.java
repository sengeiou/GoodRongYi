package com.lichi.goodrongyi.mvp.model;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 默小小 on 2017/12/19.
 */

public interface DiscoverService {
    //精彩圈子推荐
    @GET("/circle/hot")
    Observable<ResponseMessage<HotCircleBean>> getSplendorCircleList(@Query("page") int page, @Query("size") int size);

    //帖子列表
    @GET("/circle/article/hot")
    Observable<ResponseMessage<EssayListBean>> getEssayList(@Query("page") int page, @Query("size") int size);

    //圈子列表
    @GET("/circle")
    Observable<ResponseMessage<HotCircleBean>> getCircleList(@Query("page") int page, @Query("size") int size);

    //圈子帖子列表
    @GET("/circle/article")
    Observable<ResponseMessage<EssayListBean>> getArticleList(@Query("circleId") String circleId, @Query("page") int page, @Query("size") int size);

    //精彩帖子列表
    @GET("/circle/article/hot")
    Observable<ResponseMessage<EssayListBean>> getArticleHotList(@Query("page") int page, @Query("size") int size);

}
