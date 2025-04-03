package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.types.ObjectId;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        TestEntity testEntity = new TestEntity(new ObjectId("507f1f77bcf86cd799439011"));
        testEntity.persist();

        System.out.println(TestEntity.listAll());
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/list")
    public void list() {
        System.out.println(TestEntity.listAll());
    }

    @GET
    @Path("/delete")
    public void delete() {
        TestEntity.deleteAll();
    }
}
