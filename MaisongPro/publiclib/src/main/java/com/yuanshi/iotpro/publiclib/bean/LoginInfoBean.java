package com.yuanshi.iotpro.publiclib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Dengbocheng on 2017/11/7.
 * 登录信息类
 */
@Entity
public class LoginInfoBean{
    @Id
    private Long _id;
    private String uid;
    private String nickname;
    private String truename;
    private String sex;
    private String birthday;
    private String qq;
    @Unique @NotNull
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
    @Generated(hash = 1177350970)
    public LoginInfoBean(Long _id, String uid, String nickname, String truename,
            String sex, String birthday, String qq, @NotNull String phone,
            String weixin, String email, String avatar, String score, String login,
            String reg_ip, String reg_time, String last_login_ip,
            String last_login_time, String status, String lang, String attribution,
            String note) {
        this._id = _id;
        this.uid = uid;
        this.nickname = nickname;
        this.truename = truename;
        this.sex = sex;
        this.birthday = birthday;
        this.qq = qq;
        this.phone = phone;
        this.weixin = weixin;
        this.email = email;
        this.avatar = avatar;
        this.score = score;
        this.login = login;
        this.reg_ip = reg_ip;
        this.reg_time = reg_time;
        this.last_login_ip = last_login_ip;
        this.last_login_time = last_login_time;
        this.status = status;
        this.lang = lang;
        this.attribution = attribution;
        this.note = note;
    }
    @Generated(hash = 410655696)
    public LoginInfoBean() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getTruename() {
        return this.truename;
    }
    public void setTruename(String truename) {
        this.truename = truename;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getQq() {
        return this.qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getWeixin() {
        return this.weixin;
    }
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getScore() {
        return this.score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getReg_ip() {
        return this.reg_ip;
    }
    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }
    public String getReg_time() {
        return this.reg_time;
    }
    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }
    public String getLast_login_ip() {
        return this.last_login_ip;
    }
    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }
    public String getLast_login_time() {
        return this.last_login_time;
    }
    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLang() {
        return this.lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public String getAttribution() {
        return this.attribution;
    }
    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }

}
