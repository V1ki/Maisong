package com.yuanshi.iotpro.publiclib.presenter;


import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;

import java.util.List;

/**
 * Created by Dengbocheng on 2017/11/7.
 */

public interface ILoginInfoDBPresenter {
    void insertLoginInfo(LoginInfoBean loginInfoBean);
    void deleteLoginInfo(String phone);
    List<LoginInfoBean> selectAllLoginInfo();
    LoginInfoBean selectLoginInfo(String phone);
    void updateNickName(String nickname,String phone);
    void updateTrueName(String truename,String phone);
    void updateSex(String sex,String phone);
    void updateBirthday(String birthday,String phone);
    void updateQQ(String qq,String phone);
//    void updatePhone(String phone);
    void updateWeixin(String weixin,String phone);
    void updateAvatar(String avatar,String phone);
    void updateScore(String score,String phone);
    void updateLogin(String login,String phone);
//    void updateRegIp(String reg_ip);
//    void updateRegTime(String reg_time);
    void updateLastLoginIP(String last_login_ip,String phone);
    void updateLastLoginTime(String last_login_time,String phone);
    void updateStatus(String status,String phone);
    void updateLang(String lang,String phone);
    void updateAttribution(String attribution,String phone);
    void updateNote(String note,String phone);
    void updateEmail(String email,String phone);

}
