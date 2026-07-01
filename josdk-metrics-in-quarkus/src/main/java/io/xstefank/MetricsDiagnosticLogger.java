package io.xstefank;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

import io.javaoperatorsdk.operator.api.config.ConfigurationService;
import io.javaoperatorsdk.operator.api.monitoring.Metrics;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class MetricsDiagnosticLogger {

    private static final Logger log = Logger.getLogger(MetricsDiagnosticLogger.class);

    @Inject
    ConfigurationService configurationService;

    @Inject
    MeterRegistry meterRegistry;

    void onStart(@Observes StartupEvent event) {
        Metrics metrics = configurationService.getMetrics();
        log.infof("metricsBeanClass='%s'", metrics.getClass().getName());
        log.infof("metricsBeanIsNoop=%s", metrics == Metrics.NOOP);
        log.infof("registryClass='%s'", meterRegistry.getClass().getName());
    }
}
