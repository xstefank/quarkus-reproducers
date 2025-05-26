package io.xstefank;

import io.quarkus.logging.Log;
import io.smallrye.common.vertx.VertxContext;
import io.vertx.core.Vertx;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.MDC;

@Path("/hello")
public class GreetingResource {

    @Inject
    Vertx vertx;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("Vertx.currentContext() = " + Vertx.currentContext().hashCode());
        MDC.put("parent", "test-parent");
        Log.error("Main Request - Vertx.currentContext() = " + Vertx.currentContext().hashCode());

        VertxContext.newNestedContext().executeBlocking(() -> {
            MDC.put("nested", "test-nested");
            Log.error("Nested - Vertx.currentContext() = " + Vertx.currentContext().hashCode());
            return null;
        });

        return "Hello from Quarkus REST";
    }
}
