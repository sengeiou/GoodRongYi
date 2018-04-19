package com.lichi.goodrongyi.mvp.model;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/1/13 0013
 * E-mail:hekescott@qq.com
 */
public interface MessageService {
    /**
     * 获取消息列表
     */
    @GET("/message/page")
    Observable<ResponseMessage<MessageListBean>> getMessageList(@Query("userId") String userId,@Query("page") int page,@Query("size") int size);

}
