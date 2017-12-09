package com.yuanshi.iotpro.publiclib.presenter;

import android.view.View;

import com.yuanshi.iotpro.publiclib.utils.FileCallBack;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;

/**
 * Created by Dengbocheng on 2017/11/6.
 */

public interface IHttpPresenter {

    /**
     * 用户登录
     * @param phone
     * @param verify
     */
    void login(String phone, String verify);

    /**
     * 回去手机验证码
     * @param phone
     */
    void getverify(String phone);

    /**
     * uid登录
     * @param uid
     */
    void login2(String uid);

    /**
     * 个人资料修改
     */
    void edituser(Map<String, Object> map);
    /**
     * 退出登录
     */
    void logout();

    /**
     * 上传图片
     */
    void appupload ( MultipartBody.Part body,String filePath);

    /**
     * 获取用户当前剧组
     */
    void index();

    /**
     * 搜索剧组
     * @param keyword
     */
    void searchcrew (String keyword);

    /**
     * 创建或修改剧组
     * @param id
     * @param title
     * @param director
     * @param produced
     * @param makinger
     * @param stime
     * @param etime
     * @param pw
     * @param groupid
     */
    void doAdd(String id, String title ,String director, String produced, String makinger, String  stime, String etime,String pw,String groupid);

    /**
     * 获取我在剧组信息
     * @param id
     */
    void thecrewinfo(String id);

    /**
     * 编辑我在剧组信息
     * @param map
     */
    void editusercrew(Map<String, Object> map);

    /**
     * 加入剧组
     * @param map
     */
    void joins(Map<String, Object> map);

    /**
     * 获取部门列表和部门下的职位列表
     * @param id 剧组id
     */
    void department(String id);

    /**
     * 获取剧组通讯录
     * @param id
     */
    void usercrew(String id);

    /**
     * 获取用户信息
     * @param phone
     * @param groupid
     */
    void phonegetuser(String phone, String groupid);

    /**
     * 剧组信息列表
     * @param requestType 申请类型
     * @param id 剧组id
     */
    void index(String requestType, String id);

    /**
     * 剧组信息详情
     * @param requestType 申请类型
     * @param id 信息id
     */
    void details(String requestType, String id);

    /**
     * 删除剧组信息
     * @param requestType 申请类型
     * @param id 信息id
     */
    void del(String requestType, String id);

    /**
     * 添加剧组信息
     * @param requestType 申请类型
     * @param id id
     * @param map 参数map： title、 content、cid 剧组ID、pics 图组 ,号分割 例: 1.jpg,2.jpg,3.jpg,、status [选填，默认1 ]
     */
    void doAdd( String requestType, String id, Map<String, Object> map);

    //文件下载
    void download(String url,String desDir, String destFileName,View convertView);
}
