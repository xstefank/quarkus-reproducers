package io.xstefank;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(baseUri = "http://localhost:8081")
public interface GreetingResource {

    @GET
    @Path("/single")
    @Produces(MediaType.TEXT_PLAIN)
    String single();

    @GET
    @Path("/streaming")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    Multi<String> streaming();
}
