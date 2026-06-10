package io.xstefank;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class Reproducer {
    void triggerBug(@Observes StartupEvent startupEvent) {
        try {
            throw new ReproducerException();
        } catch (ReproducerException e) {
            Log.warnf(e, "This is a reproducer (%s)", e.getMessage());
        }
    }

    public static class ReproducerException extends RuntimeException {
    }
}