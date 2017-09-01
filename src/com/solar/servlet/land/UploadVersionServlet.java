package com.solar.servlet.land; 
import java.io.File;  
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;  
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solar.dao.impl.LandDaoImpl;
import com.solar.utils.OracleUtil;
import com.solar.utils.ResourceBundleUtil;

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
		//上传的版本描述
				String description = request.getParameter("describe");
				description=URLDecoder.decode(description,"Utf-8");
				String uid = request.getParameter("uid");
				String username = request.getParameter("username");
			//	doGet(request, response);
			 	logger.debug("岸端开始上传版本包");
				String key = request.getParameter("key");
				//文件大小 单位kb
				 long kb = 0;
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
			                kb = size / 1014;
			               
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
			        
			        ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
			        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			        //确定一个新的版本名字
			        String new_repository_name = "";
			        String path =  bundleUtil.getInfo("config/land", key); 
			 //       path =  new String(path.getBytes("ISO-8859-1"),"utf-8");
			        File file = new File(path);
			        int size = 0;
			        if(file.exists()){
			        	size =  file.listFiles().length;
			        //	size++;
			        	if(size < 1000){
			        		size += 1000;
			        		new_repository_name = String.valueOf(size);
			        		char[] charList = new_repository_name.toCharArray();
			        		new_repository_name = "";
			        		for(char c:charList){
			        			new_repository_name += c + ".";
			        		}
			        		new_repository_name = new_repository_name.substring(0, new_repository_name.length()-1);
			        	}
			        	new_repository_name += "_" + key + "_release_" + simpleDateFormat.format(new Date());
			        	System.out.println(new_repository_name);
			        }
			        OracleUtil oracleUtil = new OracleUtil();
			        Connection conn = oracleUtil.getConn();
			        String sql = "insert into version(versionname,sizes,uploadtime,userId,username,href,remark) values(?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS '),?,?,?,?)";
			        try {
						PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
						simpleDateFormat = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
						ps.setString(1, new_repository_name);
						ps.setString(2, kb + "kb");
						ps.setString(3, simpleDateFormat.format(new Date()));
						ps.setString(4, uid);
						ps.setString(5, username);
						ps.setString(6, path + "/" + new_repository_name);
						ps.setString(7, description);
						ps.execute();
						ps.close();
						oracleUtil.closeConn(conn); 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			        LandDaoImpl dao = new LandDaoImpl();
			        
			        file = new File(path + "/" + new_repository_name);
			        if(!file.exists())
			        	file.mkdir();
			        Map<String, Object> map = dao.unzip(key, savePath + name + extName,new_repository_name);
			        
			        ObjectMapper mapper = new ObjectMapper();
			        String json = mapper.writeValueAsString(map);
			        response.setCharacterEncoding("utf-8");
			        PrintWriter out = response.getWriter();
			        out.write(json);
			        
			        
	}

}
