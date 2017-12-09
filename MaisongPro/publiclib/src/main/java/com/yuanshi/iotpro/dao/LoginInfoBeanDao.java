package com.yuanshi.iotpro.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOGIN_INFO_BEAN".
*/
public class LoginInfoBeanDao extends AbstractDao<LoginInfoBean, Long> {

    public static final String TABLENAME = "LOGIN_INFO_BEAN";

    /**
     * Properties of entity LoginInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Uid = new Property(1, String.class, "uid", false, "UID");
        public final static Property Nickname = new Property(2, String.class, "nickname", false, "NICKNAME");
        public final static Property Truename = new Property(3, String.class, "truename", false, "TRUENAME");
        public final static Property Sex = new Property(4, String.class, "sex", false, "SEX");
        public final static Property Birthday = new Property(5, String.class, "birthday", false, "BIRTHDAY");
        public final static Property Qq = new Property(6, String.class, "qq", false, "QQ");
        public final static Property Phone = new Property(7, String.class, "phone", false, "PHONE");
        public final static Property Weixin = new Property(8, String.class, "weixin", false, "WEIXIN");
        public final static Property Email = new Property(9, String.class, "email", false, "EMAIL");
        public final static Property Avatar = new Property(10, String.class, "avatar", false, "AVATAR");
        public final static Property Score = new Property(11, String.class, "score", false, "SCORE");
        public final static Property Login = new Property(12, String.class, "login", false, "LOGIN");
        public final static Property Reg_ip = new Property(13, String.class, "reg_ip", false, "REG_IP");
        public final static Property Reg_time = new Property(14, String.class, "reg_time", false, "REG_TIME");
        public final static Property Last_login_ip = new Property(15, String.class, "last_login_ip", false, "LAST_LOGIN_IP");
        public final static Property Last_login_time = new Property(16, String.class, "last_login_time", false, "LAST_LOGIN_TIME");
        public final static Property Status = new Property(17, String.class, "status", false, "STATUS");
        public final static Property Lang = new Property(18, String.class, "lang", false, "LANG");
        public final static Property Attribution = new Property(19, String.class, "attribution", false, "ATTRIBUTION");
        public final static Property Note = new Property(20, String.class, "note", false, "NOTE");
    }


    public LoginInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public LoginInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOGIN_INFO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: _id
                "\"UID\" TEXT," + // 1: uid
                "\"NICKNAME\" TEXT," + // 2: nickname
                "\"TRUENAME\" TEXT," + // 3: truename
                "\"SEX\" TEXT," + // 4: sex
                "\"BIRTHDAY\" TEXT," + // 5: birthday
                "\"QQ\" TEXT," + // 6: qq
                "\"PHONE\" TEXT NOT NULL UNIQUE ," + // 7: phone
                "\"WEIXIN\" TEXT," + // 8: weixin
                "\"EMAIL\" TEXT," + // 9: email
                "\"AVATAR\" TEXT," + // 10: avatar
                "\"SCORE\" TEXT," + // 11: score
                "\"LOGIN\" TEXT," + // 12: login
                "\"REG_IP\" TEXT," + // 13: reg_ip
                "\"REG_TIME\" TEXT," + // 14: reg_time
                "\"LAST_LOGIN_IP\" TEXT," + // 15: last_login_ip
                "\"LAST_LOGIN_TIME\" TEXT," + // 16: last_login_time
                "\"STATUS\" TEXT," + // 17: status
                "\"LANG\" TEXT," + // 18: lang
                "\"ATTRIBUTION\" TEXT," + // 19: attribution
                "\"NOTE\" TEXT);"); // 20: note
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOGIN_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoginInfoBean entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(2, uid);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(3, nickname);
        }
 
        String truename = entity.getTruename();
        if (truename != null) {
            stmt.bindString(4, truename);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(6, birthday);
        }
 
        String qq = entity.getQq();
        if (qq != null) {
            stmt.bindString(7, qq);
        }
        stmt.bindString(8, entity.getPhone());
 
        String weixin = entity.getWeixin();
        if (weixin != null) {
            stmt.bindString(9, weixin);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(11, avatar);
        }
 
        String score = entity.getScore();
        if (score != null) {
            stmt.bindString(12, score);
        }
 
        String login = entity.getLogin();
        if (login != null) {
            stmt.bindString(13, login);
        }
 
        String reg_ip = entity.getReg_ip();
        if (reg_ip != null) {
            stmt.bindString(14, reg_ip);
        }
 
        String reg_time = entity.getReg_time();
        if (reg_time != null) {
            stmt.bindString(15, reg_time);
        }
 
        String last_login_ip = entity.getLast_login_ip();
        if (last_login_ip != null) {
            stmt.bindString(16, last_login_ip);
        }
 
        String last_login_time = entity.getLast_login_time();
        if (last_login_time != null) {
            stmt.bindString(17, last_login_time);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(18, status);
        }
 
        String lang = entity.getLang();
        if (lang != null) {
            stmt.bindString(19, lang);
        }
 
        String attribution = entity.getAttribution();
        if (attribution != null) {
            stmt.bindString(20, attribution);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(21, note);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoginInfoBean entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(2, uid);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(3, nickname);
        }
 
        String truename = entity.getTruename();
        if (truename != null) {
            stmt.bindString(4, truename);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(6, birthday);
        }
 
        String qq = entity.getQq();
        if (qq != null) {
            stmt.bindString(7, qq);
        }
        stmt.bindString(8, entity.getPhone());
 
        String weixin = entity.getWeixin();
        if (weixin != null) {
            stmt.bindString(9, weixin);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(11, avatar);
        }
 
        String score = entity.getScore();
        if (score != null) {
            stmt.bindString(12, score);
        }
 
        String login = entity.getLogin();
        if (login != null) {
            stmt.bindString(13, login);
        }
 
        String reg_ip = entity.getReg_ip();
        if (reg_ip != null) {
            stmt.bindString(14, reg_ip);
        }
 
        String reg_time = entity.getReg_time();
        if (reg_time != null) {
            stmt.bindString(15, reg_time);
        }
 
        String last_login_ip = entity.getLast_login_ip();
        if (last_login_ip != null) {
            stmt.bindString(16, last_login_ip);
        }
 
        String last_login_time = entity.getLast_login_time();
        if (last_login_time != null) {
            stmt.bindString(17, last_login_time);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(18, status);
        }
 
        String lang = entity.getLang();
        if (lang != null) {
            stmt.bindString(19, lang);
        }
 
        String attribution = entity.getAttribution();
        if (attribution != null) {
            stmt.bindString(20, attribution);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(21, note);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LoginInfoBean readEntity(Cursor cursor, int offset) {
        LoginInfoBean entity = new LoginInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // uid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // truename
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sex
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // birthday
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // qq
            cursor.getString(offset + 7), // phone
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // weixin
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // email
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // avatar
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // score
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // login
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // reg_ip
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // reg_time
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // last_login_ip
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // last_login_time
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // status
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // lang
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // attribution
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // note
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoginInfoBean entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNickname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTruename(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSex(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBirthday(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setQq(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPhone(cursor.getString(offset + 7));
        entity.setWeixin(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEmail(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAvatar(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setScore(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setLogin(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setReg_ip(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setReg_time(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setLast_login_ip(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setLast_login_time(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setStatus(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setLang(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setAttribution(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setNote(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LoginInfoBean entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LoginInfoBean entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LoginInfoBean entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}