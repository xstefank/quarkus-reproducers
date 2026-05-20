package io.xstefank;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

@Path("/participant1")
public class LRAParticipant1 {

    @LRA(end = false, cancelOn = { Response.Status.BAD_GATEWAY, Response.Status.BAD_REQUEST, Response.Status.SERVICE_UNAVAILABLE, Response.Status.GATEWAY_TIMEOUT })
    @GET
    @Path("/create")
    public String create(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("Creating LRA " + lraId);
        return lraId;
    }

    @LRA
    @GET
    @Path("/end")
    public String end(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        Log.info("Ending LRA " + lraId);
        return lraId;
    }

    @Compensate
    @PUT
    @Path("/compensate")
    public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
                               @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) String recoveryId) {
        Log.info("Compensating LRA " + lraId + " to " + recoveryId);
        return Response.ok().build();
    }


}
