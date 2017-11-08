package com.yuanshi.iotpro.publiclib.presenter;

import android.content.Context;

import com.yuanshi.iotpro.publiclib.activity.IBaseView;
import com.yuanshi.iotpro.publiclib.bean.Status;
import com.yuanshi.iotpro.publiclib.model.IHttpModelImpl;
import com.yuanshi.iotpro.publiclib.model.http.ApiManager;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IHttpModel;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;

/**
 * Created by Dengbocheng on 2017/11/6.
 */

public class IHttpPresenterIml implements IHttpPresenter {
    private IBaseView view;
    private IHttpModel serverModel;
    private Context context;
    public IHttpPresenterIml(IBaseView view,Context context) {
        this.context = context;
        this.view = view;
        this.serverModel = (IHttpModel) new IHttpModelImpl(context);
    }
    @Override
    public void login(String phone, String verify) {
        serverModel.login(phone, verify, new Observer<Status>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.onError("login",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("login",status.getInfo(),status.getData());
                }else{
                    view.onHttpFaild("login",status.getInfo(),null);
                }

            }
        });
    }

    @Override
    public void getverify(String phone) {
        serverModel.getverify(phone, new Observer<Status>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.onError("getverify",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("getverify",status.getInfo(),status.getData());
                }else{
                    view.onHttpFaild("getverify",status.getInfo(),null);
                }

            }
        });
    }

    @Override
    public void login2(String uid) {
        serverModel.login2(uid, new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onError("login2",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("login2",status.getInfo(),status.getData());
                }else{
                    view.onHttpFaild("login2",status.getInfo(),null);
                }

            }
        });
    }

    /**
     * 提交个人信息
     */
    @Override
    public void edituser(Map<String, String> map) {
        serverModel.edituser(map,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                YLog.e("edituser~~~OnError:"+e.getMessage());
                view.onError("edituser",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("edituser",status.getInfo(),status.getData());
                    YLog.e("edituser~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("edituser",status.getInfo(),null);
                    YLog.e("edituser~~~onHttpFaild:"+status.getInfo());
                }

            }
        });
    }

    @Override
    public void logout() {
        serverModel.logout(new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                YLog.e("logout~~~OnError:"+e.getMessage());
                view.onError("edituser",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("logout",status.getInfo(),status.getData());
                    YLog.e("logout~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("edituser",status.getInfo(),null);
                    YLog.e("logout~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }
}
