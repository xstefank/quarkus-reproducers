package org.acme;

import io.quarkus.agroal.runtime.health.DataSourceHealthCheck;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.health.api.HealthType;
import io.smallrye.health.registry.HealthRegistries;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class StartObserver {

    @Inject
    @Readiness
    DataSourceHealthCheck healthCheck;

    public void observeStart(@Observes StartupEvent startupEvent) {
        HealthRegistries.getRegistry(HealthType.LIVENESS).register(healthCheck);
    }
}
