package com.yuanshi.iotpro.publiclib.model;



import android.content.Context;

import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.model.http.ApiManager;
import com.yuanshi.iotpro.publiclib.model.http.HttpServer;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IHttpModel;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
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
    public IHttpModelImpl(Context context){
        retrofit = ApiManager.getmRetrofit(context);
        server = retrofit.create(HttpServer.class);
    }

    @Override
    public void login(String phone,String verify  ,Observer observer) {
        server.login("App","Index","login",phone,verify).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void getverify(String phone, Observer observer) {
        server.getverify("App","Index","getverify",phone).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void login2(String uid, Observer observer) {
        server.login2("App","Index","login2",uid).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void edituser(Map<String, String> map, Observer observer) {
        server.edituser("App","Index","edituser",map).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void logout(Observer observer) {
        server.logout("App","Index","logout").subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

}
