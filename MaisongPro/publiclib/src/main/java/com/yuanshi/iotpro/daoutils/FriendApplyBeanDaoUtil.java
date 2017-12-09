package com.yuanshi.iotpro.daoutils;

import android.content.Context;
import android.util.Log;

import com.yuanshi.iotpro.publiclib.bean.FriendsApplyBean;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class FriendApplyBeanDaoUtil {

    private static final String TAG = FriendApplyBeanDaoUtil.class.getSimpleName();
    private DaoManager mManager;

    public FriendApplyBeanDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成meizi记录的插入，如果表未创建，先创建Meizi表
     * @param friendsApplyBean
     * @return
     */
    public boolean insertFrdApplyInfo(FriendsApplyBean friendsApplyBean){
        boolean flag = false;
        flag = mManager.getDaoSession().getFriendsApplyBeanDao().insert(friendsApplyBean) == -1 ? false : true;
        Log.i(TAG, "insert  :" + flag + "-->" + friendsApplyBean.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @return
     */
    public boolean insertMultFrdapply(final List<FriendsApplyBean> frApplyList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (FriendsApplyBean friendsApplyBean : frApplyList) {
                        mManager.getDaoSession().insertOrReplace(friendsApplyBean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param friendsApplyBean
     * @return
     */
    public boolean updateFrdApplyInfo(FriendsApplyBean friendsApplyBean){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(friendsApplyBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param userInfoBean
     * @return
     */
    public boolean deleteFrdApplyInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(userInfoBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
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
            mManager.getDaoSession().deleteAll(FriendsApplyBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<FriendsApplyBean> queryAllFrdApplyInfo(){
        return mManager.getDaoSession().loadAll(FriendsApplyBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public FriendsApplyBean qeuryFrdApplyInfo(Long key){
        return mManager.getDaoSession().load(FriendsApplyBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserInfoBean> queryFrdApplyByNativeSql(String sql, String[] conditions){
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
