package com.yuanshi.maisong.bean;

/**
 * Created by Dengbocheng on 2017/11/28.
 * 我在剧组后台信息解析类
 */

import java.util.HashMap;
import java.util.Map;

/**
 * {"id":"41","addtime":"1510906660","uid":"51","cid":"28","department":"1","position":"5","status":"1","username":"123we","phone":"13590461973","phoneshow":"1"}
 */


public class MyProfileInfo {
    private String id;
    private String addtime;
    private String uid;
    private String cid;
    private String department;
    private String position;
    private String status;
    private String username;
    private String phone;
    private String phoneshow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneshow() {
        return phoneshow;
    }

    public void setPhoneshow(String phoneshow) {
        this.phoneshow = phoneshow;
    }

    public Map<String, Object> getParamsMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("department",department);
        map.put("position",position);
        map.put("username",username);
        map.put("phone",phone);
        map.put("phoneshow",phoneshow);
        return map;
    }
}
