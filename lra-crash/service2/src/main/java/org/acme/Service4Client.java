package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8084")
public interface Service4Client {

    @GET
    @Path("/hello")
    String hello(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId);
}
