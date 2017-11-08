package com.yuanshi.iotpro.publiclib.presenter;


import android.content.ContentValues;

import com.yuanshi.iotpro.publiclib.activity.IBaseView;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.model.IDatabaseModelImpl;
import com.yuanshi.iotpro.publiclib.model.interfacepkg.IDatabaseModel;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Dengbocheng on 2017/11/7.
 */

public class ILoginInfoDBPresenterIml implements ILoginInfoDBPresenter {
    private IDatabaseModel iDatabaseModel;
    private IBaseView view;
    public ILoginInfoDBPresenterIml(IBaseView view){
        this.view = view;
        this.iDatabaseModel = new IDatabaseModelImpl();
    }
    @Override
    public void insertLoginInfo(final LoginInfoBean loginInfoBean) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                iDatabaseModel.insertData(loginInfoBean);
            }
        });
    }

    @Override
    public void deleteLoginInfo(final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                iDatabaseModel.delete(LoginInfoBean.class,"phone = ?",phone);
            }
        });
    }

    @Override
    public List <LoginInfoBean> selectLoginInfo() {
        try {
            return MyApplication.THREAD_EXCUTER.submit(new Callable<List<LoginInfoBean>>() {
                @Override
                public List<LoginInfoBean> call() throws Exception {
                    List<LoginInfoBean> list = (List<LoginInfoBean>) iDatabaseModel.select(LoginInfoBean.class);
                    return list;
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateNickName(final String nickname, final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("nickname",nickname);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateTrueName(final String truename, final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("truename",truename);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateSex(final String sex, final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("sex",sex);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateBirthday(final String birthday,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("birthday",birthday);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateQQ(final String qq,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("qq",qq);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateWeixin(final String weixin,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("weixin",weixin);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateAvatar(final String avatar,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("avatar",avatar);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateScore(final String score,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("score",score);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateLogin(final String login,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("login",login);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateLastLoginIP(final String last_login_ip,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("last_login_ip",last_login_ip);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateLastLoginTime(final String last_login_time,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("last_login_time",last_login_time);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateStatus(final String status,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("status",status);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateLang(final String lang,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("lang",lang);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateAttribution(final String attribution,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("attribution",attribution);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }

    @Override
    public void updateNote(final String note,final String phone) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("note",note);
                iDatabaseModel.update(LoginInfoBean.class,values,"phone = ?",phone);
            }
        });
    }


}
