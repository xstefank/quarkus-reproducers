package io.xstefank;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    TestAiService testAiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println(testAiService.test("Generate a random data for the model."));
        return "Hello from Quarkus REST";
    }
}
