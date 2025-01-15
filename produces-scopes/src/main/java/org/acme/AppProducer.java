package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

public class AppProducer {

    @Produces
    @ApplicationScoped
    public AppScopedBean appScopedBean() {
        return new AppScopedBean();
    }

    @Produces
    @RequestScoped
    public RequestScopedBean requestScopedBean() {
        return new RequestScopedBean();
    }
}
