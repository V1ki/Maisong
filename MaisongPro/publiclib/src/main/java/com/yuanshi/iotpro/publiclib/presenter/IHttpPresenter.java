package com.yuanshi.iotpro.publiclib.presenter;

/**
 * Created by Dengbocheng on 2017/11/6.
 */

public interface IHttpPresenter {

    /**
     * 用户登录
     * @param phone
     * @param verify
     */
    void login(String phone, String verify);


    void getverify(String phone);

    void login2(String uid);

}
