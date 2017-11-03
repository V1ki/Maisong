package com.yuanshi.maisong.bean;

import java.util.ArrayList;

/**
 * Created by Dengbocheng on 2017/11/2.
 */

public class MemoireInfoBean {
    private ArrayList<String> imgUrlList;//备忘图片地址列表
    private String title;
    private String content;//备忘文本信息
    private String auther;//作者
    private String createDate;//创建时间
    private String synopsis;//内容提要

    private String buildContent;//组建好的内容

    public ArrayList<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(ArrayList<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 从文本内容中取出部分作为内容概要
     * @return
     */
    public String getSynopsis() {

        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * 把图片地址嵌入到文本内容中，查找换行/句号插入
     * @return
     */
    public String getBuildContent() {
        return buildContent;
    }

}
