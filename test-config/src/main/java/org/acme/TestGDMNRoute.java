package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
//@Slf4j
public class TestGDMNRoute {

    private final TestConfig testConfig;

    @Inject
    public TestGDMNRoute(@ConfigProperties(prefix = "test") TestConfig testConfig) {
        this.testConfig = testConfig;
    }
}