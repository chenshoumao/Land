package com.solar.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import com.solar.bean.Version;
public class VersionListUtil {
	private static Logger logger = Logger.getLogger(VersionListUtil.class);
	//版本命名规范，如10.0.0_db_release_20170713，可是可分为四个部分
	private static int length = 4;
	
	public List<Version> getVersionFromPath(String path){
		List<Version> list = new ArrayList<Version>(); 
		File file = new File(path);
		if(file.exists()){
			File[] fileList = file.listFiles();
			for(File fileIt:fileList){
				String name = fileIt.getName();
				String[] validateName = name.split("_");
				//判断版本的命名是否规范，若是不规范，要及时通知技术人员
				if(validateName.length == length)
					list.add(new Version(name));
				else{ 
					System.out.println("命名非法！:  " + name);
					logger.debug(	"命名非法！:  " + name); 
					//通知相关人员，版本库命名不规范
				}
			}
		} 
		Collections.sort(list);
		return list;

		
		
	}
}
