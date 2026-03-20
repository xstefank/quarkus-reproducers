package org.acme;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SyslogGreeter {

    @ConfigProperty(name = "hello")
    String greeting;

    public String sayHello() {
        Log.info("SyslogGreeter Greeting " + greeting);
        Log.error("SyslogGreeter Error Greeting " + greeting);

        return greeting;
    }
}
