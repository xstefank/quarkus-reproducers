package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class HealthTest {

    @Test
    void testHealth() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                given()
                    .when().get("/q/health/ready")
                    .then()
                    .statusCode(200)
                    .body("checks[0].name", is("RequestContextActive: true"));
            }
        }
    }

}