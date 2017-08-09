package com.solar.utils;

import java.io.File;

public class FileSize {
	public long getFileSize(File file){
		long size = 0; 
		
		if(file.isFile()){
			size += file.length();
		}
		if (file.isDirectory()) {
			File[] fileList = file.listFiles();
			for (File fileIt : fileList)
				size += getFileSize(fileIt);
		}
		return size;
	}
}
