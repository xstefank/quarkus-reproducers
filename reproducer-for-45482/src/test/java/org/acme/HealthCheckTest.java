package org.acme;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.mongodb.MongoTimeoutException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HealthCheckTest {

    @Test
    void testHealthCheck() {
        given()
                .when().get("/q/health/ready")
                .then()
                .statusCode(200)
                .body(Matchers.containsString("\"status\": \"UP\""));

        var dummyEntity = new DummyEntity();
        dummyEntity.test = "Test";
        try {
            dummyEntity.persist();
        } catch (MongoTimeoutException e) {
            // Ignoring expected exception
        }

        given()
                .when().get("/q/health/ready")
                .then()
                .body(Matchers.containsString("\"status\": \"DOWN\""));
    }
}
