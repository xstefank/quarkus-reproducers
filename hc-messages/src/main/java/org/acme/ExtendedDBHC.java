package org.acme;

import io.quarkus.agroal.runtime.health.DataSourceHealthCheck;
import io.smallrye.health.api.HealthGroup;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
public class ExtendedDBHC extends DataSourceHealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponse result = super.call();
//        if (result.getStatus().equals(HealthCheckResponse.Status.DOWN)) {
            System.out.println("Send message here");
//        }
        return result;
    }
}
