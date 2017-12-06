package com.yuanshi.iotpro.publiclib.model.http;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.StringConverterFactory;
import com.yuanshi.iotpro.publiclib.utils.YLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carl on 2017/4/19.
 */

public class ApiManager {
    private static  Retrofit mRetrofit = null;

    public static Retrofit getmRetrofit(Context context){
        if(mRetrofit == null){
            mRetrofit =  new Retrofit.Builder()
                    .baseUrl(Constant.SERVER_DOMAIN)//url固定部分
                    .addConverterFactory(GsonConverterFactory.create())//使用Gson解析
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 使用RxJava作为回调适配器
                    .client(genericClient(context))
                    .build();
        }
        return  mRetrofit;
    }
    private static OkHttpClient genericClient(Context context) {
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().cookieJar(cookieJar)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .build();
                        return chain.proceed(request);
                    }

                })
                .addInterceptor(logging)
                .build();
        return httpClient;
    }


//    private static OkHttpClient getOkHttpClient() {
//        //日志显示级别
//        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
//        //新建log拦截器
//        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                YLog.e("OkHttp====Message:"+message);
//            }
//        });
//        loggingInterceptor.setLevel(level);
//        //定制OkHttp
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
//                .Builder();
//        //OkHttp进行添加拦截器loggingInterceptor
//        httpClientBuilder.addInterceptor(loggingInterceptor);
//        return httpClientBuilder.build();
//    }

}
