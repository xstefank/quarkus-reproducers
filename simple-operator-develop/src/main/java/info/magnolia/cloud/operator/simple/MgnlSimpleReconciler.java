/**
 * This file Copyright (c) 2023-2025 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This program and the accompanying materials are made
 * available under the terms of the Magnolia Network Agreement
 * which accompanies this distribution, and is available at
 * http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.cloud.operator.simple;

import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import io.quarkus.logging.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.javaoperatorsdk.operator.api.reconciler.Constants.WATCH_ALL_NAMESPACES;

/**
 * {@link Reconciler} for the customer {@link MgnlSimple}.
 */
//@Workflow(dependents = {
//    @Dependent(type = DeploymentDependentResource.class, name = "deployment")
//})
@ControllerConfiguration(
    informer = @Informer(namespaces = WATCH_ALL_NAMESPACES)
)
@RequiredArgsConstructor
@Slf4j
public class MgnlSimpleReconciler implements Reconciler<MgnlSimple>, Cleaner<MgnlSimple> {
    private static final String CENTRAL_REALM_CONFIGURED = "CentralRealmConfigured";
    private static final String CUSTOMER_REALM_CONFIGURED = "CustomerRealmConfigured";


    @Override
    public UpdateControl<MgnlSimple> reconcile(MgnlSimple mgnlRealm, Context<MgnlSimple> context) {
        Log.info("Reconciling the Magnolia simple " + mgnlRealm.getMetadata().getName());
        return UpdateControl.noUpdate();
    }

    @Override
    public DeleteControl cleanup(MgnlSimple mgnlRealm, Context<MgnlSimple> context) {
        Log.info("Deleting the Magnolia simple " + mgnlRealm.getMetadata().getName());
        return DeleteControl.defaultDelete();
    }
}
