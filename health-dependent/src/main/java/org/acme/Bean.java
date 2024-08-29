package org.acme;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class Bean {

    public String id;

    @PostConstruct
    public void init() {
        id = UUID.randomUUID().toString();
        System.out.println("Bean created: " + id);
    }

    public String getId() {
        return id;
    }
}
