package com.yuanshi.iotpro.daoutils;

import android.content.Context;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class LoginBeanDaoUtil {
    private static final String TAG = UserBeanDaoUtil.class.getSimpleName();
    private DaoManager mManager;

    public LoginBeanDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成meizi记录的插入，如果表未创建，先创建Meizi表
     * @param userInfoBean
     * @return
     */
    public boolean insertLoginInfo(LoginInfoBean userInfoBean){
        boolean flag = false;
        flag = mManager.getDaoSession().getLoginInfoBeanDao().insert(userInfoBean) == -1 ? false : true;
        Log.i(TAG, "insert  :" + flag + "-->" + userInfoBean.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @return
     */
    public boolean insertMultLoginInfo(final List<LoginInfoBean> loginInfoBeanList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (LoginInfoBean userInfoBean : loginInfoBeanList) {
                        mManager.getDaoSession().insertOrReplace(userInfoBean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            CrashReport.postCatchedException(e);
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param loginInfoBean
     * @return
     */
    public boolean updateUserInfo(LoginInfoBean loginInfoBean){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(loginInfoBean);
            flag = true;
        }catch (Exception e){
            CrashReport.postCatchedException(e);
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param loginInfoBean
     * @return
     */
    public boolean deleteUserInfo(LoginInfoBean loginInfoBean){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(loginInfoBean);
            flag = true;
        }catch (Exception e){
            CrashReport.postCatchedException(e);
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(LoginInfoBean.class);
            flag = true;
        }catch (Exception e){
            CrashReport.postCatchedException(e);
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<LoginInfoBean> queryAllUserInfo(){
        return mManager.getDaoSession().loadAll(LoginInfoBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public LoginInfoBean qeuryUserInfo(Long key){
        return mManager.getDaoSession().load(LoginInfoBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<LoginInfoBean> queryUserInfoByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(LoginInfoBean.class, sql, conditions);
    }

//    /**
//     * 使用queryBuilder进行查询
//     * @return
//     */
//    public List<TestBean> queryMeiziByQueryBuilder(long id){
//        QueryBuilder<TestBean> queryBuilder = mManager.getDaoSession().queryBuilder(TestBean.class);
//        return queryBuilder.where(TestBean.Properties._id.eq(id)).list();
//    }
}
