package com.lichi.goodrongyi.mvp.model;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/12/7 0013
 * E-mail:
 */
public interface VisaPresenterService {

    /**
     * 获取信用卡列表
     */
    @GET("/bill/bill")
    Observable<ResponseMessage<List<CreditCardBean>>> getIndexBillList(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     * 获取全部信用卡列表
     */
    @GET("/bill/bill")
    Observable<ResponseMessage<List<CreditCardBean>>> getIndexBillLists(@Query("userId") String userId, @Query("page") int page, @Query("size") int size);

    /**
     * 获取信用卡详细信息列表
     */
    @GET("/bill/detail/{billId}/")
    Observable<ResponseMessage<CreditCardDetailsBean>> getIndexBillDetail(@Path("billId") String billId);

    /**
     * 获取信用卡月列表
     */
    @GET("/bill")
    Observable<ResponseMessage<CreditCardMonthBean>> getBill1(@Query("userId") String userId, @Query("issueBank") String issueBank
            , @Query("holderName") String holderName, @Query("last4digit") String last4digit);

    /**
     * 获取信用卡月列表
     */
    @GET("/bill/getBill")
    Observable<ResponseMessage<CreditCardMonthBean>> getBill(@Query("creditId") String creditId, @Query("page") int page, @Query("size") int size);

    /**
     * 获取诊断列表
     */
    @GET("/diagnosis/get")
    Observable<ResponseMessage<DiagnseListBean>> getDiagnosisList(@Query("userId") String userId, @Query("bankname") String bankname, @Query("last4digit") String last4digit);

    /**
     * 获取诊断文案
     */
    @GET("/diagnosis/radar")
    Observable<ResponseMessage<List<String>>> getRadarList();

    /**
     * 诊断
     */
    @GET("/diagnosis")
    Observable<ResponseMessage<DiagnoseBean>> getDiagnosis(@QueryMap Map<String, String> map);

    /**
     * 诊断
     */
    @GET("/diagnosis/result")
    Observable<ResponseMessage<List<ResultDiagnoseBean>>> getResultDiagnosis(@Query("userId") String userId);

    /**
     * 获取客服
     *
     * @param type
     * @return
     */
    @GET("/login-user/qq")
    Observable<ResponseMessage<CustomerBean>> getCustomer(@Query("type") String type);

    /**
     * 获取客服
     *
     * @return
     */
    @POST("/bill/delete/{billId}")
    @FormUrlEncoded
    Observable<ResponseMessage<BillDeleteBean>> getDelete(@Path("billId") String billId, @Field("userId") String userId);

    /**
     * 删除信用卡
     *
     * @return
     */
    @POST("/credit/delete")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getCreditDelete(@Field("id") String id);
}
