package com.example.mongo;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection = "resource2")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity2 {

    @BsonId
    public String _id; // We need String as ObjectId would not be parsed from non hex strings

    public String surname;
    public int age;
}
