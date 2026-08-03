package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class GreetingResourceIT extends GreetingResourceTest {
    // Runs the same test, but against the packaged artifact. Which launch mode (jar / native /
    // docker container / kubernetes) is used is entirely driven by build/test config - see the
    // "jar", "native", "docker" and "k8s" profiles in pom.xml.

    @Override
    @Test
    void testHelloEndpoint() {
        given()
                .log().uri()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("hello"));
        try {
            // gives a window to observe the deployed pod in k9s before teardown deletes it
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
