package com.example.mongo;

import com.mongodb.MongoClientSettings;
import io.quarkus.logging.Log;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;


public class EntityCodec<V extends Object> implements CollectibleCodec<V> {

    private final Codec<Document> documentCodec;
    private final Class<V> entityClass;
    private final Field idField;
    private final String idFieldName;

    public EntityCodec(Class<V> entityClass) {
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
        this.entityClass= entityClass;
        this.idField = getIdField();
        this.idFieldName = getFieldName(this.idField);
    }

    @Override
    public void encode(BsonWriter bsonWriter, V entity, EncoderContext encoderContext) {
        Document document = new Document();
        Class<?> clazz = entity.getClass();
        // This is to set the discriminator key in mongo collection if the class is annotated with @BsonDiscriminator
        // this happens in Spring automatically
        if (clazz.isAnnotationPresent(BsonDiscriminator.class)) {
            BsonDiscriminator discriminator = clazz.getAnnotation(BsonDiscriminator.class);
            document.put(discriminator.key(), Objects.isNull(discriminator.value()) ? clazz.getName() : discriminator.value());
        }
        populateDocumentData(entity, document, clazz);
        documentCodec.encode(bsonWriter, document, encoderContext);
    }




    @Override
    public Class<V> getEncoderClass() {
        return entityClass;
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

    /*@Override
    public V decode(BsonReader bsonReader, DecoderContext decoderContext) {
        try {
            Document doc = documentCodec.decode(bsonReader, decoderContext);
            Class<V> clazz = getEncoderClass();
            Constructor<V> noArgContructor = clazz.getDeclaredConstructor();
            noArgContructor.setAccessible(true);
            V entity = noArgContructor.newInstance();

            Field id = getIdField();

            Object docId = doc.get(idFieldName);
            if (ObjectId.class.isAssignableFrom(docId.getClass())) {
                id.set(entity, ((ObjectId) docId).toString());
            } else if (String.class.isAssignableFrom(docId.getClass())) {
                id.set(entity, (String) docId);
            } else {
                Log.error("Unexpected id type in the document " + id);
            }

            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equals(id.getName())) {
                    field.setAccessible(true);
                    field.set(entity, doc.get(getFieldName(field)));
                }
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


    @Override
    public V decode(BsonReader bsonReader, DecoderContext decoderContext) {
        try {
            Document doc = documentCodec.decode(bsonReader, decoderContext);
            Class<V> clazz = getEncoderClass();
            // Assumes public no-arg constructor
            Constructor<V> constructor = clazz.getConstructor();
            V entity = constructor.newInstance();

            Field idField = getIdField();
            Object docId = doc.get(idFieldName);
            if (ObjectId.class.isAssignableFrom(docId.getClass())) {
                setFieldIfAccessible(entity, idField, ((ObjectId) docId).toString());
            } else if (String.class.isAssignableFrom(docId.getClass())) {
                setFieldIfAccessible(entity, idField, (String) docId);
            } else {
                Log.error("Unexpected id type in the document: " + idField);
            }

            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equals(idField.getName())) {
                    Object fieldValue = doc.get(getFieldName(field));
                    setFieldIfAccessible(entity, field, fieldValue);
                }
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void setFieldIfAccessible(Object target, Field field, Object value) {
        try {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
                field.set(target, value);
            } else {
                // Try to find a public setter method
                String setterName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                Method setter = target.getClass().getMethod(setterName, field.getType());
                setter.invoke(target, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to set field: " + field.getName(), e);
        }
    }



    private Field getIdField() {
        try {
            Field id = null;
            // try to find field with the @BsonId
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getAnnotation(BsonId.class) != null) {
                    id = field;
                    break;
                }
            }

            // if not defined, try to find "id" field
            if (id == null) {
                id = getEncoderClass().getDeclaredField("id");
            }

            id.setAccessible(true);
            return id;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFieldName(Field field) {
        BsonProperty bsonProperty = field.getAnnotation(BsonProperty.class);
        if (bsonProperty != null) {
            return bsonProperty.value();
        }
        return field.getName();
    }

    private void populateDocumentData(V entity, Document document, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if(field.getName().equals("id") || field.getName().equals("_id")){
                    // if the field is id, we need to set it to the value of the idField
                    populateDocumentId(entity, document);
                }else {
                    // if the field is not id, we need to set it to the value of the field
                    document.put(getFieldName(field), field.get(entity));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void populateDocumentId(V entity, Document document) throws IllegalAccessException {
        Object value = idField.get(entity);
        if (value instanceof ObjectId objectId) {
            document.put(idFieldName, new ObjectId(objectId.toHexString()));
        } else if (value instanceof String stringValue) {
            if (ObjectId.isValid(stringValue)) {
                document.put(idFieldName, new ObjectId(stringValue));
            } else {
                document.put(idFieldName, stringValue);
            }

        } else {
            Log.error("Unexpected id type in the document " + idField);
        }
    }
}
