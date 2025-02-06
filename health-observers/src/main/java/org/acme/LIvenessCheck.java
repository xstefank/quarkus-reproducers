package org.acme;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import java.util.concurrent.atomic.AtomicInteger;

@Liveness
public class LIvenessCheck implements HealthCheck {

    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public HealthCheckResponse call() {
//        return counter.getAndIncrement() % 3 != 0 ? HealthCheckResponse.up("test-liveness") : HealthCheckResponse.down("test-liveness");
        return HealthCheckResponse.up("test-liveness");
    }
}
