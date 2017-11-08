package com.yuanshi.iotpro.publiclib.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Dengbocheng on 2017/11/7.
 * 登录信息类
 */

/**
 *     "uid": "51",
 "nickname": null,
 "truename": null,
 "sex": "0",
 "birthday": "0",
 "qq": "",
 "phone": "13590461973",
 "weixin": null,
 "email": "",
 "avatar": null,
 "score": "0",
 "login": "0",
 "reg_ip": "11630",
 "reg_time": "1509962600",
 "last_login_ip": "0",
 "last_login_time": "0",
 "status": "1",
 "lang": null,
 "attribution": null,
 "note": null
 */
public class LoginInfoBean extends DataSupport {
    private String uid;
    private String nickname;
    private String truename;
    private String sex;
    private String birthday;
    private String qq;
    private String phone;
    private String weixin;
    private String email;
    private String avatar;//头像
    private String score;
    private String login;
    private String reg_ip;
    private String reg_time;
    private String last_login_ip;
    private String last_login_time;
    private String status;
    private String lang;
    private String attribution;
    private String note;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
