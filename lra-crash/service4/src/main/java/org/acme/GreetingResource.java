package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @LRA(value = LRA.Type.MANDATORY, end = false)
    public String hello(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("Joined LRA " + lraId);
        return "Hello from Quarkus REST";
    }

    @PUT
    @Path("/compensate")
    @Compensate
    public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("Compensate for LRA " + lraId);
        return Response.ok().build();
    }
}
