package at.fhooe.mc.task03;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

@Path("/HelloWorld")
public class HelloWorld extends Application {
    /**
     * @return returns a String containing HTML data
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getDataHtml() {
        return "<head></head><body><h1>Hello World!</h1></body>";
    }

    /**
     * @return returns a String containing JSON data
     */
    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDataJson() {
        return "{\"val\":\"HelloWorld\"}";
    }

    /**
     * @return returns a String containing XML data
     */
    @GET
    @Path("/xml")
    @Produces(MediaType.TEXT_XML)
    public String getDataXml() {
        return "<HelloWorld>Hello World!</HelloWorld>";
    }
}
