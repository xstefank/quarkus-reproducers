package org.acme;

import io.quarkus.logging.Log;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Liveness
@Dependent
public class DependentHealthCheck implements HealthCheck {

    @Inject
    Bean bean;

    List<GreetingResource> greetingResourceList = new ArrayList<>();

    @Override
    public HealthCheckResponse call() {
        greetingResourceList.add(new GreetingResource());
        Log.info("Dependent health check called");
        Log.info("Bean " + bean.getId());
        Log.error(Instant.now());
        return HealthCheckResponse.up("Dependent health check");
    }

    @PreDestroy
    public void preDestroy() {
        Log.info("PreDestroy called");
        Log.info(Instant.now());
    }
}
