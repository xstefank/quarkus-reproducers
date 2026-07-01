package io.xstefank;

import org.jboss.logging.Logger;

import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

@ControllerConfiguration
public class SampleReconciler implements Reconciler<SampleCR> {

    private static final Logger log = Logger.getLogger(SampleReconciler.class);

    @Override
    public UpdateControl<SampleCR> reconcile(SampleCR resource, Context<SampleCR> context) {
        log.infof("Reconciling SampleCR '%s/%s'", resource.getMetadata().getNamespace(),
                resource.getMetadata().getName());

        var status = resource.getStatus();
        if (status == null) {
            status = new SampleCRStatus();
        }
        status.setReconciledCount(status.getReconciledCount() + 1);
        resource.setStatus(status);

        log.infof("Reconciliation count for '%s': %d", resource.getMetadata().getName(),
                status.getReconciledCount());

        return UpdateControl.patchStatus(resource);
    }
}
