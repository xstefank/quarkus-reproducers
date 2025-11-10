package io.xstefank;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Deleter;
import io.javaoperatorsdk.operator.processing.dependent.Creator;
import io.javaoperatorsdk.operator.processing.dependent.Updater;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.event.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class ConfigMapDependent extends CRUDKubernetesDependentResource<ConfigMap, TestResource> {

    private static final Logger log = LoggerFactory.getLogger(ConfigMapDependent.class);


    @Override
    protected ConfigMap desired(TestResource primary, Context<TestResource> context) {
        ConfigMap configMap = new ConfigMap();
        configMap.setMetadata(
                new ObjectMetaBuilder()
                        .withName(primary.getMetadata().getName() + "-cm")
                        .withNamespace(primary.getMetadata().getNamespace())
                        .build());
        configMap.setData(Map.of("key", "data"));
        return configMap;
    }

    @Override
    public void delete(TestResource primary, Context<TestResource> context) {
        Optional<ConfigMap> optionalConfigMap = context.getSecondaryResource(ConfigMap.class);
        if (optionalConfigMap.isEmpty()) {
            log.debug("Config Map not found for primary: {}", ResourceID.fromResource(primary));
            return;
        }
        optionalConfigMap.ifPresent(
                (configMap -> {
                    if (configMap.getMetadata().getAnnotations() != null) {
                        context.getClient().resource(configMap).delete();
                    }
                }));

    }
}
