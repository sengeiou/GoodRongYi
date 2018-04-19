package com.lichi.goodrongyi.mvp.model;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 默小小 on 2017/12/20.
 */

public interface QueryService {
    //征信查询

    @POST("/credit")
    Observable<ResponseMessage<CreditInvestigationBean>> getCreditInvestigation(@Body String middleAuthCode, @Body String password, @Body String username);
    //公积金查询

    @POST("/housefund")
    Observable<ResponseMessage<HouseFundBaseBean>> getHouseFund(@Body String area, @Body String password, @Body String username, @Body String otherInfo, @Body String realName);
    //支持公积金地区

    @GET("/housefund/area")
    Observable<ResponseMessage<String>> getArea();
    //公积金地区登陆元素

    @GET("/housefund/area/{areaCode}")
    Observable<ResponseMessage<String>> getAreaCode();
    //社保查询

    @POST("/socialsecurity")
    Observable<ResponseMessage<SocialBaseBean>> getSocialsecurity(@Body String area, @Body String password, @Body String username, @Body String otherInfo, @Body String realName);
    //学历查询

    @POST("/study")
    Observable<ResponseMessage<StudyBaseBean>> getStudy(@Query("username") String username, @Query("password") String password, @Query("method") String method);

}
