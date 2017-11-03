package com.yuanshi.maisong.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Dengbocheng on 2017/10/23.
 */

public class MsgBean extends DataSupport {
    private String title;
    private String msg;
    private String dateTime;
    private String headIconUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }
}
