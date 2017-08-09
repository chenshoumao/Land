package com.solar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.log4j.Logger;


public class UnzipUtil {
	private static Logger logger = Logger.getLogger(UnzipUtil.class);
	/**
	 * @author 陈守貌
	 * @Time 2017-07-14
	 * @Funtion 解压的代码
	 * @param sourcePath
	 *            压缩包所在的路径
	 * @param outPath
	 *            解压的后文件的存放路径
	 */
	public static boolean unzip(String sourcePath, String outPath) {
		logger.debug("进入解压缩的工具类...");
		logger.debug("源文件 ： " + sourcePath);
		logger.debug("解压路径 ： " + outPath);
		boolean stateResult = false;

		long zipFileSize = 0;
		try {
			// 文件输入流
			FileInputStream fin = null;

			int runCount = 0;
			while (!stateResult && runCount < 20) {
				try {
					fin = new FileInputStream(sourcePath);
					File fileJudge = new File(sourcePath);
					String name = "";
					name = fileJudge.getName(); 
					stateResult = true;
				} catch (Exception e) { 
					System.out.println(e);
					stateResult = false;
					runCount++;
					logger.debug("文件暂时不可解压,4秒后再尝试解压，目前已经失败"+runCount+"次");
					Thread.sleep(4000);
				}
			}

			if (stateResult) {
				logger.debug("开始解压。。。");
				// 需要维护所读取数据校验和的输入流。校验和可用于验证输入数据的完整性
				CheckedInputStream checkIn = new CheckedInputStream(fin, new CRC32());
				// 指定编码 否则会出现中文文件解压错误
				Charset gbk = Charset.forName("GBK");
				// zip格式的输入流
				ZipInputStream zin = new ZipInputStream(checkIn, gbk);

				// 遍历压缩文件中的所有压缩条目
				ZipEntry zinEntry;

				while ((zinEntry = zin.getNextEntry()) != null) {
					System.out.println(zinEntry);
					File targetFile = new File(outPath + File.separator + zinEntry.getName());

					System.out.println("..." + targetFile + "   " + targetFile.getParentFile());

					// String sourceUpdatePath = targetFile.toString();
					if (!targetFile.getParentFile().exists()) {
						System.out.println("..." + targetFile + "   " + targetFile.getParentFile());
						targetFile.getParentFile().mkdirs();
					}
					if (zinEntry.isDirectory()) {
						targetFile.mkdirs();
					} else {
						FileOutputStream fout = new FileOutputStream(targetFile);
						byte[] buff = new byte[1024];
						int length;
						while ((length = zin.read(buff)) > 0) {
							fout.write(buff, 0, length);
						}
						fout.close();
					}
				}

				zin.close();
				fin.close();
				System.out.println(checkIn.getChecksum().getValue());
				checkIn.close();

				Thread.sleep(4000);
				File file = new File(sourcePath); 
			}

		} catch (Exception e) {
			// TODO: handle exception
			stateResult = false;
			System.out.println(e);
		}
		if (stateResult) {
			logger.debug("解压数据成功");
			System.out.println("解压数据成功");
			stateResult = true;
		} else {
			logger.debug("解压数据失败");
			System.out.println("解压数据失败");
			stateResult = false;
		}
		return stateResult; 
	}

	public Map<String, Object> updateDir() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//更新代码
			overideUpdate("");
			//更新海图
			overideUpdate("haitu");
			//更新底图
			overideUpdate("ditu");
			//更新配置
			//...
			//更新数据库
			//...
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	// 以覆盖的形式更新 此部分包括代码，图
	public Map<String, Object> overideUpdate(String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		try {

			ResourceBundle resouce = ResourceBundle.getBundle("config/ship");

			// 获取项目的路径
			String sourcePath = null;
			if(!key.equals(""))
				sourcePath = resouce.getString("web") + File.separator + key;
			else
				sourcePath = resouce.getString("web");
			//获取最新解压的文件夹
			ReadFile readFile = new ReadFile();
			String filePath = resouce.getString("informUnzipFilePath");
			String unzipFileName = readFile.readLastLine(new File(filePath), "utf-8");
			// 获取解压路径中要获取的内容的路径
			String unzipPath = resouce.getString("unzipPath") + File.separator + unzipFileName + File.separator + resouce.getString(key);

			// 直接覆盖
			CopyFileUtil copyUtil = new CopyFileUtil();
			result = copyUtil.copyDirectory(unzipPath, sourcePath, true);

		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		}
		map.put("result", result);
		return map;
	}

	

	public static void main(String[] args) {
		File file = new File("D:/海图项目/通知文件/解压文件/inform.txt");

		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			long len;

			len = raf.length();

			if (len == 0L) {

			} else {
				long pos = len - 1;
				while (pos > 0) {
					pos--;
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						break;
					}
				}
				if (pos == 0) {
					raf.seek(0);
				}
				byte[] bytes = new byte[(int) (len - pos)];
				raf.read(bytes);
				System.out.println(new String(bytes, "gbk"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				}
			}
		}

	}

}
