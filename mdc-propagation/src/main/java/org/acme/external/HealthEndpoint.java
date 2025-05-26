package org.acme.external;

import io.quarkus.logging.Log;
import io.quarkus.vertx.core.runtime.VertxMDC;
import io.smallrye.common.vertx.VertxContext;
import io.smallrye.health.SmallRyeHealthReporter;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.MDC;

import java.util.UUID;

@Path("/health")
@ApplicationScoped
public class HealthEndpoint {

  public static final Object LOCK = new Object();

  @Inject
  SmallRyeHealthReporter smallRyeHealthReporter;

  @GET
  public Response health() {
    System.out.println("-----------------------------------------------------------------------");
//    VertxMDC.INSTANCE.putObject("class", "HealthEndpoint asdf", VertxContext.newNestedContext());
    MDC.put("class", "HealthEndpoint");
//    Vertx.currentContext().putLocal("class", "HealthEndpoint asdf");

    String id = UUID.randomUUID().toString();
    System.out.println("Main thread " + id);
    // this was testing custom 999-SNAPSHOT
//    VertxMDC.INSTANCE.contextualDataMap(Vertx.currentContext(), id);
    String s = smallRyeHealthReporter.getHealth().getPayload().toString();
//    Log.error("TASEASDF");

    return Response.ok(s).build();
  }
}
