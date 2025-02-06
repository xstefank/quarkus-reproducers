package info.magnolia.cloud.operator.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MgnlSimpleSpec {

    private String name;
}
