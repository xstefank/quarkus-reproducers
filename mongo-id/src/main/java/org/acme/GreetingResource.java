package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.types.ObjectId;

@Path("/hello")
public class GreetingResource {

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello() {
//        TestEntityString testEntity = new TestEntityString(new ObjectId(), "Hello World");
//        testEntity.persist();
//
//        System.out.println(TestEntityString.listAll());
//        return "Hello from Quarkus REST";
//    }

    @GET
    @Path("/list")
    public void list() {
        System.out.println(TestEntityString.listAll());
    }

    @GET
    @Path("/delete")
    public void delete() {
        TestEntityString.deleteAll();
    }


    @GET
    @Path("/find/{id}")
    public TestEntityString find(String id) {
        return TestEntityString.findById(new ObjectId(id));
    }

    @GET
    @Path("/find2/{id}")
    public TestEntityString find2(String id) {
        return TestEntityString.find("_id", id).firstResult();
    }

    @GET
    @Path("/find3/{id}")
    public TestEntityBase find3(String id) {
        TestEntityBase testEntity;

        if (ObjectId.isValid(id)) {
            testEntity = TestEntityObjectId.findById(new ObjectId(id));
        } else {
            testEntity = TestEntityString.find("_id", id).firstResult();
        }

        testEntity.set_id(id);
        return testEntity;
    }



}
