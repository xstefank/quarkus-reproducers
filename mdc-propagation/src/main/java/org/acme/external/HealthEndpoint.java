package org.acme.external;

import io.smallrye.health.SmallRyeHealthReporter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.MDC;

@Path("/health")
@ApplicationScoped
public class HealthEndpoint {
  @Inject
  SmallRyeHealthReporter smallRyeHealthReporter;

  @GET
  public Response health() {
    MDC.put("class", "HealthEndpoint");
    return Response.ok(smallRyeHealthReporter.getHealth().getPayload().toString()).build();
  }
}
