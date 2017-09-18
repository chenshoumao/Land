package com.solar.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

public class WriteFileUtil {

	public void writeInfoToFile(String info, String url,boolean overwrite) { 
		try {
			File txt = new File(url);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			byte bytes[] = new byte[1024];
			bytes = info.getBytes(); // 新加的
			int b = info.length(); // 改
			FileOutputStream fos = new FileOutputStream(txt,overwrite);
			fos.write(bytes);
			if(overwrite)
				fos.write("\r\n".getBytes());  
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		

		String str = "hostip = 192 ";
		File txt = new File("D:\\text.properties");
		if (!txt.exists()) {
			txt.createNewFile();
		}
		byte bytes[] = new byte[512];
		
		 
		List<String> contentList = FileUtils.readLines(txt);
		FileOutputStream fos = new FileOutputStream(txt);
		for(int i = 0;i < contentList.size() -1 ;i++){
			String content = contentList.get(i);
			bytes = content.getBytes(); // 新加的
			int b = content.length(); // 改 
			fos.write(bytes, 0, b);
			fos.write("\r\n".getBytes());  
		} 
		 
		bytes = str.getBytes(); // 新加的
		int b = str.length(); // 改 
		fos.write(bytes, 0, b);
		fos.write("\r\n".getBytes());  
		
		fos.close();

	}

}
