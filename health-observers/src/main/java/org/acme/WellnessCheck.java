package org.acme;

import io.smallrye.health.api.Wellness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import java.util.concurrent.atomic.AtomicInteger;

@Wellness
public class WellnessCheck implements HealthCheck {

    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public HealthCheckResponse call() {
//        return counter.getAndIncrement() % 3 != 0 ? HealthCheckResponse.up("test-wellness") : HealthCheckResponse.down("test-wellness");
        return HealthCheckResponse.up("test-wellness");
    }
}
