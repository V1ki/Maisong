package com.yuanshi.iotpro.publiclib.utils;

/**
 * Created by Dengbocheng on 2017/6/2.
 */

public class Constant {
    public static final String SERVER_DOMAIN = "http://47.104.13.45/";//业务服务器域名

    public static final int HTTP_REQUEST_SUCCESS = 1;//http请求成功
    public static final int HTTP_REQUEST_FAILD = 0;//http请求失败
    public static final String MAIN_SH_NAME = "MySongSp";
    public static final String HAS_PUT_USER_INFO_KEY = "has_put_user_info";
    public static final String USER_PHONE_KEY ="user_phone_key";

    public static final int CHATROOM_MAX_MENBER_COUNT = 1000;//聊天室最大人数

    //请求类型
    public static final String HTTP_REQUEST_NOTICE = "Notice";//通知单请求；
    public static final String HTTP_REQUEST_MEMORANDUM = "Memorandum ";//备忘录请求；
    public static final String HTTP_REQUEST_REMIND = "Remind";//剧组通知请求；
    public static final String HTTP_REQUEST_BIGPLAN = "Bigplan";//拍摄大计划请求；
    public static final String HTTP_REQUEST_SCRIPTPAGE = "Scriptpage";//剧组扉页请求；

}
