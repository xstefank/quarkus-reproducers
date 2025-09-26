package io.xstefank;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class MyCustomBean {

    private final String id = UUID.randomUUID().toString();

    public String ping() {
        return id;
    }
}
