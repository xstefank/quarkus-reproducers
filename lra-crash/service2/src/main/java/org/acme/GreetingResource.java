package org.acme;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hello")
public class GreetingResource {

    @Inject
    @RestClient
    Service4Client service4Client;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @LRA(value = LRA.Type.MANDATORY, end = false)
    public String hello(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("Joined LRA " + lraId);
        return "Hello from Quarkus REST";
    }

    boolean invoked = false;

    @PUT
    @Path("/compensate")
    @Compensate
    public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("Compensate for " + lraId);
//        Log.info("Calling service4");
//        service4Client.hello(lraId);
//
//        if (!invoked) {
//            invoked = true;
//            Log.info("Waiting for 2 seconds");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
        return Response.ok().build();
    }

}
