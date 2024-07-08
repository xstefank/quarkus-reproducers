package org.acme;

import io.smallrye.health.SmallRyeHealthReporter;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    SmallRyeHealthReporter smallRyeHealthReporter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println(smallRyeHealthReporter.getHealth());
        return "Hello from Quarkus REST";
    }
}
