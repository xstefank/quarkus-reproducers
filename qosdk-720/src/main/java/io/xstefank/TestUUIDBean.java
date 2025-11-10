package io.xstefank;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class TestUUIDBean {

    private final String id = UUID.randomUUID().toString();

    public String uuid() {
        return id;
    }
}
