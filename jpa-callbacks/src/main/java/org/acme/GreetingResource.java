package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String hello() {
        TestEntity te = new TestEntity();
        te.setName("test");
        em.persist(te);
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/{id}")
    @Transactional
    public void update(Long id) {
        TestEntity testEntity = em.find(TestEntity.class, id);
        testEntity.setName("updated");
        em.merge(testEntity);
    }
}
