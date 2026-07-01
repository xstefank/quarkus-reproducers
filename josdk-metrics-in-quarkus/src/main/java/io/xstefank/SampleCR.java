package io.xstefank;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("demo.xstefank.io")
@Version("v1")
public class SampleCR extends CustomResource<Void, SampleCRStatus> implements Namespaced {
}
