package io.xstefank;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class GreetingResourceIT extends GreetingResourceTest {
    // Runs the same test, but against the packaged artifact. Which launch mode (jar / native /
    // docker container / OpenShift cluster) is used is entirely driven by build/test config - see
    // the "jar", "native", "docker" and "openshift" profiles in pom.xml.

    @Override
    @Test
    void testHelloEndpoint() {
        given()
                .log().uri()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from Quarkus REST"));
    }
}
