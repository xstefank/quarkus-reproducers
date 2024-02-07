package org.acme;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class TestHC2 implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("test-2");
    }
}
