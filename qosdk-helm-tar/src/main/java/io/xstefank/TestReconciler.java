package io.xstefank;


import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

public class TestReconciler implements Reconciler<TestResource> {

    @Override
    public UpdateControl<TestResource> reconcile(TestResource resource, Context context) {
        return null;
    }
}
