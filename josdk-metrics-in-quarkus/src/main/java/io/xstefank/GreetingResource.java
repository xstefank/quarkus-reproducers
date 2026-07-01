package io.xstefank;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;

@Path("/trigger")
public class GreetingResource {

    @Inject
    KubernetesClient client;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createSampleCR() {
        SampleCR cr = new SampleCR();
        cr.setMetadata(new ObjectMetaBuilder()
                .withName("test-cr")
                .withNamespace("default")
                .build());
        client.resource(cr).create();
        return "created";
    }
}
