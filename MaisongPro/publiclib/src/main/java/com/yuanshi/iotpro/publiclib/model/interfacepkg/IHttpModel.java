package com.yuanshi.iotpro.publiclib.model.interfacepkg;

import rx.Observer;


/**
 * Created by Dengbocheng on 2017/5/24.
 */

public interface IHttpModel {
    /**
     * 用户登录
     * @param deviceID
     * @param username
     * @param password
     * @param observer
     */
    void userLogin(String deviceID, String username, String password, String appId, Observer observer);



}
