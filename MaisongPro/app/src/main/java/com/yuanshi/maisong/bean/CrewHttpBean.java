package com.yuanshi.maisong.bean;

/**
 * Created by Dengbocheng on 2017/11/27.
 */

import java.util.ArrayList;

/**
 *  "id": "28",
 "addtime": "2017-11-17 16:16",
 "status": "1",
 "uid": "50",
 "title": "OnePiece",
 "produced": "OnePiece",
 "makinger": "OnePiece",
 "stime": "1510851600",
 "etime": "1510938000",
 "pw": "123456",
 "director": "OnePiece",
 "groupid": [
 {
 "id": 1,
 "group": "33038717222914"
 */
public class CrewHttpBean {
    private String id;
    private String addTime;
    private String status;
    private String uid;
    private String title;
    private String produced;
    private String makinger;
    private String stime;
    private String etime;
    private String pwd;
    private String director;
    private ArrayList<GroupBean> groupid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduced() {
        return produced;
    }

    public void setProduced(String produced) {
        this.produced = produced;
    }

    public String getMakinger() {
        return makinger;
    }

    public void setMakinger(String makinger) {
        this.makinger = makinger;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<GroupBean> getGroupid() {
        return groupid;
    }

    public void setGroupid(ArrayList<GroupBean> groupid) {
        this.groupid = groupid;
    }
}
