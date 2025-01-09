package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(database = "test-db")
public class TestEntity extends PanacheMongoEntity {

    private ObjectId _id;

    public TestEntity() {
    }

    public TestEntity(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

}
