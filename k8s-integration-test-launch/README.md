# k8s-integration-test-launch

Reproducer for the new `@QuarkusIntegrationTest` Kubernetes/OpenShift cluster launch mode added to
`~/git/xstefank/quarkus` (test-framework/common, test-framework/junit). Runs the exact same test
(`GreetingResourceIT` hitting `GET /hello`) against all four `@QuarkusIntegrationTest` launch modes:
jar, native, local Docker container, and a real Kubernetes cluster.

## Prerequisites

This project depends on `io.quarkus:*:999-SNAPSHOT`, i.e. whatever is currently checked out and
built in `~/git/xstefank/quarkus`. Build (at least) the two modules this reproducer exercises before
running anything here:

```shell script
cd ~/git/xstefank/quarkus
./mvnw install -f test-framework/common/ -f test-framework/junit/ -DskipTests
```

(If `io.quarkus:quarkus-bom:999-SNAPSHOT` / `quarkus-maven-plugin:999-SNAPSHOT` aren't in your local
`~/.m2` yet, you'll need a full `./mvnw install -Dquickly` of that repo first.)

## Running each mode

```shell script
# jar
./mvnw verify -Djar

# native (needs GraalVM, or add -Dquarkus.native.container-build=true to build in a container)
./mvnw verify -Dnative

# local Docker/Podman container
./mvnw verify -Ddocker

# real Kubernetes cluster - needs a reachable cluster (current kubeconfig context) and a registry
# reachable from it. Quickest way to get both locally:
minikube start
minikube addons enable registry
kubectl port-forward --namespace kube-system service/registry 5000:80 &
./mvnw verify -Dk8s
```

The `k8s` mode's registry defaults to `localhost:5000` in `src/main/resources/application.properties`
(`quarkus.test.kubernetes.registry`), matching the minikube recipe above. If your registry is
somewhere else, either edit that file or override it for a single run with
`-Dquarkus.test.kubernetes.registry=<other-registry>`.

Or run all four back-to-back:

```shell script
./run-all.sh
```

(`run-all.sh` only attempts the `k8s` mode if `RUN_K8S=1` is set in the environment, since it needs a
cluster to already be up and reachable.)

## What to look for in `k8s` mode

- `Retagging '...' as '...' and pushing it to the test registry` then a successful `docker push`
- `Applied: Deployment greeting ...` / `Applied: Service greeting ...`
- `Waiting for Deployment greeting ... to be ready...`
- `Port-forwarding default/greeting-... :8080 to localhost:<port>`
- The test passing against that port
- On teardown: the resources being deleted (or, with
  `-Dquarkus.test.kubernetes.delete-after-test=false` added to the failsafe systemPropertyVariables,
  left running - check with `kubectl get all`)
