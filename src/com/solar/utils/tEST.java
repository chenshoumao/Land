package com.solar.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class tEST {
	public static void main(String[] args) throws IOException {
		String[] paths = {"D:\\海图项目\\tu\\sql.txt","D:\\海图项目\\tu\\sql2.txt","D:\\海图项目\\tu\\sql3.txt"};
		String dest = "D:\\海图项目\\tu\\sql4.txt";
		WriteFileUtil fileUtil = new WriteFileUtil();
		for(String path:paths){
			List<String> contentList = FileUtils.readLines(new File(path));
			for(String content:contentList){
				fileUtil.writeInfoToFile(content, dest,true);
			}
		}
		
	}
}
