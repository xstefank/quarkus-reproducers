package org.acme;

import io.quarkus.agroal.runtime.health.DataSourceHealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class ExtendedDBHC extends DataSourceHealthCheck {

    @Override
    public HealthCheckResponse call() {
        return super.call();
    }
}
