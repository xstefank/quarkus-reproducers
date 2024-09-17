package org.acme;


import io.quarkus.grpc.GrpcService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

@GrpcService
public class GrpcTestService implements Streaming {


    @Override
    public Multi<Item> bidiStreaming(Multi<Item> request) {
        return request.map(item ->
            Item.newBuilder().setValue(item.getValue().toUpperCase()).build());
    }

    @Override
    public Uni<Item> unary(Item request) {
        return Uni.createFrom().item(() -> Item.newBuilder().setValue(request.getValue().toUpperCase()).build());
    }

    @Override
    public Multi<Item> serverStreaming(Item request) {
        Log.error("serverStreaming");
        return Multi.createFrom().items(
            Item.newBuilder().setValue(request.getValue().toUpperCase()).build(),
            Item.newBuilder().setValue(request.getValue().toUpperCase()).build(),
            Item.newBuilder().setValue(request.getValue().toUpperCase()).build()
        );
    }

    @Override
    public Uni<Item> clientStreaming(Multi<Item> request) {
        Log.error("clientStreaming");
        return request.map(item -> item.getValue().toUpperCase()).collect().asList().onItem().transformToUni(l -> Uni.createFrom().item(() -> Item.newBuilder().setValue(String.join(" - ", l)).build()));
    }
}
