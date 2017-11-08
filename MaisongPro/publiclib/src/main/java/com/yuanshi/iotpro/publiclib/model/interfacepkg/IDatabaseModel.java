package com.yuanshi.iotpro.publiclib.model.interfacepkg;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public interface IDatabaseModel {
    /**
     * 插入数据
     * @param dataSupport 插入对象
     * @return 操作结果
     */
    boolean insertData(DataSupport dataSupport);

    /**
     * 删除
     * @param clazz //操作的表
     * @param conditions //删除条件
     * @return 受影响的数据
     */
    int delete(Class clazz, String... conditions);


    /**
     * 修改数据
     * @param clazz 操作的表
     * @param value 修改后的数据
     * @param conditions 筛选条件
     */
    int update(Class clazz, ContentValues value, String... conditions);

    /**
     * 查询
     * @param clazz 表
     * @param conditions 查询条件
     * @return
     */
    List<? extends DataSupport>  select(Class clazz, String... conditions);

}
