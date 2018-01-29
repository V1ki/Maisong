package com.yuanshi.iotpro.publiclib.model.interfacepkg;

import com.yuanshi.iotpro.publiclib.utils.FileCallBack;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;


/**
 * Created by Dengbocheng on 2017/5/24.
 */

public interface IHttpModel {
    void login(String phone, String verify , Observer observer);

    void getverify(String phone, Observer observer);

    void login2(String uid, Observer observer);

    void edituser(Map<String, Object> map, Observer observer);

    void logout(Observer observer);

    void appupload ( MultipartBody.Part body, Observer observer);

    void index(Observer observer);

    void searchcrew (String keyword, Observer observer);

    void doAdd(String id, String title ,String director, String produced, String makinger, String  stime, String etime,String pw,String groupid, Observer observer);

    void thecrewinfo(String id, Observer observer);

    void editusercrew(Map<String, Object> map,Observer observer);

    void joins(Map<String, Object> map,Observer observer);

    void department(String id,Observer observer);

    void usercrew(String id,Observer observer);

    void phonegetuser(String phone, String groupid,Observer observer);

    void index(String requestType, String id,Observer observer);

    void details(String requestType, String id,Observer observer);

    void del(String requestType, String id,Observer observer);

    void doAdd( String requestType, String id, Map<String, Object> map,String addtime,Observer observer);

    void download( String url,FileCallBack<ResponseBody> callBack);

    void outs (String crewId,Observer observer);

    void searchMemorandum(String cid,String keyword, Observer observer);
}
