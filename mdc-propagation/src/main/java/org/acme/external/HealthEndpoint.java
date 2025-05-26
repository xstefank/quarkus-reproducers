package org.acme.external;

import io.smallrye.common.vertx.VertxContext;
import io.smallrye.health.SmallRyeHealth;
import io.smallrye.health.SmallRyeHealthReporter;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.MutinyHelper;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.MDC;

@Path("/health")
@ApplicationScoped
public class HealthEndpoint {

  @Inject
  SmallRyeHealthReporter smallRyeHealthReporter;

  @GET
  public Response health() {
    System.out.println("-----------------------------------------------------------------------");
    MDC.put("class", "HealthEndpoint");

//    Context context = Vertx.currentContext();
//    System.out.println("context = " + context.getClass());
//    Uni<SmallRyeHealth> healthAsync = smallRyeHealthReporter.getHealthAsync();
//    healthAsync.emitOn(MutinyHelper.executor(VertxContext.newNestedContext(context)));
//    String s = healthAsync.await().indefinitely().getPayload().toString();
    String s = smallRyeHealthReporter.getHealth().getPayload().toString();

    return Response.ok(s).build();
  }
}
