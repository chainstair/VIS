package at.fhooe.mc.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/helloworld")
public class HelloWorldServlet extends HttpServlet {
	
	private int mCounter = 0;
	
	public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		++mCounter;
        _response.setContentType("text/html");
		PrintWriter out = _response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>HelloWorld</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<H1>Hello World!</H1>");
		out.println("<H2>Aufrufe: " + mCounter + "</H2>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
}