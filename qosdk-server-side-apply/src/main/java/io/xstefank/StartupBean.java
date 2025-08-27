package io.xstefank;

import io.javaoperatorsdk.operator.Operator;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class StartupBean {

    public void onStart(@Observes StartupEvent event) {
        Log.error("StartupBean.onStart");

        Operator operator = new Operator(configurationServiceOverrider ->
            configurationServiceOverrider.withUseSSAToPatchPrimaryResource(true));
        operator.register(new TestReconciler());
        operator.start();
    }
}
