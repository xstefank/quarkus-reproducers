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
public class ReadyPostCondition implements Condition {

    @Inject
    MyCustomBean myCustomBean;

    @Override
    public boolean isMet(DependentResource dependentResource, HasMetadata primary, Context context) {
        Log.error("ReadyPostCondition " + myCustomBean.ping());
        return true;
    }
}
