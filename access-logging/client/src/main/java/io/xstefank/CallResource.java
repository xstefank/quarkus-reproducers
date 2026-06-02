package io.xstefank;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/call")
public class CallResource {

    @Inject
    @RestClient
    GreetingResource greetingResource;

    @GET
    @Path("/single")
    @Produces(MediaType.TEXT_PLAIN)
    public String single() {
        return greetingResource.single();
    }

    @GET
    @Path("/streaming")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> streaming() {
        return greetingResource.streaming().map(s -> "call: " + s);
    }

}
