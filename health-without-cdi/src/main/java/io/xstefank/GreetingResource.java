package io.xstefank;

import io.smallrye.health.SmallRyeHealthReporter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        SmallRyeHealthReporter smallRyeHealthReporter = new SmallRyeHealthReporter();
        smallRyeHealthReporter.postConstruct();
        smallRyeHealthReporter.addHealthCheck(() -> HealthCheckResponse.up("healthy"));
        smallRyeHealthReporter.reportHealth(System.out, smallRyeHealthReporter.getHealth());

        return "Hello from Quarkus REST";
    }
}
