package org.acme;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(database = "test-db", collection = "TestEntity")
public class TestEntityObjectId extends TestEntityBase {

    @BsonId
    private ObjectId _id;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @Override
    public void set_id(String id) {
        this._id = new ObjectId(id);
    }

    @Override
    public String toString() {
        return "TestEntity{" +
            "_id='" + _id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
