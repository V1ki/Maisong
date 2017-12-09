package com.yuanshi.iotpro.publiclib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/8.
 */

/**
 * 好友验证消息类
 */
@Entity
public class FriendsApplyBean {
    @Id
    private Long _id;
    private String reason;
    private int state;//处理状态，0 已拒绝， 1 已接受， 2 未处理
    private String nickname;
    private String phone;
    private String avatar;

    @Generated(hash = 1873154176)
    public FriendsApplyBean(Long _id, String reason, int state, String nickname,
            String phone, String avatar) {
        this._id = _id;
        this.reason = reason;
        this.state = state;
        this.nickname = nickname;
        this.phone = phone;
        this.avatar = avatar;
    }

    @Generated(hash = 2141675981)
    public FriendsApplyBean() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }


    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
