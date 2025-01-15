package org.acme;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

public class AppProducer {



    @Produces
    @RequestScoped
    public RequestScopedBean requestScopedBean() {
        return new RequestScopedBean();
    }
}
