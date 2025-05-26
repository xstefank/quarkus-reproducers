package org.acme;

import io.quarkus.logging.Log;
import io.vertx.core.Vertx;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

@Liveness
public class TestHC implements HealthCheck {

    private static final Logger log = Logger.getLogger("mdc-logger");

    @Override
    public HealthCheckResponse call() {
        System.out.println("TestHC.call() with context " + Vertx.currentContext().hashCode());

        MDC.put("health-check", getClass().getSimpleName());
        MDC.put("context", Vertx.currentContext() != null ? String.valueOf(Vertx.currentContext().hashCode()) : "null");
        log.error("Health check - " + Thread.currentThread().getName());
        return HealthCheckResponse.up("test-hc");
    }
}
