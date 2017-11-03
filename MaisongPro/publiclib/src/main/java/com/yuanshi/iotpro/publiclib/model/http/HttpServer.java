package com.yuanshi.iotpro.publiclib.model.http;

import com.yuanshi.iotpro.publiclib.bean.Status;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Dengbocheng on 2017/6/1.
 */

public interface HttpServer {


    /**
     * 用户登录接口
     * @param deviceID  冰箱号,
     * @param username  用户名/用户卡号
     * @param password   密码，或为空
     * @param appId   移动设备唯一ID
     * @returnz
     */
    @POST("index.php")
    Observable<Status> userLogin(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Query("deviceID") String deviceID, @Query("userName") String username, @Query("userPwd") String password, @Query("appId") String appId);
}
