package org.acme.external;

import io.smallrye.common.vertx.VertxContext;
import io.smallrye.health.AsyncHealthCheckFactory;
import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.MutinyHelper;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

@Singleton
@Alternative
@Priority(1)
public class QuarkusAsyncHealthCheckFactory extends AsyncHealthCheckFactory {

    private final Vertx vertx;

    public QuarkusAsyncHealthCheckFactory(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public Uni<HealthCheckResponse> callSync(HealthCheck healthCheck) {
        Uni<HealthCheckResponse> healthCheckResponseUni = super.callSync(healthCheck);
        return healthCheckResponseUni.runSubscriptionOn(new Executor() {
            @Override
            public void execute(Runnable command) {
                Context orCreateContext = Vertx.vertx().getOrCreateContext();
                System.out.println("orCreateContext = " + orCreateContext);
                Context duplicatedContext = VertxContext.createNewDuplicatedContext(orCreateContext);
                duplicatedContext.executeBlocking(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        command.run();
                        return null;
                    }
                }, false);
            }
        });
    }

    @Override
    public Uni<HealthCheckResponse> callAsync(AsyncHealthCheck asyncHealthCheck) {
        Uni<HealthCheckResponse> healthCheckResponseUni = super.callAsync(asyncHealthCheck);
        return healthCheckResponseUni.runSubscriptionOn(MutinyHelper.executor(vertx));
    }
}