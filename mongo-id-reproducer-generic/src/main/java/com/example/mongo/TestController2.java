package com.example.mongo;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/resource/mongo2")
@Slf4j
public class TestController2 {

    @Inject
    TestRepository2 testRepository;

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") String id) {
        TestEntity2 testEntity;
        if(ObjectId.isValid(id)){
            testEntity=testRepository.findById(new ObjectId(id));
        }else {
            testEntity=testRepository.find("_id", id).firstResult();
        }

        System.out.println("Test entity: " + testEntity);
        return Response.ok(testEntity).build();
    }

    @GET
    public List<TestEntity2> all() {
        return testRepository.listAll();
    }


    @GET
    @Path("/create")
    public TestEntity2 create() {
        TestEntity2 testEntity= TestEntity2.builder().surname("Test").age(42).build();
        testRepository.persist(testEntity);
        return testEntity;
    }


}
