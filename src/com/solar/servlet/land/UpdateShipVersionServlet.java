package com.solar.servlet.land;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.solar.dao.impl.LandDaoImpl;

/**
 * Servlet implementation class UpdateShipVersionServlet
 */
@WebServlet("/UpdateShipVersionServlet")
public class UpdateShipVersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private static Logger logger = Logger.getLogger(UpdateShipVersionServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateShipVersionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("更新船的版本信息：");
		String data ="app,haitu,ditu,db";
		String[] dataList = data.split(","); 
		String app_version = request.getParameter("app");
		String db_version = request.getParameter("db");
		String ditu_version = request.getParameter("ditu");
		String haitu_version = request.getParameter("haitu");
		String ip = request.getParameter("ip");
		logger.debug("  接受到来自 " + ip + " 的版本信息是 " + app_version + ","
				+ db_version + "," + haitu_version + "," + ditu_version) ;
		
		logger.debug("开始更新数据库中船版本号的数据");
		LandDaoImpl dao = new LandDaoImpl();
		
		dao.UpdateShipVersion(ip, app_version,db_version,haitu_version,ditu_version);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
