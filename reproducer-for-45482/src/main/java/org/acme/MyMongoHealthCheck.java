package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.mongodb.health.MongoHealthCheck;
import io.quarkus.mongodb.runtime.MongodbConfig;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class MyMongoHealthCheck implements HealthCheck {

    @Inject
    MongodbConfig mongodbConfig;

    private MongoHealthCheck mongoHealthCheck;

    private HealthCheckResponse cachedResponse = HealthCheckResponse.up("MongoDB connection health check");

    @PostConstruct
    public void init() {
        mongoHealthCheck = new MongoHealthCheck(mongodbConfig);
    }

    @Scheduled(every = "10s")
    void invokeHealthCheck() {
        Log.info("Mongo health check invoked.");
        cachedResponse = mongoHealthCheck.call();
    }

    @Override
    public HealthCheckResponse call() {
        return cachedResponse;
    }
}
