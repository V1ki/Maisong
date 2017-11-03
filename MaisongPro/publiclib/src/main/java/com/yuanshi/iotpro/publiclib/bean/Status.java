package com.yuanshi.iotpro.publiclib.bean;

/**
 * Created by carl on 2017/4/26.
 */

public class Status<T> {
    //{"status":{"status_code":"999","status_reason":"????????,?????!"}}
    private int status;
    private String msg;
    private  T data;
    public  T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }




    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ",data=" +data+"\'"+
                '}';
    }
}
