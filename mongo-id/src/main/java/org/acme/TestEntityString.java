package org.acme;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(database = "test-db", collection = "TestEntity")
public class TestEntityString extends TestEntityBase {

    @BsonId
    public String _id;

    @Override
    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
            "_id='" + _id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
