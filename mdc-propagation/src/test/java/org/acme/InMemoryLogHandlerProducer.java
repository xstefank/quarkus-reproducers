package org.acme;

import io.quarkus.bootstrap.logging.InitialConfigurator;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class InMemoryLogHandlerProducer {

    public volatile boolean initialized = false;

    @Produces
    @Singleton
    public InMemoryLogHandler inMemoryLogHandler() {
        return new InMemoryLogHandler();
    }

    public boolean isInitialized() {
        return initialized;
    }

    void onStart(@Observes StartupEvent ev, InMemoryLogHandler inMemoryLogHandler) {
        InitialConfigurator.DELAYED_HANDLER.addHandler(inMemoryLogHandler);
        initialized = true;
    }

    void onStop(@Observes ShutdownEvent ev, InMemoryLogHandler inMemoryLogHandler) {
        initialized = false;
        InitialConfigurator.DELAYED_HANDLER.removeHandler(inMemoryLogHandler);
    }
}
