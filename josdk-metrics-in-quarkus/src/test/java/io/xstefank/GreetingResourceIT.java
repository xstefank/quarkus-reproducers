package io.xstefank;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.kubernetes.client.KubernetesServerTestResource;

/**
 * Reproducer for https://github.com/quarkiverse/quarkus-operator-sdk/issues/1385
 *
 * Runs the same test as GreetingResourceTest but against the packaged JAR (prod profile).
 *
 * Expected result with quarkus-operator-sdk 7.7.5 (bug present):
 *   FAIL — MicrometerMetricsProvider.bindTo() is called after ConfigurationServiceRecorder
 *   captures the Metrics bean, so Metrics.NOOP is used and no JOSDK reconciliation metrics
 *   appear in /q/metrics even after reconciliation.
 *
 * Expected result after the fix:
 *   PASS — MicrometerMetricsV2 is properly wired and reconciliation metrics appear in /q/metrics.
 */
@QuarkusIntegrationTest
@QuarkusTestResource(KubernetesServerTestResource.class)
class GreetingResourceIT extends GreetingResourceTest {
}
