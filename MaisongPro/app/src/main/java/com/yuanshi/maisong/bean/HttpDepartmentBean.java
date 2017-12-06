package com.yuanshi.maisong.bean;

import java.util.ArrayList;

/**
 * Created by Dengbocheng on 2017/11/29.
 */
public class HttpDepartmentBean {
    private String id ;
    private String title;
    private String groupid;
    private ArrayList<AuthBean> auth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public ArrayList<AuthBean> getAuth() {
        return auth;
    }

    public void setAuth(ArrayList<AuthBean> auth) {
        this.auth = auth;
    }
}
