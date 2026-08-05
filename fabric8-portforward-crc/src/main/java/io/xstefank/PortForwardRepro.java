package io.xstefank;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.LocalPortForward;
import io.fabric8.kubernetes.client.http.HttpRequest;
import io.fabric8.kubernetes.client.http.HttpResponse;
import io.fabric8.openshift.client.OpenShiftClient;

/**
 * Standalone reproducer for a fabric8 LocalPortForward issue observed against CRC (OpenShift Local).
 * <p>
 * Deploys nginx via a Deployment, waits for it to be ready, finds its pod via the Deployment's own label
 * selector, opens a LocalPortForward to that pod, then makes one plain HTTP request through it - mirroring
 * exactly what {@code DefaultKubernetesContainerLauncher} in the actual Quarkus {@code @QuarkusIntegrationTest}
 * Kubernetes/OpenShift launch mode does (applyAndWaitUntilReady()/findFirstPod()/exposeViaPortForward()).
 * <p>
 * Earlier versions of this reproducer (bare Pod, Deployment with a plain KubernetesClient, Deployment with a
 * delayed-start container) all worked fine against CRC. This version adapts the client to
 * {@code OpenShiftClient} (via {@code client.adapt(OpenShiftClient.class)}) - the one thing the real launcher
 * does differently for the openshift target that hadn't been tested yet - to check whether the adaptation
 * itself is what triggers the hang.
 */
public class PortForwardRepro {

    private static final String APP_NAME = "fabric8-portforward-repro";
    private static final int CONTAINER_PORT = 8080;
    private static final Map<String, String> SELECTOR_LABELS = Map.of("app", APP_NAME);

    public static void main(String[] args) throws Exception {
        Config config = Config.autoConfigure(null);
        if (config.getNamespace() == null || config.getNamespace().isBlank()) {
            throw new IllegalStateException(
                    "No namespace/project is set on the current context. Run 'oc project <your-project>' "
                            + "(or 'kubectl config set-context --current --namespace=<your-namespace>') first - "
                            + "'default' is a restricted namespace regular users typically can't create/delete "
                            + "resources in.");
        }

        KubernetesClient base = new KubernetesClientBuilder().withConfig(config).build();
        try (OpenShiftClient client = base.adapt(OpenShiftClient.class)) {
            System.out.println("Master URL: " + client.getMasterUrl());
            System.out.println("Namespace:  " + client.getNamespace());

            Deployment deployment = new DeploymentBuilder()
                    .withNewMetadata()
                    .withName(APP_NAME)
                    .withNamespace(client.getNamespace())
                    .endMetadata()
                    .withNewSpec()
                    .withReplicas(1)
                    .withNewSelector().withMatchLabels(SELECTOR_LABELS).endSelector()
                    .withNewTemplate()
                    .withNewMetadata().withLabels(SELECTOR_LABELS).endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("nginx")
                    .withImage("nginxinc/nginx-unprivileged:alpine")
                    .addNewPort().withContainerPort(CONTAINER_PORT).endPort()
                    .endContainer()
                    .endSpec()
                    .endTemplate()
                    .endSpec()
                    .build();

            try {
                System.out.println("Creating Deployment " + APP_NAME + "...");
                client.apps().deployments().resource(deployment).createOrReplace();

                System.out.println("Waiting for Deployment to be ready...");
                client.apps().deployments().withName(APP_NAME).waitUntilReady(2, TimeUnit.MINUTES);

                System.out.println("Finding pod via label selector " + SELECTOR_LABELS + "...");
                List<Pod> pods = client.pods().inNamespace(client.getNamespace()).withLabels(SELECTOR_LABELS)
                        .list().getItems();
                if (pods.isEmpty()) {
                    throw new IllegalStateException("No pods found for label selector " + SELECTOR_LABELS);
                }
                Pod pod = pods.get(0);
                System.out.println("Found pod: " + pod.getMetadata().getName());

                System.out.println("Establishing port-forward to container port " + CONTAINER_PORT + "...");
                try (LocalPortForward portForward = client.pods().withName(pod.getMetadata().getName())
                        .portForward(CONTAINER_PORT)) {
                    int localPort = portForward.getLocalPort();
                    System.out.println("Port-forward established: localhost:" + localPort);

                    URI uri = URI.create("http://localhost:" + localPort + "/");
                    HttpRequest request = client.getHttpClient().newHttpRequestBuilder().uri(uri)
                            .timeout(15, TimeUnit.SECONDS).build();

                    long deadline = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2);
                    while (true) {
                        System.out.println("Sending HTTP GET " + uri + " through the port-forward...");
                        try {
                            HttpResponse<byte[]> response = client.getHttpClient().sendAsync(request, byte[].class)
                                    .get(15, TimeUnit.SECONDS);
                            System.out.println("SUCCESS: got HTTP " + response.code());
                            break;
                        } catch (Exception e) {
                            System.out.println("Attempt failed (" + e + "), retrying...");
                            if (System.currentTimeMillis() > deadline) {
                                throw new IllegalStateException("Gave up after 2 minutes of retrying", e);
                            }
                        }
                    }
                }
            } finally {
                // Caught separately so a cleanup failure never masks whatever exception the try block threw.
                try {
                    System.out.println("Cleaning up Deployment " + APP_NAME + "...");
                    client.apps().deployments().withName(APP_NAME).delete();
                } catch (RuntimeException e) {
                    System.err.println("Failed to clean up Deployment " + APP_NAME + ": " + e.getMessage());
                }
            }
        }
    }
}
