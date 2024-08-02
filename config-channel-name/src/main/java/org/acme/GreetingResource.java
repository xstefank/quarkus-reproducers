package org.acme;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

@Path("/hello")
public class GreetingResource {

    @Channel("test-channel")
    Emitter<String> emitter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        emitter.send("Hello World!");
        return "Hello from Quarkus REST";
    }

    @Incoming("test-channel")
    public CompletionStage<Void> consume(Message<String> message) {
        System.out.println("Received: " + message.getPayload());
        return message.ack();
    }

    @Outgoing("ticks")
    public Multi<Payload> aFewTicks() {
        return Multi.createFrom()
            .ticks().every(Duration.ofSeconds(1))
            .map(aLong -> new Payload("value " + aLong, aLong));
    }

//    @Incoming("ticks")
//    @Outgoing("times")
//    public Multi<String> processor(Multi<Long> ticks) {
//        return ticks.map(tick -> Instant.now().toString());
//    }
//
//    @Incoming("times")
//    public void consumer(String payload) {
//        System.out.println(payload);
//    }

    @Inject
    @Channel("ticks")
    Multi<Payload> ticks;

    @GET
    @Path("/consume")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Payload> sseTicks() {
        return ticks;
    }

    public record Payload(String value1, long value2) {
    }
}
