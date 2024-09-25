package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@RequestScoped
public class SimpleHealthCheck implements AsyncHealthCheck {
    @Override
    public Uni<HealthCheckResponse> call() {
        return Uni.createFrom().item(HealthCheckResponse.up("Simple health check"));
    }

    @PostConstruct
    void init() {
        Log.warn("Init called " + this);
    }

    @PreDestroy
    void destroy() {
        Log.warn("Destroy called " + this);
    }
}
