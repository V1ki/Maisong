package com.yuanshi.iotpro.publiclib.bean;

/**
 * Created by Dengbocheng on 2017/11/7.
 * 用户个人信息类
 */

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.HashMap;
import java.util.Map;

/**
 * name
 sex //0女，1男
 birthday
 qq
 phone //备用
 weixin
 email
 avatar
 */

@Entity
public class UserInfoBean {
    @Id
    private Long _id;
    private String name ="";
    private int sex = 0;
    private String birthday ="";
    private String qq ="";
    @Unique @NotNull
    private String phone ="";
    private String weixin ="";
    private String email ="";
    private String avatar ="";
    private String nickname ="";
    private String truename = "";


    @Generated(hash = 654103997)
    public UserInfoBean(Long _id, String name, int sex, String birthday, String qq,
            @NotNull String phone, String weixin, String email, String avatar,
            String nickname, String truename) {
        this._id = _id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.qq = qq;
        this.phone = phone;
        this.weixin = weixin;
        this.email = email;
        this.avatar = avatar;
        this.nickname = nickname;
        this.truename = truename;
    }


    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }


    public Long get_id() {
        return this._id;
    }


    public void set_id(Long _id) {
        this._id = _id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getSex() {
        return this.sex;
    }


    public void setSex(int sex) {
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
}
