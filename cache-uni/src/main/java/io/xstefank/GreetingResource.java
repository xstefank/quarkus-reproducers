package io.xstefank;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.MutinyHelper;
import io.vertx.core.Vertx;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.atomic.AtomicInteger;

@Path("/hello")
public class GreetingResource {

    private Uni<String> cachedUni;

    private final AtomicInteger counter = new AtomicInteger();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        System.out.println("Vertx.currentContext in hello(): " + Vertx.currentContext());
        if (cachedUni == null) {
            cachedUni = Uni.createFrom().item(() -> {
                System.out.println("Vertx.currentContext in hello() item(): " + Vertx.currentContext());
                var s = "Hello World! " + counter.incrementAndGet();
                return s;
            });
        }

        return cachedUni.emitOn(MutinyHelper.executor(Vertx.currentContext()));
    }
}
