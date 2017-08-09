package com.solar.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.PropertyConfigurator;


public class Log4jServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		String path = config.getServletContext().getRealPath("WEB-INF\\classes");
		 path  = path + File.separator + config.getInitParameter("log4j");  
		 System.out.println(path);
		 PropertyConfigurator.configure(path);  
	}

}