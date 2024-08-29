package org.acme;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.Dependent;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@Dependent
public class AsyncDependentHealthCheck implements AsyncHealthCheck {
    @Override
    public Uni<HealthCheckResponse> call() {
        return Uni.createFrom().item(() -> HealthCheckResponse.up("Async Dependent health check"));
    }
}
