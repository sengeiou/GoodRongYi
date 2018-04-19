package com.lichi.goodrongyi.mvp.model;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/12/7 0013
 * E-mail:
 */
public interface LoansService {

    /**
     * 获取网贷列表
     */
    @GET("/netloan")
    Observable<ResponseMessage<List<LoansBean>>> getNetloan(@Query("type") int type);

    /**
     * 获取快卡列表
     */
    @GET("/quick-card")
    Observable<ResponseMessage<List<QuickCardBean>>> getquickCard(@Query("type") int type);

}
