package org.acme;


import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

@GrpcService
public class GrpcTestService implements Streaming {


    @Override
    public Multi<Item> pipe(Multi<Item> request) {
        return request.map(item ->
            Item.newBuilder().setValue(item.getValue().toUpperCase()).build());
    }
}
