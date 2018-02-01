package com.yuanshi.maisong.utils.txtread_utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DataManager {
	/**
	 * 没有使用成员变量的方法可以定义为静态方法
	 * 保存数据的业务逻辑
	 * OutputStream 输出流
	 * content 文件内容
	 * @throws Exception 
	 */
	public static void saveDate (OutputStream outputStream , String contentString) throws Exception
	{
		outputStream.write(contentString.getBytes());
		outputStream.close();
	}
	/**
	 * 读取数据的业务逻辑
	 * @param InputStream
	 * @param contentString
	 * @throws Exception
	 */
	public static  String readDate (InputStream inputStream ) throws Exception
	{
		byte [] byte1 = new byte[1024];
		/**
		 * 当输入流读到文件的末尾 返回就是－1 
		 */
		int length = inputStream.read(byte1);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		if(length!=-1)
		{
			//读到的内容存在内存中ByteArrayOutputStream 这个类用于将byte流存储在内存中
			
			byteArrayOutputStream.write(byte1, 0, length);
		}
		String dateString =   byteArrayOutputStream.toString();
		byteArrayOutputStream.close();
		inputStream.close();
		return dateString;
	}
}
