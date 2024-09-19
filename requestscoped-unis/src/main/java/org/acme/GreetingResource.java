package org.acme;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    private Uni<String> cache = Uni.createFrom().item(() -> {
        System.out.println("Creating the item");
        return "hello";
    });

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        cache.subscribe().with(item -> System.out.println("Got the item: " + item));
        cache.subscribe().with(item -> System.out.println("Got the item: " + item));
        cache.subscribe().with(item -> System.out.println("Got the item: " + item));


        return "Hello from Quarkus REST";
    }
}
