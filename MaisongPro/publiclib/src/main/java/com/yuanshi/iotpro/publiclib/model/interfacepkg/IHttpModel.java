package com.yuanshi.iotpro.publiclib.model.interfacepkg;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;


/**
 * Created by Dengbocheng on 2017/5/24.
 */

public interface IHttpModel {
    /**
     * 用户登录
     * @param phone
     * @param verify
     * @param observer
     */
    void login(String phone, String verify , Observer observer);

    void getverify(String phone, Observer observer);

    void login2(String uid, Observer observer);

    void edituser(Map<String, String> map, Observer observer);

    void logout(Observer observer);
}
