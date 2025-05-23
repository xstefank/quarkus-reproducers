package com.example.mongo;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {
    @BsonId
    public String id; // We need String as ObjectId would not be parsed from non hex strings
    public String name;
}
