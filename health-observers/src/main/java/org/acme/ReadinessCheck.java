package org.acme;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import java.util.concurrent.atomic.AtomicInteger;

@Readiness
public class ReadinessCheck implements HealthCheck {

    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public HealthCheckResponse call() {
//        return counter.getAndIncrement() % 3 != 0 ? HealthCheckResponse.up("test-readiness") : HealthCheckResponse.down("test-readiness");
        return HealthCheckResponse.up("test-readiness");
    }
}
