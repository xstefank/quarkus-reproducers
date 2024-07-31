package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperties;

//@Data
@ApplicationScoped
@ConfigProperties(prefix = "test")
public class TestConfig {
    private String path;
}
