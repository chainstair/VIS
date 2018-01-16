import Helper.EnvData;
import Helper.IEnvService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@WebServlet(
        name = "Environment Service",
        urlPatterns = {"/envservice"}
)
public class EnvironmentServiceServlet extends HttpServlet {
    public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {

        _response.addIntHeader("Refresh", 5);
        _response.setContentType("text/html");
        PrintWriter out = _response.getWriter();

        out.println("<HTML>");
        out.println("<HEAD><TITLE>Environment Service</TITLE></HEAD>");
        out.println("<Body>");
        out.println("<INPUT TYPE=\"button\" onClick=\"history.go(0)\" VALUE=\"Refresh\">");



        try {
            Client client = new Client("10.29.18.246", 4711);

            EnvData[] envDatas = client.requestAll();

            out.println("<h3>C++ Server Environment Data</h3>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>Timestamp</td>");
            out.println("<td>Sensor</td>");
            out.println("<td>Value</td>");
            out.println("</tr>");

            for (EnvData envData : envDatas) {
                out.println("<tr>");
                out.println("<td>" + envData.getTimestamp() + "</td>");
                out.println("<td>" + envData.getType() + "</td>");
                out.println("<td>" + envData.getData() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

        } catch (Exception _e) {
            out.println("<h3>C++ Server offline</h3>");
            out.println(_e.getMessage());

        }

        try {
            String addr = "EnvService";
            Registry reg = LocateRegistry.getRegistry("10.29.18.246");
            IEnvService envService = (IEnvService) reg.lookup(addr);

            EnvData[] envDatas = envService.requestAll();

            out.println("<h3>RMI Server Environment Data</h3>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>Timestamp</td>");
            out.println("<td>Sensor</td>");
            out.println("<td>Value</td>");
            out.println("</tr>");

            for (EnvData envData : envDatas) {
                out.println("<tr>");
                out.println("<td>" + envData.getTimestamp() + "</td>");
                out.println("<td>" + envData.getType() + "</td>");
                out.println("<td>" + envData.getData() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

        } catch (Exception _e) {
            out.println("<h3>RMI Server offline</h3>");
            out.println(_e.getMessage());

        }

        out.println("</Body>");
        out.println("</HTML>");

        out.close();
    }
}
