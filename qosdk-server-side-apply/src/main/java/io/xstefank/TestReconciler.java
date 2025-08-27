package io.xstefank;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.concurrent.atomic.AtomicInteger;

@Workflow(dependents = {
    @Dependent(type = DeploymentDependent.class),
})
public class TestReconciler implements Reconciler<TestResource> {
    private static final AtomicInteger counter = new AtomicInteger();

    @Override
    public UpdateControl<TestResource> reconcile(TestResource testResource, Context<TestResource> context) throws Exception {
        Log.error(counter.get() + " -------------------- Reconciler invoked for " + testResource);
        Log.error(testResource.getSpec().field);
        Log.error("Config works? " + ConfigProvider.getConfig().getOptionalValue("quarkus.operator-sdk.enable-ssa", String.class).orElse("N/A"));


        testResource.setStatus(new TestStatus("value-" + counter.incrementAndGet()));
        if (!testResource.getSpec().field.endsWith("-updated")) {
            TestSpec testSpec = new TestSpec();
            testSpec.field = testResource.getSpec().field + "-updated";
            testResource.setSpec(testSpec);

            testResource.getMetadata().setManagedFields(null);

            Log.error("Returning update spec !!!!!!!!!!!!!!!");
            return UpdateControl.patchResourceAndStatus(testResource);
        }
        Log.error("Returning update status !!!!!!!!!!!!!!!");
        return UpdateControl.patchStatus(testResource);
    }
}
