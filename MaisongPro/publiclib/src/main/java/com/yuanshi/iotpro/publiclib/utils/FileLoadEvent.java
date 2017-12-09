package com.yuanshi.iotpro.publiclib.utils;

/**
 * Created by Administrator on 2017/12/9.
 */

public class FileLoadEvent {

    long total;
    long bytesLoaded;

    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadEvent(long total, long bytesLoaded) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }
}
