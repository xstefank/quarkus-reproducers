package org.acme;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class FileGreeter {

    @ConfigProperty(name = "hello")
    String greeting;

    public String sayHello() {
        Log.info("FileGreeter Greeting " + greeting);
        Log.error("FileGreeter Error Greeting " + greeting);

        return greeting;
    }
}
