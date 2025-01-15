package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    AppScopedBean appScopedBean;

    @Inject
    RequestScopedBean requestScopedBean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("APP " + appScopedBean);
        System.out.println("APP manual " + AppScopedBean.getInstance());
        System.out.println("REQ " + requestScopedBean);
        return "Hello from Quarkus REST";
    }
}
