package io.xstefank;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import io.javaoperatorsdk.operator.monitoring.micrometer.MicrometerMetricsV2;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kubernetes.client.KubernetesServerTestResource;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(KubernetesServerTestResource.class)
class GreetingResourceTest {

    @Test
    void josdkShouldUseDefaultMicrometerMetrics() {
        // With the bug, the operator is wired with Metrics.NOOP (inner class Metrics$1)
        // instead of MicrometerMetricsV2 when running in prod/packaged mode.
        given().when().get("/trigger/metrics-type")
                .then()
                .statusCode(200)
                .body(
                    not(containsString("Metrics$1")),
                    containsString(MicrometerMetricsV2.class.getName()));
    }
}
