package com.solar.servlet.land; 
import java.io.File;  
import java.io.IOException;
import java.util.Iterator;  
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.solar.dao.impl.LandDaoImpl;

/**
 * Servlet implementation class Test
 */
@WebServlet("/UploadVersion")
public class UploadVersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UploadVersionServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadVersionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println(request.getParameter("someKey"));
	//	System.out.println(URLDecoder.decode(request.getParameter("someKey"),"UTF-8"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	doGet(request, response);
		logger.debug("岸端开始上传版本包");
		String key = request.getParameter("key");
		//System.out.println(request.getParameter("someKey"));
		
		 String savePath = this.getServletConfig().getServletContext()
	                .getRealPath("");
	        //保存文件的路径
	        savePath = savePath + "/upload/";
	        File f1 = new File(savePath);
	        System.out.println(savePath);
	        //如果文件不存在,就新建一个
	        if (!f1.exists()) {
	            f1.mkdirs();
	        }
	        //这个是文件上传需要的类,具体去百度看看,现在只管使用就好
	        DiskFileItemFactory fac = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(fac);
	        upload.setHeaderEncoding("utf-8");
	        List fileList = null;
	        try {
	            fileList = upload.parseRequest(request);
	        } catch (FileUploadException ex) {
	            return;
	        }
	        //迭代器,搜索前端发送过来的文件
	        Iterator<FileItem> it = fileList.iterator();
	        String name = "";
	        String extName = "";
	        while (it.hasNext()) {
	            FileItem item = it.next();
	            //判断该表单项是否是普通类型
	            if (!item.isFormField()) {
	                name = item.getName();
	                long size = item.getSize();
	                String type = item.getContentType();
	                System.out.println(size + " " + type);
	                if (name == null || name.trim().equals("")) {
	                    continue;
	                }
	                // 扩展名格式： extName就是文件的后缀,例如 .txt
	                if (name.lastIndexOf(".") >= 0) {
	                    extName = name.substring(name.lastIndexOf("."));
	                }
	                File file = null;
	                do {
	                    // 生成文件名：
	                    name = UUID.randomUUID().toString();
	                    file = new File(savePath + name + extName);
	                } while (file.exists());
	                File saveFile = new File(savePath + name + extName);
	                try {
	                    item.write(saveFile);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }  
	        LandDaoImpl dao = new LandDaoImpl();
	        dao.unzip(key, savePath + name + extName);
	}

}
