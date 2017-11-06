package com.yuanshi.iotpro.publiclib.model.http;

import com.yuanshi.iotpro.publiclib.bean.Status;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Dengbocheng on 2017/6/1.
 */

public interface HttpServer {



    @POST("index.php")
    Observable<Status> getverify(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Query("phone") String phone);
    /**
     * 用户登录接口
     * @returnz
     */
    @POST("index.php")
    Observable<Status> login(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Query("phone") String phone , @Query("verify") String verify);

    /**
     * 用户自动登录接口
     * @returnz
     */
    @POST("index.php")
    Observable<Status> login2(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Query("uid") String phone);

}
