package com.solar.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solar.utils.ResourceBundleUtil;

/**
 * Servlet implementation class ClearServlet
 */
@WebServlet("/ClearServlet")
public class ClearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		//获取增量文件路径
		String addPath = bundleUtil.getInfo("config/land", "tempPath");
		File addFile = new File(addPath);
		deleteFile(addFile,false);
		
		
		//压缩文件所在地址
		String unzipPath = bundleUtil.getInfo("config/land", "zipPath");
		deleteFile(new File(unzipPath),false);
	}


	public void deleteFile(File file,boolean state){
		if(file.isFile()){
			file.delete();
		}
		else{ 
			File[] fileList = file.listFiles();
			for(File filel:fileList){
				deleteFile(filel,true);
			}
			if(state)
				file.delete();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
