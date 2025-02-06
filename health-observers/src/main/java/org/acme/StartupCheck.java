package org.acme;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import java.util.concurrent.atomic.AtomicInteger;

@Startup
public class StartupCheck implements AsyncHealthCheck {

    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Uni<HealthCheckResponse> call() {
        return Uni.createFrom().item(counter.getAndIncrement() % 3 != 0 ? HealthCheckResponse.up("test-startup") : HealthCheckResponse.down("test-startup"));
    }
}
