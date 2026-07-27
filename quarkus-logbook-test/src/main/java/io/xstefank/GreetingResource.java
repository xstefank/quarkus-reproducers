package io.xstefank;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.zalando.logbook.Logbook;

@Path("/hello")
public class GreetingResource {

    @Inject
    Logbook logbook;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println(logbook);
        return "Hello from Quarkus REST";
    }
}
