package io.xstefank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String json = """
                {
                  "name": "Iron Man",
                  "civilName": "Tony Stark",
                    "age": 48,
                    "powers": [
                      "Genius-level intellect",
                      "Powered armor suit",
                      "Flight",
                      "Energy repulsors"
                    ],
                    "address": {
                      "street": "10880 Malibu Point",
                      "city": "Malibu",
                      "state": "CA",
                      "zip": "90265"
                    }
                }
            """;

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            Hero hero = mapper.readValue(json, Hero.class);
            Log.info("Hello " + hero.name + " a.k.a. " + hero.civilName + ", age " + hero.age + ", your powers are: " + String.join(", ", hero.powers
                + ", and your address is " + hero.address.street + ", " + hero.address.city + ", " + hero.address.state + " " + hero.address.zip));

            mapper.writeValue(System.out, hero);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Hello from Quarkus REST";
    }

    public record Hero(
        String name,
        String civilName,
        int age,
        String[] powers,
        Address address) {
    }

    public record Address(
        String street,
        String city,
        String state,
        String zip) {}
}
