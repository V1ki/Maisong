package com.yuanshi.iotpro.publiclib.bean;

/**
 * Created by carl on 2017/4/26.
 */

public class Status<T> {
    //{"status":{"status_code":"999","status_reason":"????????,?????!"}}
    private int status;
    private String info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", msg='" + info + '\'' +
                ",data=" +data+"\'"+
                '}';
    }
}
