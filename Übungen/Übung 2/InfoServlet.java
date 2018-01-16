import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "Info",
        urlPatterns = {"/info"}
)
public class InfoServlet extends HttpServlet {

    private int hitCount;

    public void init() {

        hitCount = 0;
    }

    public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {

        hitCount++;

        _response.setContentType("text/html");
        PrintWriter out = _response.getWriter();

        out.println("<HTML>");
        out.println("<HEAD><TITLE>Info</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("<H1>Info</H1>");
        out.println("<H2>Hitcount: " + hitCount + "</H2><br/>");

        out.println("<H2>Client</H2>");
        out.println("IP-Address: " + _request.getRemoteAddr() + "<br/>");
        out.println("Browsertype: " + _request.getHeader("user-agent") + "<br/>");
        out.println("MIME Types: " + _request.getHeader("accept") + "<br/>");
        out.println("Protocol: " + _request.getProtocol() + "<br/>");

        out.println("<H2>Server</H2>");
        out.println("Port: " + _request.getServerPort() + "<br/>");
        out.println("Name: " + _request.getServerName() + "<br/>");

        out.println("</BODY>");
        out.println("</HTML>");

        out.close();
    }
}
