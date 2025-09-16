package io.xstefank;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import io.quarkus.logging.Log;

@Workflow(dependents = @Dependent(type = DeploymentDependent.class, activationCondition =  CustomActionvationCondition.class))
public class TestReconciler implements Reconciler<TestResource> {
    @Override
    public UpdateControl<TestResource> reconcile(TestResource resource, Context<TestResource> context) throws Exception {
        Log.error("XXXXXXXXXXX " + resource.getMetadata().getName() + " " + resource.getSpec().field);

        return UpdateControl.noUpdate();
    }
}
