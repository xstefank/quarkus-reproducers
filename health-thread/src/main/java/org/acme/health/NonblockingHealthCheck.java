package org.acme.health;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class NonblockingHealthCheck implements AsyncHealthCheck {
    @Override
    public Uni<HealthCheckResponse> call() {
        System.out.println(getClass().getName() + ": " + Thread.currentThread().getName());
        return Uni.createFrom().item(HealthCheckResponse.up("async"));
    }
}
