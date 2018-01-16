package at.fhooe.mc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/environmentserviceservlet")
public class EnvironmentServiceServlet extends HttpServlet {
	public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        _response.setContentType("text/html");
		PrintWriter out = _response.getWriter();
		
		
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Environment Data</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<H1>Environment Data</H1>");
		out.println("<H2>C++ Server Environment Data<br></H2>");
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.println("<p>IP: " + _request.getRemoteHost() + "</p>");
		out.println("<p>Browser Information: " + _request.getHeader(_request.getHeaderNames().nextElement()) + "</p>");
		out.println("<p>MIME-Type: " + _request.getAuthType() + "</p>");
		out.println("<p>Remote Port: " + _request.getRemotePort() + "</p>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
}
