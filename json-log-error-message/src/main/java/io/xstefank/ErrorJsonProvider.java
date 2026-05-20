package io.xstefank;

import io.quarkus.logging.json.runtime.JsonFormatter;
import io.quarkus.logging.json.runtime.JsonProvider;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logmanager.ExtLogRecord;

import java.util.Arrays;

@ApplicationScoped
public class ErrorJsonProvider implements JsonProvider {

    @Override
    public void writeTo(JsonFormatter.JsonLogGenerator generator, ExtLogRecord record) throws Exception {
        if (record.getThrown() != null) {
            generator.add("error-message", "%s | %s | %s".formatted(record.getThrown().getClass(), record.getThrown().getMessage(),
                Arrays.toString(record.getThrown().getStackTrace())));
        }
    }
}
