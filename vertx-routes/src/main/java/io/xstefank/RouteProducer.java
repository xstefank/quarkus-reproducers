package io.xstefank;

import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class RouteProducer {

    public void init(@Observes Router router) {
        router.get("/my-route" + "/*").handler(rc -> rc.response().end("Hello from my route"));
    }
}
