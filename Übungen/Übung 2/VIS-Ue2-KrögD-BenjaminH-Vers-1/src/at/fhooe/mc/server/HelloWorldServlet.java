package at.fhooe.mc.server;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/helloWorld!")
public class HelloWorldServlet extends HttpServlet {
	public void doGet (HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException{
				_response.setContentType("text/html");
				PrintWriter out = _response.getWriter();
				out.println("<HTML>");
				out.println("<HEAD><TITLE>HelloWorld</TITLE></HEAD>");
				out.println("<BODY>");
				out.println("<H1>Hello World!</H1>");
				out.println("<H2>Aufrufe: </H2>");
				out.println("</BODY>");
				out.println("</HTML>");
		}
}