package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        TestEntity testEntity = new TestEntity();
        testEntity.name = UUID.randomUUID().toString();
        testEntity.persist();
        return "Hello from Quarkus REST";
    }
    
    @GET
    @Path("/all")
    public List<TestEntity> all() {
        return TestEntity.listAll();
    }

    @GET
    @Path("/delete")
    public void delete() {
        TestEntity.deleteAll();
    }


    
    
}
