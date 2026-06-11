package io.quarkiverse.operatorsdk.reproducer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class UnwrappedTypeResolverStartup {

    void onStart(@Observes StartupEvent event) throws Exception {
        // class name passed at runtime so GraalVM cannot detect it via static analysis
        String className = System.getProperty("reproducer.class",
                "io.fabric8.kubernetes.model.jackson.UnwrappedTypeResolverBuilder");
        Class.forName(className).getDeclaredConstructor().newInstance();
    }
}
