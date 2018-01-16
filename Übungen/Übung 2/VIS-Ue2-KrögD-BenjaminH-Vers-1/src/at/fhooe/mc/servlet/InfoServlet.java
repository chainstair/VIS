package at.fhooe.mc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

@WebServlet("/infoservlet")
public class InfoServlet extends HttpServlet {
	public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        _response.setContentType("text/html");
		PrintWriter out = _response.getWriter();

		out.println("<HTML>");
		out.println("<HEAD><TITLE>Informationen - Client / Server</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<H1>Informationen - Client / Server</H1>");
		out.println("<H2>Info: <br></H2>");
        out.println("<p>IP: " + _request.getRemoteAddr() + "</p>");
        out.println("<p>Browser information: " + _request.getHeader("user-agent") + "</p>");
		out.println("<p>MIME-Type: " + _request.getHeader("accept") + "</p>");
		out.println("<p>Protocol: " + _request.getProtocol() + "</p>");
        out.println("<p>Server-Name: " + _request.getServerPort() + "</p>");
        out.println("<p>Server-Port: " + _request.getServerName() + "</p>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
}
