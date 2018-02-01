package com.yuanshi.iotpro.daoutils;

import android.content.Context;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class UserBeanDaoUtil {

    private static final String TAG = UserBeanDaoUtil.class.getSimpleName();
    private DaoManager mManager;

    public UserBeanDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成meizi记录的插入，如果表未创建，先创建Meizi表
     * @param userInfoBean
     * @return
     */
    public boolean insertUserInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        flag = mManager.getDaoSession().getUserInfoBeanDao().insert(userInfoBean) == -1 ? false : true;
        Log.i(TAG, "insert  :" + flag + "-->" + userInfoBean.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @return
     */
    public boolean insertMultUserInfo(final List<UserInfoBean> userInfoBeanList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserInfoBean userInfoBean : userInfoBeanList) {
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
     * @param userInfoBean
     * @return
     */
    public boolean updateUserInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(userInfoBean);
            flag = true;
        }catch (Exception e){
            CrashReport.postCatchedException(e);
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param userInfoBean
     * @return
     */
    public boolean deleteUserInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(userInfoBean);
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
            mManager.getDaoSession().deleteAll(UserInfoBean.class);
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
    public List<UserInfoBean> queryAllUserInfo(){
        return mManager.getDaoSession().loadAll(UserInfoBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public UserInfoBean qeuryUserInfo(Long key){
        return mManager.getDaoSession().load(UserInfoBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserInfoBean> queryUserInfoByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(UserInfoBean.class, sql, conditions);
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
