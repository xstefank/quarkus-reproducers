package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.common.vertx.VertxContext;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.concurrent.ExecutionException;

@Path("/hello")
public class GreetingResource {

    private final TenantContext tenantContext = new TenantContext();

    @Inject
    Vertx vertx;

    @Inject
    ManagedExecutor executor;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws ExecutionException, InterruptedException {

        tenantContext.setTenant("parent");
        Log.infof("Main thread tenant %s", tenantContext.getTenant());

        executor.runAsync(this::callChild);

        Log.infof("Main thread tenant %s", tenantContext.getTenant());

        return "Hello from Quarkus REST";
    }

    private void callChild() {
        getContext().removeLocal("tenant");
        Log.infof("Child thread tenant %s", tenantContext.getTenant());
        tenantContext.setTenant("child");
        Log.infof("Child thread tenant %s", tenantContext.getTenant());
    }

    public final class TenantContext {

        public void setTenant(String tenant) {
            getContext().putLocal("tenant", tenant);
        }

        public String getTenant() {
            return getContext().getLocal("tenant");
        }
    }

    private Context getContext() {
        Context ctxt = Vertx.currentContext();
        if (ctxt != null && VertxContext.isDuplicatedContext(ctxt)) {
            return ctxt;
        } else {
            Context current = vertx.getOrCreateContext();
            return VertxContext.createNewDuplicatedContext(current);
        }
    }

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello() throws ExecutionException, InterruptedException {
//
//        tenantContext.setTenant("parent");
//        Log.infof("Main thread tenant %s", tenantContext.getTenant());
//
//        Executors.newSingleThreadExecutor().submit(new Runnable() {
//            @Override
//            public void run() {
//                Log.infof("Child thread tenant %s", tenantContext.getTenant());
//                tenantContext.setTenant("child");
//                Log.infof("Child thread tenant %s", tenantContext.getTenant());
//            }
//        }).get();
//        Log.infof("Main thread tenant %s", tenantContext.getTenant());
//
//        return "Hello from Quarkus REST";
//    }


}
