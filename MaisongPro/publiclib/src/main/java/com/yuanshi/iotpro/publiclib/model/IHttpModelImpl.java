package com.yuanshi.iotpro.publiclib.model;



import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.model.http.ApiManager;
import com.yuanshi.iotpro.publiclib.model.http.HttpServer;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IHttpModel;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public class IHttpModelImpl implements IHttpModel {
    Retrofit retrofit;
    HttpServer server;
    public IHttpModelImpl(){
        retrofit = ApiManager.mRetrofit;
        server = retrofit.create(HttpServer.class);
    }

    @Override
    public void userLogin(String deviceID, String username, String password,String appId ,Observer observer) {
        server.userLogin("Api","App","userLogin",deviceID,username,password,appId).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }
}
