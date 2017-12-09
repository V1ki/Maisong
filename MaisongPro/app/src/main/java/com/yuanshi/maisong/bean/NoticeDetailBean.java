package com.yuanshi.maisong.bean;

import java.util.ArrayList;

/**
 * Created by Dengbocheng on 2017/11/30.
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
        "http:\/\/47.104.13.45\/Uploads\/Download\/2017-11-20\/5a128f2ce6d61.jpg"
    ],
    "file": "",
    "crewtitle": "OnePiece",
    "author": "SVK\u4e07\u263a\ufe0f",
    "readed": 1
}
 */
public class NoticeDetailBean {
    private String id;
    private String cid;
    private String uid;
    private String title;
    private String content;
    private String status;
    private String addtime;
    private String[] pics;
    private String crewtitle;
    private String author;
    private int readed;
    private ArrayList<String> file;//文件列表

    public ArrayList<String> getFile() {
        return file;
    }

    public void setFile(ArrayList<String> file) {
        this.file = file;
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

    public String getCrewtitle() {
        return crewtitle;
    }

    public void setCrewtitle(String crewtitle) {
        this.crewtitle = crewtitle;
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
}
