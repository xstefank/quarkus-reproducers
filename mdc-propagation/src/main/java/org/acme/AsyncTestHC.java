package org.acme;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

@Liveness
public class AsyncTestHC implements AsyncHealthCheck {

    private static final Logger log = Logger.getLogger("mdc-logger");

    @Override
    public Uni<HealthCheckResponse> call() {
        MDC.put("health-check", getClass().getSimpleName());
        log.error("Async Health check - " + Thread.currentThread().getName());
        return Uni.createFrom().item(HealthCheckResponse.up("async-test-hc"));
    }
}
