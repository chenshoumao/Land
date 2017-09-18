package com.solar.servlet.land;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solar.bean.Version;
import com.solar.dao.LandDao;
import com.solar.dao.impl.LandDaoImpl;
import com.solar.utils.MyException;
import com.solar.utils.VersionListUtil;

/**
 * Servlet implementation class LandListener
 */
@WebServlet("/LandListener")
public class LandListener extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LandDaoImpl dao = new LandDaoImpl();
      
	private static Logger logger = Logger.getLogger(LandListener.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LandListener() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			logger.debug("岸端 第一步 ：");
			logger.debug("	岸端开始 解析船端传来的数据");
			//获取船的版本
			String versionInfo = request.getParameter("ship");
			String host = request.getParameter("ip");
			if(versionInfo.equals("") || versionInfo.equals(null)){
				throw new MyException("岸端 第一步 ：船端传来的数据为空！");
			}
			if(host.equals("") || host.equals(null)){
				throw new MyException("岸端 第一步 ：船端传来的host为空！");
			}
			logger.debug("	船端的host 为：" + host);
			logger.debug("	船端传来的数据为：" + versionInfo);
			List<Map<String, Object>> list = dao.analysisVersion(host,versionInfo);
		
			response.setCharacterEncoding("utf-8");			
			response.setHeader("content-type", "text/html;chaset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			String json =  mapper.writeValueAsString(list);
			PrintWriter out = response.getWriter();
			out.print(json);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} 
	} 
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}