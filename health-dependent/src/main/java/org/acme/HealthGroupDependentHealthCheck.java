package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.health.api.HealthGroup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@HealthGroup("group1")
@Dependent
public class HealthGroupDependentHealthCheck implements HealthCheck {

    @Inject
    Bean bean;

    @Override
    public HealthCheckResponse call() {
        Log.info("HealthGroupDependentHealthCheck called");
        Log.info(bean.id);
        return HealthCheckResponse.up("HealthGroupDependentHealthCheck");
    }

    @PreDestroy
    public void preDestroy() {
        Log.info("PreDestroy called");
    }
}
