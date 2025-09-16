package io.xstefank;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.DependentResource;
import io.javaoperatorsdk.operator.processing.dependent.workflow.Condition;
import io.quarkus.arc.Arc;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.ConfigProvider;

public class CustomActionvationCondition implements Condition {

    @Inject
    MyCustomBean myCustomBean;

    @Override
    public boolean isMet(DependentResource dependentResource, HasMetadata primary, Context context) {
        Log.error("aASDFASDFASDF CustomActivationCondition" + primary.getMetadata().getName());
        Log.error("MyCustomBean says: " + Arc.container().instance(MyCustomBean.class).get().ping());
        Log.error("Test config = " + ConfigProvider.getConfig().getValue("test.config", String.class));

        return true;
    }
}
