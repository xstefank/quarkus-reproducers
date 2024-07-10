package org.acme;

import io.quarkus.logging.Log;
import io.vertx.core.Vertx;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logmanager.MDC;

@Liveness
public class TestHC implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        MDC.put("health-check", getClass().getSimpleName());
        MDC.put("context", Vertx.currentContext() != null ? String.valueOf(Vertx.currentContext().hashCode()) : "null");
        Log.info("Health check - " + Thread.currentThread().getName());
        return HealthCheckResponse.up("test-hc");
    }
}
