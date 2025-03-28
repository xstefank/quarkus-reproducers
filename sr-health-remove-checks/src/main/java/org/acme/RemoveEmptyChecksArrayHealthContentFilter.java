package org.acme;

import io.smallrye.health.api.HealthContentFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@ApplicationScoped
public class RemoveEmptyChecksArrayHealthContentFilter implements HealthContentFilter {
    @Override
    public JsonObject filter(JsonObject jsonObject) {
        if (jsonObject.getJsonArray("checks").isEmpty()) {
            return Json.createObjectBuilder().add("status", jsonObject.getString("status")).build();
        }

        return jsonObject;
    }
}
