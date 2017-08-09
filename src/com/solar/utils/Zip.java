package com.solar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Zip
 */
@WebServlet("/Zip")
public class Zip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	String filePath = ("D:\\海图项目\\zip");

	// 要压缩的 文件夹 路径
	String sourcePath = "D:/海图项目/zip2";

	// 压缩文件夹路径
	String outPutZipPath = "D:/海图项目/zip3/zip.zip"; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Zip() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
		Zip zip = new Zip();
		//zip.zip("D:/海图项目/临时文件/增量文件", "D:/海图项目/临时文件/压缩文件/TEST.zip");
	}
    
    
    public static boolean zip(String host,String fileName,String sourcePath, String outPutZipPath) {
    	boolean stateResult = false; 
    	
    	outPutZipPath+="/" + fileName;
		try {
			
			// 文件输出流
			FileOutputStream fout = new FileOutputStream(outPutZipPath);

			// 需要维护写入数据校验和的输出流。校验和可用于验证输出数据的完整性。
			CheckedOutputStream checkOut = new CheckedOutputStream(fout, new CRC32());
			// zip格式输出流
			ZipOutputStream zipOput = new ZipOutputStream(checkOut);

			// 将一个源文件写入到一个压缩文件中
			File sourceFile = new File(sourcePath);

			// 压缩条目
			String zipEntryName = "";
			stateResult = zipDire(zipOput, sourceFile, zipEntryName);
			zipOput.closeEntry();
			zipOput.close();
			fout.close();
			System.out.println(checkOut.getChecksum().getValue());
			checkOut.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return stateResult;
	}

	// 压缩目录
	public static boolean zipDire(ZipOutputStream zipOput, File sourceFile, String zipEntryName)
			throws IOException, FileNotFoundException {
		boolean stateResult = false;
		try {
			for (File file : sourceFile.listFiles()) {
				// String name = sourcePath + "/" + "下载必看/" + file.getName() ;
				if(!zipEntryName.equals(""))
					zipEntryName = zipEntryName + "/";
				if (file.isFile())
					ZipFile(file.toString(), zipOput, zipEntryName + file.getName());
				else {
					if (file.listFiles().length > 0)
						zipDire(zipOput, file, zipEntryName + "/" + file.getName());
					else
						zipOput.putNextEntry(new ZipEntry(zipEntryName + "/" + file.getName() + "/"));
				}
			}
			stateResult = true;
		} catch (Exception e) {
			// TODO: handle exception
			stateResult = false;
		}
		return stateResult;
		

	}

	// 压缩文件
	public static void ZipFile(String sourcePath, ZipOutputStream zipOput, String zipEntryName)
			throws IOException, FileNotFoundException {
		// 讲一个要压缩的文件写入到压缩条目中
		zipOput.putNextEntry(new ZipEntry(zipEntryName));

		// 读入要压缩的文件
		FileInputStream fileInput = new FileInputStream(sourcePath);

		byte[] buff = new byte[1024];
		int length;

		while ((length = fileInput.read(buff)) > 0) {
			zipOput.write(buff, 0, length);
		}

		fileInput.close();
	}
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.println("hello world");
		//zip(sourcePath, outPutZipPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
