package com.lichi.goodrongyi.mvp.model;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Auther: Scott
 * Date: 2017/1/13 0013
 * E-mail:hekescott@qq.com
 */
public interface BillService {

    /**
     * 添加邮箱
     */
    @POST("/bill")
    @FormUrlEncoded
    Observable<ResponseMessage<AddMaillboxBean>> getBillData(@Field("userId") String userId, @Field("username") String username, @Field("password") String password);

    /**
     * 手动添加邮箱
     */
    @POST("/bill/add")
    @FormUrlEncoded
    Observable<ResponseMessage<AddMaillboxBean>> getBillDataAdd(@FieldMap Map<String, String> map);

    /**
     * 修改信用卡
     */
    @POST("/credit/edit")
    @FormUrlEncoded
    Observable<ResponseMessage<String>> getCreditEdit(@FieldMap Map<String, String> map);

    /**
     * 获取银行
     */
    @GET("/bill/bank")
    Observable<ResponseMessage<List<BankBean>>> getBankListData();

    /**
     * 添加账单
     */
    @POST("/bill/addBill")
    @FormUrlEncoded
    Observable<ResponseMessage<AddMaillboxBean>> getAddBillData(@FieldMap Map<String, String> map);

    /**
     * 邮箱列表
     */
    @GET("/bill/email/")
    Observable<ResponseMessage<List<MaillboxListBean>>> getBillDataList(@Query("userId") String userId);

    /**
     * 邮箱列表
     */
    @GET("/email/list")
    Observable<ResponseMessage<List<MailboxListBean>>> getEmailList();
}
