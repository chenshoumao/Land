package com.solar.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

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
		ResourceBundle resource = ResourceBundle.getBundle("config/ship");
		String informUnzipFilePath = resource.getString("informUnzipFilePath");
		informUnzipFilePath = new String(informUnzipFilePath.getBytes("ISO-8859-1"), "utf-8");

		String str = "i love china!";
		File txt = new File(informUnzipFilePath);
		if (!txt.exists()) {
			txt.createNewFile();
		}
		byte bytes[] = new byte[512];
		bytes = str.getBytes(); // 新加的
		int b = str.length(); // 改
		FileOutputStream fos = new FileOutputStream(txt);
		fos.write(bytes, 0, b);
		fos.close();

	}

}
