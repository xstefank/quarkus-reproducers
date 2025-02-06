package info.magnolia.cloud.operator.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("magnolia.info")
@Version("v1alpha1")
@Kind("MgnlSimple")
@Plural("mgnlsimples")
@ShortNames("mgnlsimple")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MgnlSimple extends CustomResource<MgnlSimpleSpec, MgnlSimpleStatus> implements Namespaced  {
}
