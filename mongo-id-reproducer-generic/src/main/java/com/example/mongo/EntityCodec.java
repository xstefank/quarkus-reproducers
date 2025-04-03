package com.example.mongo;

import com.mongodb.MongoClientSettings;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Singleton
public abstract class EntityCodec<V extends Object> implements CollectibleCodec<V> {

    private final Codec<Document> documentCodec;

    public EntityCodec() {
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public void encode(BsonWriter bsonWriter, V entity, EncoderContext encoderContext) {
        Document document = new Document();
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                document.put(field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        documentCodec.encode(bsonWriter, document, encoderContext);
    }

    @Override
    public V generateIdIfAbsentFromDocument(V entity) {
        Field id = getIdField();
        try {
            if (id.get(entity) == null) {
                try {
                    id.set(entity, new ObjectId().toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public boolean documentHasId(V entity) {
        Field id = getIdField();
        try {
            return id.get(entity) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BsonValue getDocumentId(V entity) {
        Field id = getIdField();
        try {
            return new BsonString(id.get(entity).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public V decode(BsonReader bsonReader, DecoderContext decoderContext) {
        try {
            Document doc = documentCodec.decode(bsonReader, decoderContext);
            Class<V> clazz = getEncoderClass();
            Constructor<V> noArgContructor = clazz.getDeclaredConstructor();
            noArgContructor.setAccessible(true);
            V entity = noArgContructor.newInstance();

            Field id = getIdField();

            Object docId = doc.get("_id");
            if (ObjectId.class.isAssignableFrom(docId.getClass())) {
                id.set(entity, ((ObjectId) docId).toString());
            } else if (String.class.isAssignableFrom(docId.getClass())) {
                id.set(entity, (String) docId);
            } else {
                Log.error("Unexpected id type in the document " + id);
            }

            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    field.set(entity, doc.get(field.getName()));
                }
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Field getIdField() {
        try {
            Field id = getEncoderClass().getDeclaredField("id");
            id.setAccessible(true);
            return id;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
