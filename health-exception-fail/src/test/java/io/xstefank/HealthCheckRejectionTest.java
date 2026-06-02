package io.xstefank;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@QuarkusTest
class HealthCheckRejectionTest {

    @Test
    void rejectedHealthChecksShouldReturn503NotHang() throws Exception {
        int concurrentRequests = 5;
        ExecutorService executor = Executors.newFixedThreadPool(concurrentRequests);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < concurrentRequests; i++) {
            futures.add(executor.submit(() ->
                given()
                    .when().get("/q/health/live")
                    .then().extract().statusCode()
            ));
        }

        // All requests must complete within 5 seconds.
        // With the bug, rejected requests hang indefinitely (executeBlocking future ignored).
        // With the fix, rejected requests return 503 immediately.
        for (int i = 0; i < futures.size(); i++) {
            try {
                int status = futures.get(i).get(5, TimeUnit.SECONDS);
                assertThat(status).as("request %d status", i).isIn(200, 503);
            } catch (TimeoutException e) {
                fail("Request %d hung instead of returning 503 — executeBlocking future was ignored", i);
            }
        }

        executor.shutdownNow();
    }
}
