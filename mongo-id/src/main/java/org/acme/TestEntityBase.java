package org.acme;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

public abstract class TestEntityBase extends PanacheMongoEntityBase {

    public abstract void set_id(String id);

    public String name;
}
