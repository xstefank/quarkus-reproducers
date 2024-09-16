package org.acme;


import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class GrpcTestService implements Streaming {


    @Override
    public Uni<Item> pipe1(Item request) {
        return Uni.createFrom().item(() -> Item.newBuilder().setValue(request.getValue().toUpperCase()).build());
    }

    @Override
    public Multi<Item> pipe(Multi<Item> request) {
        return request.map(item ->
            Item.newBuilder().setValue(item.getValue().toUpperCase()).build());
    }
}
