package com.solar.utils;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class ResourceBundleUtil {
	/*
	 * @author 陈守貌
	 * @param perpertyName 配置文件名
	 * @param prefix 前缀名
	 * 获取配置文件中的相关属性的信息
	 */
	public static String getInfo(String propertyName,String prefix){ 
		try { 
			ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyName);
			String info = resourceBundle.getString(prefix);
			info =  new String(info.getBytes("ISO-8859-1"),"utf-8");
			return info;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
