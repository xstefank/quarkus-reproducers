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

import java.util.UUID;

@Singleton
public class TestEntityCodec implements CollectibleCodec<TestEntity> {

    private final Codec<Document> documentCodec;

    public TestEntityCodec() {
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public void encode(BsonWriter bsonWriter, TestEntity testEntity, EncoderContext encoderContext) {
        Document document = new Document();
        document.put("id", testEntity.getId());
        document.put("name", testEntity.getName());
        documentCodec.encode(bsonWriter, document, encoderContext);
    }

    @Override
    public TestEntity generateIdIfAbsentFromDocument(TestEntity testEntity) {
        if (!documentHasId(testEntity)) {
            testEntity.setId(UUID.randomUUID().toString());
        }

        return testEntity;
    }

    @Override
    public boolean documentHasId(TestEntity testEntity) {
        return testEntity.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(TestEntity testEntity) {
        return new BsonString(testEntity.getId());
    }

    @Override
    public TestEntity decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document doc = documentCodec.decode(bsonReader, decoderContext);
        TestEntity testEntity = new TestEntity();
        Object id = doc.get("_id");
        if (ObjectId.class.isAssignableFrom(id.getClass())) {
            testEntity.setId(((ObjectId) id).toString());
        } else if (String.class.isAssignableFrom(id.getClass())) {
            testEntity.setId((String) id);
        } else {
            Log.error("Unexpected id type in the document " + id);
        }

        testEntity.setName(doc.getString("name"));

        return testEntity;
    }

    @Override
    public Class<TestEntity> getEncoderClass() {
        return TestEntity.class;
    }
}
