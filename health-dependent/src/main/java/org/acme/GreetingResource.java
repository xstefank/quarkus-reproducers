package org.acme;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
@RequestScoped
public class GreetingResource {

    @Inject
    Bean bean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("Bean: " + bean.id);
        System.gc();
        return "Hello from Quarkus REST";
    }
}
