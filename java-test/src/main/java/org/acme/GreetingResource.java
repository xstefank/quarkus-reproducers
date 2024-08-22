package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        method(arr);
        return "Hello from Quarkus REST";
    }

    private void method(String... args) {
        System.out.println(Arrays.toString(args));
    }
}
