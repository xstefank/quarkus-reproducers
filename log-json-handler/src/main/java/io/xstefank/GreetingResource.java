package io.xstefank;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.FileGreeter;
import org.acme.Greeter;
import org.acme.SocketGreeter;
import org.acme.SyslogGreeter;

@Path("/hello")
public class GreetingResource {

    @Inject
    Greeter greeter;

    @Inject
    FileGreeter fileGreeter;

    @Inject
    SyslogGreeter syslogGreeter;

    @Inject
    SocketGreeter socketGreeter;

    @GET
    @Path("/console")
    @Produces(MediaType.TEXT_PLAIN)
    public String console() {
        Log.info("GreetingResource.console()");
        greeter.sayHello();
        return "Hello from console";
    }

    @GET
    @Path("/file")
    @Produces(MediaType.TEXT_PLAIN)
    public String file() {
        Log.info("GreetingResource.file()");
        fileGreeter.sayHello();
        return "Hello from file";
    }

    @GET
    @Path("/syslog")
    @Produces(MediaType.TEXT_PLAIN)
    public String syslog() {
        Log.info("GreetingResource.syslog()");
        syslogGreeter.sayHello();
        return "Hello from syslog";
    }

    @GET
    @Path("/socket")
    @Produces(MediaType.TEXT_PLAIN)
    public String socket() {
        Log.info("GreetingResource.socket()");
        socketGreeter.sayHello();
        return "Hello from socket";
    }
}
