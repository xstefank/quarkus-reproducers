package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.RepeatedTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class MDCLoggingTest {

    @Inject
    InMemoryLogHandler inMemoryLogHandler;
    @Inject
    InMemoryLogHandlerProducer producer;

    @RepeatedTest(10)
    void testMDCLogging() {
        InMemoryLogHandler.reset();

        given().get("/q/health").then().statusCode(200);

        assertThat(inMemoryLogHandler.logRecords())
            .hasSize(4)
            .anyMatch(record -> record.contains("health-check=TestHC"))
            .anyMatch(record -> record.contains("health-check=TestHC2"))
            .anyMatch(record -> record.contains("health-check=AsyncTestHC"))
            .anyMatch(record -> record.contains("health-check=AsyncTestHC2"));
    }
}
