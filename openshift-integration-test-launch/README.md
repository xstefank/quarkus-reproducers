# openshift-integration-test-launch

Reproducer for the new `@QuarkusIntegrationTest` OpenShift cluster launch mode added to
`~/git/xstefank/quarkus` (test-framework/common, test-framework/junit). Runs the exact same test
(`GreetingResourceIT` hitting `GET /hello`) against all four `@QuarkusIntegrationTest` launch modes:
jar, native, local Docker container, and a real OpenShift cluster.

This is the OpenShift-only counterpart to `../k8s-integration-test-launch` (plain Kubernetes/minikube).
They're kept as separate projects/kubeconfig-context targets on purpose, to avoid mixing up which
cluster a given run is actually talking to.

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
```

### OpenShift (e.g. Red Hat Developer Sandbox)

Needs a reachable OpenShift cluster (current `oc`/`kubectl` context) and a registry reachable from
that cluster. For the Red Hat Developer Sandbox, the easiest registry to use is the cluster's own
internal one, exposed externally via a route:

```shell script
oc login --token=<token> --server=<sandbox api server>
oc registry info   # prints the registry route, e.g. default-route-openshift-image-registry.apps.<sandbox domain>

# authenticate docker/podman against that route using your oc token
docker login -u "$(oc whoami)" -p "$(oc whoami -t)" <registry route>

./mvnw verify -Dopenshift \
  -Dquarkus.test.openshift.registry=<registry route> \
  -Dquarkus.container-image.group=<your sandbox project name, e.g. myuser-dev> \
  -Dquarkus.docker.additional-args=--provenance=false
```

Notes on the flags above (all needed, not optional extras):

- **`quarkus.test.openshift.registry`**: must be reachable *from the cluster*, not just from your
  machine - `localhost:5000` (as used by the plain-k8s/minikube reproducer) will not work here.
- **`quarkus.container-image.group`**: the OpenShift-internal registry namespaces images per-project,
  so the pushed image's group must exactly match the target project/namespace name, or the push is
  rejected.
- **`quarkus.docker.additional-args=--provenance=false`**: modern Docker's default builder attaches a
  BuildKit provenance/attestation manifest even for a plain `docker build`. OpenShift's integrated
  image registry returns a `500` on push for these unless disabled.
- **`quarkus.container-image.builder=docker`** is already pinned in `application.properties` -
  `quarkus-openshift` transitively pulls in `quarkus-container-image-openshift` (the S2I builder),
  which otherwise conflicts with `quarkus-container-image-docker` since both would be present.

If the registry/repo is private, the target namespace also needs an image pull secret for the
deployment to actually be able to pull the image - this reproducer doesn't set one up automatically.

### Exposure: Route vs. port-forward

`application.properties` defaults `quarkus.test.openshift.exposure` to `route` (rather than the
`port-forward` the k8s reproducer uses), so the test hits the actual OpenShift `Route` instead of
tunnelling straight to the pod. Two things had to be true for that to work against the Red Hat
Developer Sandbox:

- `quarkus.openshift.route.expose=true` - otherwise no `Route` is generated at all, and
  `DefaultKubernetesContainerLauncher` fails with "No Route found in the generated OpenShift manifest".
- `quarkus.openshift.route.tls.termination=edge` - **required for the sandbox specifically**. Without
  it, the generated `Route` has no `spec.tls`, so the launcher (correctly, by its own logic) uses plain
  `http`. But the sandbox's router only actually serves traffic over `https` - hitting it over `http`
  gets you the router's own generic "Application is not available" `503` page, not a connection error,
  which makes it look like a backend/readiness problem rather than a protocol one. (Same reason you
  have to manually add `https://` when opening a sandbox route in a browser.) With edge termination
  set, the `Route` has `spec.tls`, and the launcher switches to `https:443` automatically.

If you want port-forward instead (e.g. to sidestep router/DNS entirely), override with
`-Dquarkus.test.kubernetes.exposure=port-forward`.

## What to look for in `openshift` mode

- `Retagging '...' as '...' and pushing it to the test registry` then a successful `docker push`
- `Deploying to openshift server: ... in namespace: ...`
- `Applied: Service ...` / `Applied: Deployment ...` / `Applied: Route ...`
- `Waiting for Deployment ... to be ready...`
- Either `Port-forwarding <namespace>/... to localhost:<port>` (port-forward mode) or the test hitting
  `https://<route host>` directly (route mode)
- The test passing
- On teardown: the resources being deleted (or, with
  `-Dquarkus.test.kubernetes.delete-after-test=false` added to the failsafe systemPropertyVariables,
  left running - check with `oc get all` or k9s)
