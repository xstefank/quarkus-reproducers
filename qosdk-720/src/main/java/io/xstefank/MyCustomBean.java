package io.xstefank;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class MyCustomBean {

    public String ping() {
        return UUID.randomUUID().toString();
    }
}
