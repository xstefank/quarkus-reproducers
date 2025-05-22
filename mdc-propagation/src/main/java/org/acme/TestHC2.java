package org.acme;

import io.vertx.core.Vertx;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

@Liveness
public class TestHC2 implements HealthCheck {

    private static final Logger log = Logger.getLogger("mdc-logger");

    @Override
    public HealthCheckResponse call() {
        MDC.put("health-check", getClass().getSimpleName());
        MDC.put("context", Vertx.currentContext() != null ? String.valueOf(Vertx.currentContext().hashCode()) : "null");
        log.error("Health check 2 - " + Thread.currentThread().getName());
        return HealthCheckResponse.up("test-hc2");
    }
}
