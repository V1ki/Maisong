package com.yuanshi.iotpro.publiclib.model;

import android.content.ContentValues;


import com.yuanshi.iotpro.publiclib.model.interfacepkg.IDatabaseModel;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public class IDatabaseModelImpl implements IDatabaseModel {

    @Override
    public boolean insertData(DataSupport dataSupport) {
        return dataSupport.save();
    }

    @Override
    public int delete(Class clazz,String... conditions) {
        if(conditions == null || conditions.length == 0){
            return DataSupport.deleteAll(clazz);
        }else{
            return DataSupport.deleteAll(clazz,conditions);
        }
    }

    @Override
    public int update(Class clazz ,ContentValues value, String... conditions) {
        return DataSupport.updateAll(clazz, value, conditions);
    }

    @Override
    public List<? extends DataSupport> select(Class clazz, String... conditions) {
        if(conditions == null || conditions.length == 0){
            return DataSupport.findAll(clazz,true);
        }else{
            return DataSupport.where(conditions).find(clazz,true);
        }

    }
}
