package io.xstefank;

import io.smallrye.health.SmallRyeHealthReporter;
import io.smallrye.health.api.HealthRegistry;
import io.smallrye.health.api.HealthType;
import io.smallrye.health.registry.HealthRegistries;
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

        HealthRegistry livenessRegistry = HealthRegistries.getRegistry(HealthType.LIVENESS);
        livenessRegistry.register(() -> HealthCheckResponse.up("liveness"));

        HealthRegistry readinessRegistry = HealthRegistries.getRegistry(HealthType.READINESS);
        readinessRegistry.register(() -> HealthCheckResponse.up("readiness"));

        System.out.println("Reporting health checks:");
        System.out.println("Liveness:");
        smallRyeHealthReporter.reportHealth(System.out, smallRyeHealthReporter.getLiveness());
        System.out.println("Readiness:");
        smallRyeHealthReporter.reportHealth(System.out, smallRyeHealthReporter.getReadiness());
        System.out.println("All:");
        smallRyeHealthReporter.reportHealth(System.out, smallRyeHealthReporter.getHealth());


        return "Hello from Quarkus REST";
    }
}
