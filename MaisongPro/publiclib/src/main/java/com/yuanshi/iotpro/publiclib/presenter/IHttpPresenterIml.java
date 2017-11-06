package com.yuanshi.iotpro.publiclib.presenter;

import com.yuanshi.iotpro.publiclib.activity.IBaseView;
import com.yuanshi.iotpro.publiclib.bean.Status;
import com.yuanshi.iotpro.publiclib.model.IHttpModelImpl;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IHttpModel;

import rx.Observer;

/**
 * Created by Dengbocheng on 2017/11/6.
 */

public class IHttpPresenterIml implements IHttpPresenter {
    private IBaseView view;
    private IHttpModel serverModel;
    public IHttpPresenterIml(IBaseView view) {
        this.view = view;
        this.serverModel = (IHttpModel) new IHttpModelImpl();
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
                view.onHttpSuccess("login",status.getInfo(),status.getData());
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
                view.onHttpSuccess("getverify",status.getInfo(),status.getData());
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
                view.onHttpSuccess("login2",status.getInfo(),status.getData());
            }
        });
    }
}
