package com.yuanshi.iotpro.publiclib.model.http;

import com.yuanshi.iotpro.publiclib.bean.Status;
import com.yuanshi.iotpro.publiclib.utils.FileCallBack;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
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

    /**
     * 编辑个人资料
     * @return
     */
    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> edituser(@Query("m") String m, @Query("c") String c, @Query("a") String a, @FieldMap Map<String, Object> map);

    /**
     * 退出登录
     * @param m
     * @param c
     * @param a
     * @return
     */
    @POST("index.php")
    Observable<Status> logout(@Query("m") String m, @Query("c") String c, @Query("a") String a);

    /**
     * 上传图片
     * @param m
     * @param c
     * @param a
     * @param body
     * @return
     */
    @Multipart
    @POST("index.php")
    Observable<Status> appupload (@Query("m") String m, @Query("c") String c, @Query("a") String a,   @Part MultipartBody.Part body);


    //http://47.104.13.45/index.php?m=App&c=Crew&a=index
    /**
     * 获取用户当前的剧组
     * @return
     */
    @GET("index.php")
    Observable<Status> index (@Query("m") String m, @Query("c") String c, @Query("a") String a);

    //http://47.104.13.45/index.php?m=App&c=Crew&a=searchcrew
    //POST [ keyword ]

    /**
     * 搜索剧组
     * @param keyword 关键字
     * @return
     */
    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> searchcrew (@Query("m") String m, @Query("c") String c, @Query("a") String a,@Field("keyword")String keyword);
    /*
    http://47.104.13.45/index.php?m=App&c=Crew&a=outs
     */
    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> outs (@Query("m") String m, @Query("c") String c, @Query("a") String a,@Field("id")String crewId);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> doAdd(@Query("m") String m, @Query("c") String c, @Query("a") String a, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> thecrewinfo(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id")String id);


    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> editusercrew(@Query("m") String m, @Query("c") String c, @Query("a") String a, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> joins(@Query("m") String m, @Query("c") String c, @Query("a") String a, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> department(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id") String id);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> usercrew(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id") String id);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> phonegetuser(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("phone") String phone, @Field("groupid") String groupid);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> index(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id") String id);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> details(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id") String id);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> del(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id") String id);

    @FormUrlEncoded
    @POST("index.php")
    Observable<Status> doAdd(@Query("m") String m, @Query("c") String c, @Query("a") String a, @Field("id") String id,@FieldMap Map<String, Object> map);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}
