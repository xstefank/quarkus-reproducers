package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonPointer;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.StringReader;
import java.util.Iterator;

@Path("/hello")
public class GreetingResource {

    @Inject
    ObjectMapper mapper;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws JsonProcessingException {
//        ObjectNode payload = (ObjectNode) mapper.readTree("{\"status\":\"UP\",\"checks\":[{\"name\":\"io.smallrye.health.deployment.SuccessLiveness\",\"status\":\"UP\",\"data\":{\"foo\":\"bar\"}}],\"foo\":\"bar\"}");
//        payload.put("redacted", "true");
//        JsonNode checks = payload.get("checks");
//
//        for (final Iterator<JsonNode> i = checks.elements(); i.hasNext(); ) {
//            final ObjectNode check = (ObjectNode) i.next();
//            check.remove("data");
//        }
//

        JsonReader reader = Json.createReader(new StringReader("{\"status\":\"UP\",\"checks\":[{\"name\":\"io.smallrye.health.deployment.SuccessLiveness\",\"status\":\"UP\",\"data\":{\"foo\":\"bar\"}},{\"name\":\"io.smallrye.health.deployment" +
            ".SuccessLiveness2\",\"status\":\"UP\",\"data\":{\"foo\":\"bar\"}}]}"));
        JsonObject payload = reader.readObject();


//        JsonArray checks = Json.createPatchBuilder()
//            .remove("/0/data").build().apply(payload.getJsonArray("checks"));

        JsonPointer dataPointer = Json.createPointer("/data");

        JsonArrayBuilder checkBuilder = Json.createArrayBuilder();

        for (Iterator<JsonValue> iterator = payload.getJsonArray("checks").iterator(); iterator.hasNext(); ) {
            JsonObject jsonObject = iterator.next().asJsonObject();
            JsonObject remove = dataPointer.remove(jsonObject);
            checkBuilder.add(remove);
        }

        payload = Json.createObjectBuilder(payload)
            .add("redacted", "true")
            .add("checks", checkBuilder.build())
            .build();


        System.out.println(payload);
        return "Hello from Quarkus REST";
    }
}
