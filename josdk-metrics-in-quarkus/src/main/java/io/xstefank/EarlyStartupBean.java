package io.xstefank;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.javaoperatorsdk.operator.api.config.ConfigurationService;
import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class EarlyStartupBean {

    @Inject
    ConfigurationService configurationService;

    @PostConstruct
    void init() {
        // Calling a method on ConfigurationService forces the QuarkusConfigurationService
        // @Singleton supplier to run eagerly, before Micrometer's RUNTIME_INIT step calls
        // MicrometerMetricsProvider.bindTo(). At that point metrics is still Metrics.NOOP.
        configurationService.getMetrics();
    }
}
