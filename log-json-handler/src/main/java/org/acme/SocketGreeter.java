package org.acme;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SocketGreeter {

    @ConfigProperty(name = "hello")
    String greeting;

    public String sayHello() {
        Log.info("SocketGreeter Greeting " + greeting);
        Log.error("SocketGreeter Error Greeting " + greeting);

        return greeting;
    }
}
