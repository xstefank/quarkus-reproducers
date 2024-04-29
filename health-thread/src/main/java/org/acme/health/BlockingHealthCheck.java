package org.acme.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;


@Liveness
public class BlockingHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        System.out.println(getClass().getName() + ": " + Thread.currentThread().getName());
        return HealthCheckResponse.up("blocking");
    }
}
