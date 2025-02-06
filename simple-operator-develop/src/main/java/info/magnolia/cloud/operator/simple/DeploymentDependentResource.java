package info.magnolia.cloud.operator.simple;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

import java.util.HashMap;
import java.util.Map;

// this annotation only activates when using managed dependents and is not otherwise needed
@KubernetesDependent
public class DeploymentDependentResource extends CRUDKubernetesDependentResource<Deployment, MgnlSimple> {

    public DeploymentDependentResource() {
        super(Deployment.class);
    }

    @Override
    protected Deployment desired(MgnlSimple mgnlSimple, Context<MgnlSimple> context) {
        Map<String, String> labels = new HashMap<>();
        var deploymentName = "test-deployment";
        Deployment deployment = new DeploymentBuilder()
            .withNewMetadata().withName(deploymentName).endMetadata()
            .withNewSpec()
            .withNewSelector().withMatchLabels(labels).endSelector()
            .withNewTemplate()
            .withNewMetadata()
            .endMetadata()
            .withNewSpec()
            .withContainers(new ContainerBuilder()
                .withName("where-am-i")
                .withImage("quay.io/xstefank/where-am-i").build())
            .endSpec()
            .endTemplate()
            .endSpec()
            .build();
        deployment.getMetadata().setName(deploymentName);
        deployment.getMetadata().setNamespace(mgnlSimple.getMetadata().getNamespace());
        deployment.getMetadata().setLabels(labels);
        deployment.getSpec().getSelector().getMatchLabels().put("app", deploymentName);

        deployment
            .getSpec()
            .getTemplate()
            .getMetadata()
            .getLabels()
            .put("app", deploymentName);

        return deployment;
    }
}
