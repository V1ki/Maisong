package com.yuanshi.maisong.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;

import com.yuanshi.iotpro.publiclib.utils.YLog;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by Dengbocheng on 2017/10/25.
 */

public class Utils {

    public static String getAppLocalPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();

    }

    public static String getHeadIconPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong/HeadIcon/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }


    /**
     * 获取位置信息
     * @param context
     * @return
     */
    public static Location getLocation(Context context){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if(location != null){
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
            return location;
//            }
        }else{
            LocationListener locationListener = new LocationListener() {
                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        YLog.e( "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if(location != null){
//                latitude = location.getLatitude(); //经度
//                longitude = location.getLongitude(); //纬度
            return location;
//            }
        }
    }

    /**
     * 根据秒数值获取指定格式的日期格式
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(Long millisecond){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateStr = sdf.format(millisecond*1000);
        YLog.e(dateStr);
        return dateStr;
    }

    /**
     * 华氏度转摄氏度
     * @return
     */
    public static double fahrenheitDegreeToCentigrade(double fahrenheitDegree){
        return (fahrenheitDegree - 32) / 1.8;
    }
}
