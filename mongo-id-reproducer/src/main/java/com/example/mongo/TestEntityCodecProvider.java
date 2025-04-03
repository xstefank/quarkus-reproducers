package com.example.mongo;

import jakarta.inject.Singleton;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

@Singleton
public class TestEntityCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry codecRegistry) {
        if (clazz.equals(TestEntity.class)) {
            return (Codec<T>) new TestEntityCodec();
        }
        return null;
    }
}
