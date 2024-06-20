package org.acme;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.time.Duration;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @Outgoing("test-topic")
    public Multi<String> produce() {
        return Multi.createFrom().ticks().every(Duration.ofMillis(100))
            .map(t -> "Event " + t);
    }
}
