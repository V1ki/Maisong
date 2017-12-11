package com.yuanshi.maisong.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;
import com.yuanshi.iotpro.publiclib.utils.YLog;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

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

    /**
     * File 目录下文件是否存在
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName){
        File file = new File(getFileDownloadRealPath()+"/"+fileName);
        return file.exists();
    }


    /**
     * 获取下载文件暂存地址
     * @return
     */
    public static String getFileDownloadTempPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong/TempFiles/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 崩溃日志保存路径
     * @return
     */
    public static String getAppLogPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong/Logs/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取下载文件真实地址
     * @return
     */
    public static String getFileDownloadRealPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong/Files/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取头像保存地址
     * @return
     */
    public static String getHeadIconPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong/HeadIcon/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取图片地址
     * @return
     */
    public static String getPicPath(){
        File file = new File(Environment.getExternalStorageDirectory()+"/MySong/Pics/");
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }


    /**
     * 将暂存文件保存到真实目录下
     * @param fileName
     */
    public static void moveFile(String fileName){
        File oldFile = new File(getFileDownloadTempPath()+"/"+fileName);
        if(oldFile.exists()){
            oldFile.renameTo(new File(getFileDownloadRealPath()+"/"+fileName));
        }
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

    public static String getCurrentDate(Long millisecond){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

    public static <T> List<T> jsonToList(String json, Class<T[]> clazz)
    {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    public static <T> List<T> jsonToList2(String json, Class<T> clazz)
    {
        Type type = new TypeToken<List<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
    /**
     * 创建群组
     * @param groupName 群组名
     * @param desc 描述
     * @param allMembers  初始成员
     * @param reason
     * @return
     */
    public static EMGroup createChatRoom(final LoginInfoBean loginInfoBean,final String groupName, final String desc, final String[] allMembers, final String reason){
        YLog.e("insertDeviceInfo");
        try {
            return MyApplication.THREAD_EXCUTER.submit(new Callable<EMGroup>() {
                @Override
                public EMGroup call() throws Exception {
                    EMGroupOptions option = new EMGroupOptions();
                    option.maxUsers = 200;
                    option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                    EMGroup group =    EMClient.getInstance().groupManager().createGroup(groupName, desc, allMembers, reason, option);
                    EMMessage message = EMMessage.createTxtSendMessage("我创建了"+groupName+"群组", group.getGroupId());//发送一条消息
                    message.setAttribute("chatUserId",loginInfoBean.getPhone());
                    message.setAttribute("chatUserHead",loginInfoBean.getAvatar());
                    message.setAttribute("chatUserName",loginInfoBean.getNickname());
                    message.setChatType(EMMessage.ChatType.GroupChat);
                    EMClient.getInstance().chatManager().sendMessage(message);
                    return group;
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * f发送打招呼消息
     * @return
     */
    public static void sendHelloMessage(final LoginInfoBean loginInfoBean,final String phone, final String msg){
        YLog.e("insertDeviceInfo");
        try {
             MyApplication.THREAD_EXCUTER.execute(new Runnable() {
                @Override
                public void run() {
                    EMMessage message = EMMessage.createTxtSendMessage(msg,phone);//发送一条消息
                    message.setAttribute("chatUserId",loginInfoBean.getPhone());
                    message.setAttribute("chatUserHead",loginInfoBean.getAvatar());
                    message.setAttribute("chatUserName",loginInfoBean.getNickname());
                    message.setChatType(EMMessage.ChatType.GroupChat);
                    EMClient.getInstance().chatManager().sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (char curchar : input) {
                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(curchar);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }


    /**
     * 图片压缩
     * @param filePath
     * @return
     */
    public static Bitmap compressPixel(String filePath){
        Bitmap bmp = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = 2;

        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        try {
            //load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }finally {
            return bmp;
        }
    }

    /**
     * 保存图片到sd卡
     * @param mBitmap
     */
    public static  String savePics(Bitmap mBitmap, String picName) {
        File file = new File(getPicPath()+"/picName");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            YLog.e("图片写入完成  headpath-->"+file.getPath());
            return file.getPath();
//            headIconPath = file.getPath();
//            resetHeadIcon(headIconPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }
    public static HashMap<String, Object>getEditInfoMap(UserInfoBean userInfoBean){
        HashMap<String, Object> map = new HashMap();
        if(!TextUtils.isEmpty(userInfoBean.getName())){
            map.put("name",userInfoBean.getName());
        }
        map.put("sex",userInfoBean.getSex());
        if(!TextUtils.isEmpty(userInfoBean.getBirthday())){
            map.put("birthday",userInfoBean.getBirthday());
        }
        if(!TextUtils.isEmpty(userInfoBean.getQq())){
            map.put("qq",userInfoBean.getQq());
        }
        if(!TextUtils.isEmpty(userInfoBean.getPhone())){
            map.put("phone",userInfoBean.getPhone());
        }
        if(!TextUtils.isEmpty(userInfoBean.getWeixin())){
            map.put("weixin",userInfoBean.getWeixin());
        }
        if(!TextUtils.isEmpty(userInfoBean.getEmail())){
            map.put("email",userInfoBean.getEmail());
        }
        if(!TextUtils.isEmpty(userInfoBean.getAvatar())){
            map.put("avatar",userInfoBean.getAvatar());
        }
        return map;
    }

    public static void deleteFile(File file){
        if(!file.exists()){
            return;
        }
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                deleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 重启程序
     * @param conext
     */
    public static void  reloadApp(Context conext,Class clazz){
        final Intent intent = conext.getPackageManager().getLaunchIntentForPackage(conext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        conext.startActivity(intent);
    }
}
