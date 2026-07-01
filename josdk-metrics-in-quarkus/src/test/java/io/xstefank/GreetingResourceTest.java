package io.xstefank;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.containsString;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kubernetes.client.KubernetesServerTestResource;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(KubernetesServerTestResource.class)
class GreetingResourceTest {

    @Test
    void josdkReconciliationMetricsAppearInPrometheus() {
        // Create a CR to trigger reconciliation
        given().when().post("/trigger").then().statusCode(200);

        // JOSDK reconciliation metrics must appear in /q/metrics once reconciliation runs.
        // With the bug (Metrics.NOOP wired at startup), these metrics never appear.
        await().atMost(10, SECONDS).untilAsserted(() ->
                given()
                    .when().get("/q/metrics")
                    .then()
                        .statusCode(200)
                        .body(containsString("reconciliations_")));
    }
}
