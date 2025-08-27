package io.xstefank;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("io.xstefank")
@Version("v1")
@ShortNames("tr")
public class TestResource extends CustomResource<TestSpec, TestStatus> implements Namespaced {
}
