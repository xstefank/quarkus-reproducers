package com.example.mongo;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection = "resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@BsonDiscriminator(key = "_class", value = "com.example.mongo.TestEntity")
public class TestEntity {

    @BsonId
    @BsonProperty("_id")
    private String id; // We need String as ObjectId would not be parsed from non hex strings

    private String name;
}
