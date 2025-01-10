package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/hello")
public class GreetingResource {

    private AtomicInteger counter = new AtomicInteger();

    private Uni<String> innerUni = Uni.createFrom().item("Hello-Inner");
    private Uni<String> innerUni2 = Uni.createFrom().item("Hello-Inner2");

    private List<Uni<String>> unis = new CopyOnWriteArrayList<>();
    private Uni<String> uni;

    public GreetingResource() {
        unis.add(innerUni);
        unis.add(innerUni2);
        uni = Uni.combine().all().unis(unis)
            .with(responses -> responses.toString())
            .onItem().invoke(s -> Log.infof("%d - %s", counter.incrementAndGet(), s));
    }



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        uni.subscribe().with(s -> System.out.println());

        return "Hello from Quarkus REST";
    }
}
