package io.xstefank;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "this works")
    String someProp;

    @ConfigProperty(name = "another-prop")
    String anotherProp;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println(someProp);
        System.out.println(anotherProp);
        return "Hello from Quarkus REST";
    }
}
