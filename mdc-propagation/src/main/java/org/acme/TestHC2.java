package org.acme;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.MDC;

@Liveness
public class TestHC2 implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        MDC.put("health-check", getClass().getSimpleName());
        Log.info("Health check 2");
        return HealthCheckResponse.up("test-hc2");
    }
}
