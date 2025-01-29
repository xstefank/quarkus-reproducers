package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(database = "Test")
public class DummyEntity extends PanacheMongoEntity {

    public String test;
}
