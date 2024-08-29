package org.acme;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@Dependent
public class AsyncDependentHealthCheck implements AsyncHealthCheck {

    @Inject
    Bean bean;

    @Override
    public Uni<HealthCheckResponse> call() {
        System.out.println("Async Dependent health check called");
        System.out.println("Bean " + bean.getId());
        return Uni.createFrom().item(() -> HealthCheckResponse.up("Async Dependent health check"));
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PreDestroy called");
    }
}
