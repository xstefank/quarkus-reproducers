package com.example.mongo;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.inject.Singleton;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

@Singleton
public class TestEntityCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz.isAnnotationPresent(MongoEntity.class)) {
            return (Codec<T>) new EntityCodec(clazz);
        }
        return null;
    }
}
