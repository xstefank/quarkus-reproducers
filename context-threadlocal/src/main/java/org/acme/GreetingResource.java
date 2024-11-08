package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Path("/hello")
public class GreetingResource {

    private final TenantContext tenantContext = new TenantContext();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws ExecutionException, InterruptedException {

        tenantContext.setTenant("parent");
        Log.infof("Main thread tenant %s", tenantContext.getTenant());

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                Log.infof("Child thread tenant %s", tenantContext.getTenant());
                tenantContext.setTenant("child");
                Log.infof("Child thread tenant %s", tenantContext.getTenant());
            }
        }).get();
        Log.infof("Main thread tenant %s", tenantContext.getTenant());

        return "Hello from Quarkus REST";
    }

    public static final class TenantContext {
        private ThreadLocal<String> tenant = new ThreadLocal<>();

        public void setTenant(String tenant) {
            this.tenant.set(tenant);
        }

        public String getTenant() {
            return tenant.get();
        }
    }
}
