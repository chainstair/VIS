package at.fhooe.mc.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name= "/environmentserviceservlet",
		urlPatterns = {"/environmentservice"}
)

public class EnvironmentServiceServlet extends HttpServlet {
	public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        _response.setContentType("text/html");
		PrintWriter out = _response.getWriter();
		
		try {
			
		} catch (Exception _e) {
			// TODO: handle exception
		}
		
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Environment Data</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<H1>Environment Data</H1>");
		
		out.println("<H2>C++ Server Environment Data<br></H2>");
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.println("<th>Timestamp</th>");
		out.println("<th>Sensor</th>");
		out.println("<th>Value</th>");		
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td></td>");
		out.println("<td></td>");
		out.println("<td></td>");	
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td></td>");
		out.println("<td></td>");
		out.println("<td></td>");	
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td></td>");
		out.println("<td></td>");
		out.println("<td></td>");	
		out.println("</tr>");	
		out.println("</table><br>");	
		
		out.println("<H2>RMI Server Environment Data<br></H2>");
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.println("<th>Timestamp</th>");
		out.println("<th>Sensor</th>");
		out.println("<th>Value</th>");		
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td></td>");
		out.println("<td></td>");
		out.println("<td></td>");		
		out.println("</tr>");
		out.println("</table><br>");	

		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
}
