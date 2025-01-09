package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MongoEntity(database = "test-db", collection = "TestEntity")
public class TestEntity2 extends PanacheMongoEntityBase {

    @BsonId
    @BsonProperty("_id")
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    Doesn't work
//    public void setId(ObjectId id) {
//        this.id = id.toString();
//    }
}
