package com.yuanshi.iotpro.publiclib.model;



import android.content.Context;

import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.model.http.ApiManager;
import com.yuanshi.iotpro.publiclib.model.http.HttpServer;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IHttpModel;
import com.yuanshi.iotpro.publiclib.utils.FileCallBack;
import com.yuanshi.iotpro.publiclib.utils.FileSubscriber;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    public void edituser(Map<String, Object> map, Observer observer) {
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

    @Override
    public void appupload(MultipartBody.Part body , Observer observer) {
        server.appupload("Home","File","appupload",body).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(20, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void index(Observer observer) {
        server.index("App","Crew","index").subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void searchcrew(String keyword, Observer observer) {
        server.searchcrew("App","Crew","searchcrew",keyword).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void doAdd(String id, String title ,String director, String produced, String makinger, String  stime, String etime,String pw,String groupid,Observer observer) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("title",title);
        map.put("director",director);
        map.put("produced",produced);
        map.put("makinger",makinger);
        map.put("stime",stime);
        map.put("etime",etime);
        map.put("pw",pw);
        map.put("groupid",groupid);
        server.doAdd("App","Crew","doAdd",map).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void thecrewinfo(String id, Observer observer) {
        server.thecrewinfo("App","Crew","thecrewinfo",id).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }


    @Override
    public void editusercrew(Map<String, Object> map, Observer observer) {
        server.editusercrew("App","Crew","editusercrew",map).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void joins(Map<String, Object> map, Observer observer) {
        server.joins("App","Crew","joins",map).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void department(String id, Observer observer) {
        server.department("App","Crew","department",id).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void usercrew(String id, Observer observer) {
        server.usercrew("App","Crew","usercrew",id).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void phonegetuser(String phone, String groupid,Observer observer) {
        server.phonegetuser("App","Index","phonegetuser",phone,groupid).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void index(String requestType, String id, Observer observer) {
        server.index("App",requestType,"index",id).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void details(String requestType, String id, Observer observer) {
        server.details("App",requestType,"details",id).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void del(String requestType, String id, Observer observer) {
        server.del("App",requestType,"del",id).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void doAdd(String requestType, String id, Map<String, Object> map,Observer observer) {
        server.doAdd("App",requestType,"doAdd",id,map).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @Override
    public void download(String url,final FileCallBack<ResponseBody> callBack) {
        server.download(url).subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        callBack.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(callBack));
    }

    @Override
    public void outs(String crewId, Observer observer) {
        server.outs("App","Crew","outs",crewId).subscribeOn(Schedulers.from(MyApplication.THREAD_EXCUTER))
                .observeOn(AndroidSchedulers.mainThread())
                . timeout(5, TimeUnit.SECONDS)
                .subscribe(observer);
    }

}
