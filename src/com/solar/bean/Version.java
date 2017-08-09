package com.solar.bean;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solar.utils.CopyFileUtil;
import com.solar.utils.FileMd5;
import com.solar.utils.FileSize;
import com.solar.utils.MD5;
import com.solar.utils.Zip; 
 
 

public class Version implements Comparable{
	private String main_Version;
	private String type_Version;
	private final String base_Version = "release";
	private Date date_Version ;
	public String getMain_Version() {
		return main_Version;
	}
	
	public Version(String str){
		int firstIndex = str.indexOf("_");
		int secondIndex = str.indexOf("_", firstIndex+1);
		int thirdIndex = str.indexOf("_", secondIndex+1);
		this.main_Version = str.substring(0,firstIndex);
		this.type_Version = str.substring(firstIndex+1,secondIndex);
		//this.base_Version = str.getBase_Version();
		SimpleDateFormat dateFormet = new SimpleDateFormat("yyyyMMdd");
		try {
			this.date_Version = dateFormet.parse(str.substring(thirdIndex+1,str.length()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setMain_Version(String main_Version) {
		this.main_Version = main_Version;
	}
	public Date getDate_Version() {
		return date_Version;
	}
	public void setDate_Version(Date date_Version) {
		this.date_Version = date_Version;
	}
	public String getBase_Version() {
		return base_Version;
	}
	public String getType_Version() {
		return type_Version;
	}

	public void setType_Version(String type_Version) {
		this.type_Version = type_Version;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Version version = (Version)arg0;
		
		int date_result = version.getDate_Version().compareTo(this.getDate_Version());
		if(date_result == 0){
			int length = Math.min(this.getMain_Version().length(), version.getMain_Version().length());
			String mian_Version_1 = this.getMain_Version().substring(0, length);
			String main_Version_2 = version.getMain_Version().substring(0, length);
			int base_result = main_Version_2.compareTo(mian_Version_1);
			 
			return base_result == 0? version.getMain_Version().length() > length ? 1:-1:base_result;
		}
		else
			return date_result;
	}
	
	public String toString(){
		SimpleDateFormat dateFormet = new SimpleDateFormat("yyyyMMdd");
		return  this.getMain_Version() + "_" + this.type_Version + "_" + this.getBase_Version() + "_" + dateFormet.format(this.getDate_Version());
	}
	
	
	
}
