package org.acme;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(database = "test-db", collection = "TestEntity")
public class TestEntityString extends TestEntityBase {

    @BsonId
    private String _id;

    public TestEntityString() {
    }


    public String get_id() {
        System.out.println("get_id: " + _id);
        return _id;
    }

    public void set_id(String _id) {
        System.out.println("Received id " + _id);
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
