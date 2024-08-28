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
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.temporal.ChronoUnit;

@Path("/hello")
public class GreetingResource {

    @Inject
    @RestClient
    Service2Client service2Client;

    @Inject
    @RestClient
    Service3Client service3Client;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @LRA
    public Response hello(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("LRA started " + lraId);

        // call service2
        service2Client.hello(lraId);

        // call service3
        service3Client.hello(lraId);

        // wait a little for enlistments to be processed
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // cancel LRA manually
        return Response.status(200).build();
    }

    @PUT
    @Path("/after")
    @AfterLRA
    public void afterLRA(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) String endedLra) {
        Log.info("LRA ended " + endedLra);
    }
}
