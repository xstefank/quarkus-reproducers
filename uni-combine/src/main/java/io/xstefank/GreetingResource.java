package io.xstefank;

import io.smallrye.health.SmallRyeHealthReporter;
import io.smallrye.health.api.HealthType;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello")
public class GreetingResource {

    private static final AtomicLong COUNTER = new AtomicLong();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        Map<HealthType, Uni<String>> healthResults = new EnumMap<>(HealthType.class);
        healthResults.put(HealthType.LIVENESS, Uni.createFrom().item("liveness1"));
        healthResults.put(HealthType.READINESS, Uni.createFrom().item("readiness1"));


        return Uni.combine().all().unis(healthResults.getOrDefault(HealthType.LIVENESS, Uni.createFrom().item("default1")),
                healthResults.getOrDefault(HealthType.READINESS, Uni.createFrom().item("default2")),
                healthResults.getOrDefault(HealthType.WELLNESS, Uni.createFrom().item("default3")),
                healthResults.getOrDefault(HealthType.STARTUP, Uni.createFrom().item("default4")))
            .with((s, s2, s3, s4) -> {
                System.out.println("result " + COUNTER.incrementAndGet());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (COUNTER.get() % 100 == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "asdfasdf";
            });
    }
}
