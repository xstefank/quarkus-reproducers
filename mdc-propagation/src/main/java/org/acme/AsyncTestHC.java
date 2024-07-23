package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.MDC;

//@Liveness
public class AsyncTestHC implements AsyncHealthCheck {

    @Override
    public Uni<HealthCheckResponse> call() {
        MDC.put("health-check", getClass().getSimpleName());
        Log.info("Async Health check - " + Thread.currentThread().getName());
        return Uni.createFrom().item(HealthCheckResponse.up("async-test-hc"));
    }
}
