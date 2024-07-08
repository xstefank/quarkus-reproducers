package org.acme;

import io.quarkus.arc.Arc;
import io.quarkus.virtual.threads.VirtualThreads;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.concurrent.ExecutorService;

@Path("/hello")
public class GreetingResource {

    @VirtualThreads
    ExecutorService executor;

    @Channel("test-channel")
    Emitter<Long> emitter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        executor.submit(() -> {
            GreetingResource greetingResource = Arc.container().select(GreetingResource.class).get();
            System.out.println(greetingResource);
        });

        emitter.send(1L);

        return "Hello from Quarkus REST";
    }

    @Inject
    TestBean testBean;

    @Incoming("test-channel")
    @RunOnVirtualThread
    public void consumeTicks(Long tick) throws InterruptedException {
        System.out.println(testBean.get());
    }
}
