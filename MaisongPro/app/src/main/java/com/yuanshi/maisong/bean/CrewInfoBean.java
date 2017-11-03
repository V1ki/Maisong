package com.yuanshi.maisong.bean;

/**
 * Created by Dengbocheng on 2017/10/31.
 * 剧组信息类
 */

public class CrewInfoBean {
    private String crewName;
    private String product_name;//出品方
    private String recipeName;//制作方
    private String director;//导演
    private String startDate;//拍摄开始时间
    private String endDate;//拍摄结束时间
    private String crewPwd;//进组密码

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCrewPwd() {
        return crewPwd;
    }

    public void setCrewPwd(String crewPwd) {
        this.crewPwd = crewPwd;
    }
}
