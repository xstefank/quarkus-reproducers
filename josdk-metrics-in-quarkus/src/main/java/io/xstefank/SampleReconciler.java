package io.xstefank;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

@ControllerConfiguration
public class SampleReconciler implements Reconciler<SampleCR> {

    @Override
    public UpdateControl<SampleCR> reconcile(SampleCR resource, Context<SampleCR> context) {
        return UpdateControl.noUpdate();
    }
}
