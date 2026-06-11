package io.quarkiverse.operatorsdk.reproducer;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

@ControllerConfiguration
public class TestReconciler implements Reconciler<TestResource> {

    @Override
    public UpdateControl<TestResource> reconcile(TestResource resource, Context<TestResource> context) {
        return UpdateControl.noUpdate();
    }
}
