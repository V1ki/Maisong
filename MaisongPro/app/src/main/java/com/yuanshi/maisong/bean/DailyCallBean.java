package com.yuanshi.maisong.bean;

/**
 * Created by Dengbocheng on 2017/10/31.
 * 每日通知单信息类
 */
/*
 {
        "id": "8",
        "cid": "28",
        "uid": "50",
        "title": "11.20",
        "content": "11.20",
        "status": "1",
        "addtime": "2017-11-20 16:15",
        "pics": [
            "http://47.104.13.45/Uploads/Download/2017-11-20/5a128f2ce6d61.jpg"
        ],
        "file": "",
        "author": "SVK万☺️",
        "readed": 1,
        "allcount": "3",
        "noreadcount": -1
    }
 */
public class DailyCallBean {
    private String id;
    private String cid;
    private String uid;
    private String title;
    private String content;
    private String status;
    private String addtime;
    private String[] pics;
    private String[] file;
    private String author;
    private int readed;
    private String allcount;

    public String[] getFile() {
        return file;
    }

    public void setFile(String[] file) {
        this.file = file;
    }

    private boolean hasRead = false;

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public int getNoreadcount() {
        return noreadcount;
    }

    public void setNoreadcount(int noreadcount) {
        this.noreadcount = noreadcount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public String getAllcount() {
        return allcount;
    }

    public void setAllcount(String allcount) {
        this.allcount = allcount;
    }

    private int  noreadcount;

}
