package com.yuanshi.maisong.bean;

/**
 * Created by Dengbocheng on 2017/11/7.
 * 用户个人信息类
 */

import android.text.TextUtils;

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

public class UserInfoBean {
    private String name ="";
    private String sex ="";
    private String birthday ="";
    private String qq ="";
    private String phone ="";
    private String weixin ="";
    private String email ="";
    private String avatar ="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String ,String> getQureyMap(){
        HashMap<String,String> map = new HashMap<>();
        if(!TextUtils.isEmpty(name)){
            map.put("name",this.name);
        }
        if(!TextUtils.isEmpty(sex)){
            map.put("sex",this.sex);
        }
        if(!TextUtils.isEmpty(birthday)){
            map.put("birthday",this.birthday);
        }
        if(!TextUtils.isEmpty(qq)){
            map.put("qq",this.qq);
        }
        if(!TextUtils.isEmpty(phone)){
            map.put("phone",this.phone);
        }
        if(!TextUtils.isEmpty(weixin)){
            map.put("weixin",this.weixin);
        }
        if(!TextUtils.isEmpty(email)){
            map.put("email",this.email);
        }
        if(!TextUtils.isEmpty(avatar)){
            map.put("avatar",this.avatar);
        }
        return map;
    }
}
