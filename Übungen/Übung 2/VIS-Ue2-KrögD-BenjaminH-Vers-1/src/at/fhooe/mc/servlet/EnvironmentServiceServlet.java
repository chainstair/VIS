package at.fhooe.mc.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import at.fhooe.mc.api.*;
import at.fhooe.mc.interfaces.*;

@WebServlet(
		name= "/environmentserviceservlet",
		urlPatterns = {"/environmentservice"}
)

public class EnvironmentServiceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_response.addIntHeader("Refresh", 5);
        _response.setContentType("text/html");
		PrintWriter out = _response.getWriter();
		
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Environment Data</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<INPUT TYPE=\"button\" onClick=\"history.go(0)\" VALUE=\"Refresh\">");
		out.println("<H1>Environment Data</H1>");
		
		out.println("<H2>C++ Server Environment Data<br></H2>");

		
		try {
			
			MyJavaClient client = new MyJavaClient("127.0.0.1", 5063);
			//Client client = new Client("127.0.0.1", 5063);
			
			EnvData[] data = client.requestAll();
			
			out.println("<table border=\"1\">");
			out.println("<tr>");
			out.println("<th>Timestamp</th>");
			out.println("<th>Sensor</th>");
			out.println("<th>Value</th>");		
			out.println("</tr>");
			
			for (EnvData e : data) {
				out.println("<tr>");
				out.println("<td>" + e.getTimestamp() + "</td>");
				out.println("<td>" + e.getType() + "</td>");
				out.println("<td>" + e.getValue() + "</td>");	
				out.println("</tr>");
			}	
			
			out.println("</table><br>");
		} catch (Exception _e) {
			out.println("<h3>Cant connect to C++ Server.");
			out.println(_e.getMessage());
		}
			
			
		
		try{
			String adr = "EnvService";
			
			Registry reg = LocateRegistry.getRegistry();
			IEnvService server = (IEnvService)reg.lookup(adr);
			EnvData[] data = server.requestAll();
			
			out.println("<H2>RMI Server Environment Data<br></H2>");
			out.println("<table border=\"1\">");
			out.println("<tr>");
			out.println("<th>Timestamp</th>");
			out.println("<th>Sensor</th>");
			out.println("<th>Value</th>");		
			out.println("</tr>");
			for (EnvData e : data){
				out.println("<tr>");
				out.println("<td>" + e.getTimestamp() + "</td>");
				out.println("<td>" + e.getType() + "</td>");
				out.println("<td>" + e.getValue() + "</td>");
				out.println("</tr>");
			}
			out.println("</table><br>");			
		}
		catch (Exception e) {
			out.println("<p>RMI offline</p>");
			out.println(e.getStackTrace());
		}

		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
}
