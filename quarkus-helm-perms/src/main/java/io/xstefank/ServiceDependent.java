package io.xstefank;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;

import java.util.Map;

import static io.xstefank.ExposedAppReconciler.LABELS_CONTEXT_KEY;
import static io.xstefank.ExposedAppReconciler.createMetadata;


public class ServiceDependent extends CRUDKubernetesDependentResource<Service, ExposedApp> {

    // todo: automatically generate
    public ServiceDependent() {
        super(Service.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Service desired(ExposedApp exposedApp, Context context) {
        final var labels = (Map<String, String>) context.managedWorkflowAndDependentResourceContext()
                .getMandatory(LABELS_CONTEXT_KEY, Map.class);

        return new ServiceBuilder()
                .withMetadata(createMetadata(exposedApp, labels))
                .withNewSpec()
                .addNewPort()
                .withName("http")
                .withPort(8080)
                .withNewTargetPort().withValue(8080).endTargetPort()
                .endPort()
                .withSelector(labels)
                .withType("ClusterIP")
                .endSpec()
                .build();
    }
}
