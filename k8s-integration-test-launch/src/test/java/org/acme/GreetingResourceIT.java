package org.acme;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class GreetingResourceIT extends GreetingResourceTest {
    // Runs the same test, but against the packaged artifact. Which launch mode (jar / native /
    // docker container / kubernetes) is used is entirely driven by build/test config - see the
    // "jar", "native", "docker" and "k8s" profiles in pom.xml.
}
