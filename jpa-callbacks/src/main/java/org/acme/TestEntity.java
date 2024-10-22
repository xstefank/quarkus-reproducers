package org.acme;

import io.quarkus.logging.Log;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class TestEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @PrePersist
    public void prePersist() {
        Log.info("PrePersist called");
    }

    @PostPersist
    public void postPersist() {
        Log.info("PostPersist called");
    }

    @PreUpdate
    public void preUpdate() {
        Log.info("PreUpdate called");
    }

    @PostUpdate
    public void postUpdate() {
        Log.info("PostUpdate called");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
