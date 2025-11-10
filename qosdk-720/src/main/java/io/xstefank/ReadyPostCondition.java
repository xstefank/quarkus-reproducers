package io.xstefank;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.DependentResource;
import io.javaoperatorsdk.operator.processing.dependent.workflow.Condition;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Unremovable
public class ReadyPostCondition implements Condition<Deployment, TestResource> {

    @Inject
    TestUUIDBean testUUIDBean;

    private String uuid;

    @Override
    public boolean isMet(DependentResource<Deployment, TestResource> dependentResource, TestResource primary, Context<TestResource> context) {
//        uuid = testUUIDBean.uuid();
//        return true;
        return dependentResource
            .getSecondaryResource(primary, context)
            .map(
                deployment -> {
                    var readyReplicas = deployment.getStatus().getReadyReplicas();
                    return readyReplicas != null
                        && deployment.getSpec().getReplicas().equals(readyReplicas);
                })
            .orElse(false);

    }

    public String getUuid() {
        return uuid;
    }
}
