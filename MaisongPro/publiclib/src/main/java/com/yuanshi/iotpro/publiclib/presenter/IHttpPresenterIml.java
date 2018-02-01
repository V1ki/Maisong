package com.yuanshi.iotpro.publiclib.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuanshi.iotpro.publiclib.activity.IBaseView;
import com.yuanshi.iotpro.publiclib.bean.Status;
import com.yuanshi.iotpro.publiclib.model.IHttpModelImpl;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IHttpModel;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.FileCallBack;
import com.yuanshi.iotpro.publiclib.utils.YLog;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observer;

/**
 * Created by Dengbocheng on 2017/11/6.
 */

public class IHttpPresenterIml implements IHttpPresenter {
    private IBaseView view;
    private IHttpModel serverModel;
    private Context context;
    public IHttpPresenterIml(IBaseView view,Context context) {
        this.context = context;
        this.view = view;
        this.serverModel = (IHttpModel) new IHttpModelImpl(context);
    }
    @Override
    public void login(String phone, String verify) {
        serverModel.login(phone, verify, new Observer<Status>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.onError("login",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("login",status.getInfo(),status.getData());
                }else{
                    view.onHttpFaild("login",status.getInfo(),null);
                }

            }
        });
    }

    @Override
    public void getverify(String phone) {
        serverModel.getverify(phone, new Observer<Status>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.onError("getverify",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("getverify",status.getInfo(),status.getData());
                }else{
                    view.onHttpFaild("getverify",status.getInfo(),null);
                }

            }
        });
    }

    @Override
    public void login2(String uid) {
        serverModel.login2(uid, new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onError("login2",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    YLog.e("onHttpSuccess~~~login2:"+status.getInfo());
                    view.onHttpSuccess("login2",status.getInfo(),status.getData());
                }else{
                    YLog.e("onHttpFaild~~~login2:"+status.getInfo());
                    view.onHttpFaild("login2",status.getInfo(),null);
                }

            }
        });
    }

    /**
     * 提交个人信息
     */
    @Override
    public void edituser(Map<String,Object> map) {
        serverModel.edituser(map,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                YLog.e("edituser~~~OnError:"+e.getMessage());
                view.onError("edituser",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("edituser",status.getInfo(),status.getData());
                    YLog.e("edituser~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("edituser",status.getInfo(),null);
                    YLog.e("edituser~~~onHttpFaild:"+status.getInfo());
                }

            }
        });
    }

    @Override
    public void logout() {
        serverModel.logout(new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                YLog.e("logout~~~OnError:"+e.getMessage());
                view.onError("logout",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("logout",status.getInfo(),status.getData());
                    YLog.e("logout~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("logout",status.getInfo(),null);
                    YLog.e("logout~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void appupload(MultipartBody.Part body, final String filePath) {
        serverModel.appupload(body,new Observer<Status>() {

            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                YLog.e("appupload~~~OnError:"+e.getMessage());
                view.onError("appupload",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("appupload",filePath,status.getData());
                    YLog.e("appupload~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("appupload",status.getInfo(),null);
                    YLog.e("appupload~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void index() {
        serverModel.index(new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("index",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("index",status.getInfo(),status.getData());
                    YLog.e("index~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("index",status.getInfo(),null);
                    YLog.e("index~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void searchcrew(String keyword) {
        serverModel.searchcrew(keyword,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("searchcrew",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("searchcrew",status.getInfo(),status.getData());
                    YLog.e("searchcrew~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("searchcrew",status.getInfo(),null);
                    YLog.e("searchcrew~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void doAdd(String id, String title, String director, String produced, String makinger, String stime, String etime, String pw, String groupid) {
        serverModel.doAdd(id,title,director,produced,makinger,stime,etime,pw,groupid,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("doAdd",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("doAdd",status.getInfo(),status.getData());
                    YLog.e("doAdd~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("doAdd",status.getInfo(),null);
                    YLog.e("doAdd~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void thecrewinfo(String id) {
        serverModel.thecrewinfo(id,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("thecrewinfo",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("thecrewinfo",status.getInfo(),status.getData());
                    YLog.e("thecrewinfo~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("thecrewinfo",status.getInfo(),null);
                    YLog.e("thecrewinfo~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }



    @Override
    public void editusercrew(Map<String, Object> map) {
        serverModel.editusercrew(map,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("editusercrew",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("editusercrew",status.getInfo(),status.getData());
                    YLog.e("editusercrew~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("editusercrew",status.getInfo(),null);
                    YLog.e("editusercrew~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void joins(Map<String, Object> map) {
        serverModel.joins(map,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("joins",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("joins",status.getInfo(),status.getData());
                    YLog.e("joins~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("joins",status.getInfo(),null);
                    YLog.e("joins~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void department(String id) {
        serverModel.department(id,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("department",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("department",status.getInfo(),new Gson().toJson(status.getData()));
                }else{
                    view.onHttpFaild("department",status.getInfo(),null);
                    YLog.e("department~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void usercrew(String id) {
        serverModel.usercrew(id,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("usercrew",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("usercrew",status.getInfo(),status.getData());
                    YLog.e("usercrew~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("usercrew",status.getInfo(),null);
                    YLog.e("usercrew~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void phonegetuser(final String phone,final String groupid) {
        serverModel.phonegetuser(phone,groupid,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("phonegetuser",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    if(!TextUtils.isEmpty(phone)){
                        view.onHttpSuccess("phonegetuser",phone,status.getData());
                        YLog.e("phonegetuser~~~onHttpSuccess:"+status.getInfo());
                    }else{
                        view.onHttpSuccess("phonegetuser",groupid,status.getData());
                        YLog.e("phonegetuser~~~onHttpSuccess:"+status.getInfo());
                    }

                }else{
                    view.onHttpFaild("phonegetuser",status.getInfo(),null);
                    YLog.e("phonegetuser~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void index(final String requestType, String id) {
        serverModel.index(requestType,id,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError(requestType+":index",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                YLog.e(requestType+":index ~~~onHttpSuccess:"+status.getInfo());
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess(requestType+":index",status.getInfo(),status.getData());
                    YLog.e(requestType+":index ~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild(requestType+":index",status.getInfo(),null);
                    YLog.e(requestType+":index ~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void details(final String requestType, String id) {
        serverModel.details(requestType,id,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError(requestType+":details",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess(requestType+":details",status.getInfo(),status.getData());
                    YLog.e(requestType+":details ~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild(requestType+":details",status.getInfo(),null);
                    YLog.e(requestType+":details ~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void del(final String requestType, String id) {
        serverModel.del(requestType,id,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError(requestType+":del",e.getMessage(),null);
            }

            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess(requestType+":del",status.getInfo(),status.getData());
                    YLog.e(requestType+":del ~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild(requestType+":del",status.getInfo(),null);
                    YLog.e(requestType+":del ~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void doAdd(final String requestType, String id, Map<String, Object> map,String addtime) {
        serverModel.doAdd(requestType,id,map,addtime,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError(requestType+":doAdd",e.getMessage(),null);
            }
            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess(requestType+":doAdd",status.getInfo(),status.getData());
                    YLog.e("notice:doAdd ~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild(requestType+":doAdd",status.getInfo(),null);
                    YLog.e("notice:doAdd ~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void download(String url,String desDir, final String destFileName,final View convertView) {
        serverModel.download(url, new FileCallBack<ResponseBody>(desDir,destFileName) {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                view.onDownloadComplete(convertView,destFileName);
            }
            @Override
            public void progress(long progress, long total) {
                view.onDownloadProgress(convertView, progress, total);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onDownloadError(convertView, e,destFileName);
            }
        });
    }

    @Override
    public void outs(String crewId) {
        serverModel.outs(crewId,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("outs",e.getMessage(),null);
            }
            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("outs",status.getInfo(),status.getData());
                    YLog.e("outs ~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("outs",status.getInfo(),null);
                    YLog.e("outs ~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

    @Override
    public void searchMemorandum(String cid, String keyword) {
        serverModel.searchMemorandum(cid,keyword,new Observer<Status>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                CrashReport.postCatchedException(e);
                view.onError("searchMemorandum",e.getMessage(),null);
            }
            @Override
            public void onNext(Status status) {
                if(status.getStatus() == Constant.HTTP_REQUEST_SUCCESS){
                    view.onHttpSuccess("searchMemorandum",status.getInfo(),status.getData());
                    YLog.e("searchMemorandum ~~~onHttpSuccess:"+status.getInfo());
                }else{
                    view.onHttpFaild("searchMemorandum",status.getInfo(),null);
                    YLog.e("searchMemorandum ~~~onHttpFaild:"+status.getInfo());
                }
            }
        });
    }

}
