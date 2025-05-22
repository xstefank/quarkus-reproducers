package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

@Liveness
public class AsyncTestHC2 implements AsyncHealthCheck {

    private static final Logger log = Logger.getLogger("mdc-logger");

    @Override
    public Uni<HealthCheckResponse> call() {
        MDC.put("health-check", getClass().getSimpleName());
        MDC.put("context", Vertx.currentContext() != null ? String.valueOf(Vertx.currentContext().hashCode()) : "null");
        log.error("Async Health check 2 - " + Thread.currentThread().getName());
        return Uni.createFrom().item(HealthCheckResponse.up("async-test-hc2"));
    }
}
