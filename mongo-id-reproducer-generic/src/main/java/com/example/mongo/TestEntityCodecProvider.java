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

        if (clazz.equals(TestEntity2.class)) {
            return (Codec<T>) new TestEntity2Codec();
        }
        return null;
    }

    public static class TestEntityCodec extends EntityCodec<TestEntity> {

        @Override
        public Class<TestEntity> getEncoderClass() {
            return TestEntity.class;
        }
    }

    public static class TestEntity2Codec extends EntityCodec<TestEntity2> {

        @Override
        public Class<TestEntity2> getEncoderClass() {
            return TestEntity2.class;
        }
    }
}
