package org.acme;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.health.SmallRyeHealth;
import io.smallrye.health.SmallRyeHealthReporter;
import io.smallrye.mutiny.Uni;
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
    @NonBlocking
    public Uni<SmallRyeHealth> hello() {
        return smallRyeHealthReporter.getHealthAsync();
    }
}
