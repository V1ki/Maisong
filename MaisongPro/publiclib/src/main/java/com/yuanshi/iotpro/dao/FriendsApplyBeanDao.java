package com.yuanshi.iotpro.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yuanshi.iotpro.publiclib.bean.FriendsApplyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FRIENDS_APPLY_BEAN".
*/
public class FriendsApplyBeanDao extends AbstractDao<FriendsApplyBean, Long> {

    public static final String TABLENAME = "FRIENDS_APPLY_BEAN";

    /**
     * Properties of entity FriendsApplyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Reason = new Property(1, String.class, "reason", false, "REASON");
        public final static Property State = new Property(2, int.class, "state", false, "STATE");
        public final static Property Nickname = new Property(3, String.class, "nickname", false, "NICKNAME");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Avatar = new Property(5, String.class, "avatar", false, "AVATAR");
    }


    public FriendsApplyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public FriendsApplyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FRIENDS_APPLY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: _id
                "\"REASON\" TEXT," + // 1: reason
                "\"STATE\" INTEGER NOT NULL ," + // 2: state
                "\"NICKNAME\" TEXT," + // 3: nickname
                "\"PHONE\" TEXT," + // 4: phone
                "\"AVATAR\" TEXT);"); // 5: avatar
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FRIENDS_APPLY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FriendsApplyBean entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(2, reason);
        }
        stmt.bindLong(3, entity.getState());
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(6, avatar);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FriendsApplyBean entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(2, reason);
        }
        stmt.bindLong(3, entity.getState());
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(6, avatar);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FriendsApplyBean readEntity(Cursor cursor, int offset) {
        FriendsApplyBean entity = new FriendsApplyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // reason
            cursor.getInt(offset + 2), // state
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // avatar
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FriendsApplyBean entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setReason(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setState(cursor.getInt(offset + 2));
        entity.setNickname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAvatar(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FriendsApplyBean entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FriendsApplyBean entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FriendsApplyBean entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
