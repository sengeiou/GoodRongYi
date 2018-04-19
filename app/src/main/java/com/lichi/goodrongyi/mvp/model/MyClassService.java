package com.lichi.goodrongyi.mvp.model;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 默小小 on 2017/12/26.
 */

public interface MyClassService {

    @GET("/user/course")
    Observable<ResponseMessage<CourseBean>> getClass(@Query("page") int page,@Query("size")int size,@Query("status")int status,@Query("id")String id);
}
