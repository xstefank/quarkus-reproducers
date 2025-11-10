package io.xstefank;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.DependentResource;
import io.javaoperatorsdk.operator.processing.dependent.workflow.Condition;
import io.quarkus.arc.Unremovable;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Unremovable
public class DeletePostCondition implements Condition {

    @Inject
    TestUUIDBean testUUIDBean;

    private String uuid;

    @Override
    public boolean isMet(DependentResource dependentResource, HasMetadata primary, Context context) {
        Log.error("XXXXXXXXXXX DeletePostCondition isMet");
        this.uuid = testUUIDBean.uuid();
        return true;
    }

    public String getUuid() {
        return uuid;
    }
}
