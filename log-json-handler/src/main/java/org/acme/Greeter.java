package org.acme;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class Greeter {

    @ConfigProperty(name = "hello")
    String greeting;

    public String sayHello() {
        Log.info("Greeting " + greeting);
        Log.error("Error Greeting " + greeting);

        return greeting;
    }
}
