package org.acme;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.EventSourceContext;
import io.javaoperatorsdk.operator.api.reconciler.ResourceDiscriminator;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependentResource;

import java.util.Optional;

@KubernetesDependent
public class MyDependentResource extends KubernetesDependentResource<Service, Deployment> implements ResourceDiscriminator<Service, Deployment> {
    EventSourceContext<Deployment> eventSourceContext;

    public MyDependentResource() {
        super(Service.class);
    }

    @Override
    public Optional<Service> distinguish(Class<Service> resource, Deployment primary, Context<Deployment> context) {
        return null;
    }
}
