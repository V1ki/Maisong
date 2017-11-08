package com.yuanshi.iotpro.publiclib.model.http;

import com.yuanshi.iotpro.publiclib.bean.Status;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;

/**
 * Created by Dengbocheng on 2017/6/1.
 */

public interface HttpServer {


    /**
     * 获取手机验证码接口
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> getverify(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("phone") String phone);
    /**
     * 用户登录接口
     * @returnz
     */
    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> login(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("phone") String phone , @Field("verify") String verify);

    /**
     * 用户自动登录接口
     * @returnz
     */
    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> login2(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("uid") String phone);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> edituser(@Query("m") String m, @Query("c") String c, @Query("a") String a, @FieldMap Map<String, String> map);

    @POST("index.php")
    Observable<Status> logout(@Query("m") String m, @Query("c") String c, @Query("a") String a);



}
