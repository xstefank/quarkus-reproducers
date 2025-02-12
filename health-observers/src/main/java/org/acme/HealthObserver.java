package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.health.api.Wellness;
import io.smallrye.health.api.event.HealthStatusChangeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Default;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class HealthObserver {

    private AtomicInteger counterHealth = new AtomicInteger();
    private AtomicInteger counterLiveness = new AtomicInteger();
    private AtomicInteger counterReadiness = new AtomicInteger();
    private AtomicInteger counterWellness = new AtomicInteger();
    private AtomicInteger counterStartup = new AtomicInteger();

    public void observeHealth(@Observes @Default HealthStatusChangeEvent event) throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        System.out.println("HEALTH counter = " + counterHealth.incrementAndGet());
        System.out.println(event.healthType());
        System.out.println(event.status());
        System.out.println(event.timestamp());
    }

    public void observeLiveness(@Observes @Liveness HealthStatusChangeEvent event) {
        System.out.println("LIVENESS counter = " + counterLiveness.incrementAndGet());
        System.out.println(event.healthType());
        System.out.println(event.status());
        System.out.println(event.timestamp());
    }

    public void observeReadiness(@Observes @Readiness HealthStatusChangeEvent event) {
        System.out.println("READINESS counter = " + counterReadiness.incrementAndGet());
        System.out.println(event.healthType());
        System.out.println(event.status());
        System.out.println(event.timestamp());
    }

    public void observeWellness(@Observes @Wellness HealthStatusChangeEvent event) {
        System.out.println("WELLNESS counter = " + counterWellness.incrementAndGet());
        System.out.println(event.healthType());
        System.out.println(event.status());
        System.out.println(event.timestamp());
    }

    public void observeStartup(@Observes @Startup HealthStatusChangeEvent event) {
        System.out.println("STARTUP counter = " + counterStartup.incrementAndGet());
        System.out.println(event.healthType());
        System.out.println(event.status());
        System.out.println(event.timestamp());
    }
}
