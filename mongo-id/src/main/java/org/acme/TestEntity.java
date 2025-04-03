package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(database = "test-db")
public class TestEntity extends PanacheMongoEntityBase {

    @BsonId
    private ObjectId oid;
    private String _id;

    public TestEntity() {
    }

    public TestEntity(ObjectId _id) {
        this._id = _id.toString();
        this.oid = _id;
    }

    public ObjectId getOid() {
        return oid;
    }

    public void setOid(ObjectId oid) {
        this.oid = oid;
        this._id = oid.toString();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        this.oid = new ObjectId(_id);
    }

    @Override
    public String toString() {
        return "TestEntity{" +
            "oid=" + oid +
            ", _id='" + _id + '\'' +
            '}';
    }
}
