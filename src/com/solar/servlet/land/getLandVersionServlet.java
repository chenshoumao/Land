package com.solar.servlet.land;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solar.bean.Version;
import com.solar.utils.ResourceBundleUtil;
import com.solar.utils.VersionListUtil;

/**
 * Servlet implementation class getLandVersionServlet12
 */
@WebServlet("/getLandVersionServlet")
public class getLandVersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getLandVersionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		
		List resultList = new ArrayList();
		ResourceBundleUtil bundleUtil = new ResourceBundleUtil();
		ObjectMapper mapper = new ObjectMapper();
		
		String[] versionModule = {"app","haitu","ditu","db"};
		VersionListUtil versionUtil = new VersionListUtil();
		for(String version:versionModule){ 
			String path = bundleUtil.getInfo("config/land", version);
			List<Version> list = versionUtil.getVersionFromPath(path); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(bundleUtil.getInfo("config/module", version), list);
			resultList.add(map);
		} 
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String json = mapper.writeValueAsString(resultList);
		out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
