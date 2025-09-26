package io.xstefank;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Deleter;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import io.quarkus.logging.Log;

@Workflow(dependents = @Dependent(type = DeploymentDependent.class,
    activationCondition =  CustomActionvationCondition.class,
    readyPostcondition = ReadyPostCondition.class,
    deletePostcondition = DeletePostCondition.class,
    reconcilePrecondition = ReconcilePrecondition.class),explicitInvocation = true)
public class TestReconciler implements Reconciler<TestResource>, Deleter<TestResource> {
    @Override
    public UpdateControl<TestResource> reconcile(TestResource resource, Context<TestResource> context) throws Exception {
        Log.error("XXXXXXXXXXX reconcile " + resource.getMetadata().getName() + " " + resource.getSpec().field);

        context.managedWorkflowAndDependentResourceContext().reconcileManagedWorkflow();

        return UpdateControl.noUpdate();
    }

    @Override
    public void delete(TestResource primary, Context<TestResource> context) {
        Log.error("XXXXXXXXXXX delete " + primary.getMetadata().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
