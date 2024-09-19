package org.acme;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class Bean {

        public String hello() {
            return "hello";
        }
}
