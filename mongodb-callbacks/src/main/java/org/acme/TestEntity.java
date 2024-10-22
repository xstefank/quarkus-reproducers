package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(database = "test")
public class TestEntity extends PanacheMongoEntity {

    public String name;
}
