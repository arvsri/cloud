package com.arvindsrivastava.demo.webapp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class SimpleWebApp {
	
	private static final Config CONFIG = ConfigFactory.load();
	
	public static void main(String[] args) throws Exception {
		
	
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir("temp");
		tomcat.setPort(8080);

		String contextPath = "/";
		String docBase = new File(".").getAbsolutePath();

		Context context = tomcat.addContext(contextPath, docBase);

		HttpServlet servlet = new HttpServlet() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				PrintWriter writer = resp.getWriter();

				writer.println("<html><title>Welcome</title>");
				writer.println("<body>");
				writer.println("<h1>Have a Great Day!</h1><br/>"
						+ "<p>" + "DB URL is :  " + CONFIG.getString("webapp.database.url") + "</p>"
						+ "<p>" + "DB user is :  " + CONFIG.getString("webapp.database.user") + "</p>"
						+ "<p>" + "DB password is : " + CONFIG.getString("webapp.database.password") + "</p>");
				writer.println("</body>");						
				writer.println("</html>");						
			}
		};

		String servletName = "Servlet1";
		String urlPattern = "/home";

		tomcat.addServlet(contextPath, servletName, servlet);
		context.addServletMappingDecoded(urlPattern, servletName);

		tomcat.start();
		tomcat.getServer().await();
	}
}
